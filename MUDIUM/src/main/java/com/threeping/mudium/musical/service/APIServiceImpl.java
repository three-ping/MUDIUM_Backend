package com.threeping.mudium.musical.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.musical.dto.MusicalItem;
import com.threeping.mudium.musical.repository.MusicalRepository;
import com.threeping.mudium.performance.aggregate.Performance;
import com.threeping.mudium.performance.dto.PerformanceItem;
import com.threeping.mudium.performance.repository.PerformanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class APIServiceImpl implements APIService {

    private final MusicalRepository musicalRepository;
    private final PerformanceRepository performanceRepository;
    private final MusicalAPIClient musicalAPIClient;

    public APIServiceImpl(MusicalRepository musicalRepository, PerformanceRepository performanceRepository,
                          MusicalAPIClient musicalAPIClient) {
        this.musicalRepository = musicalRepository;
        this.performanceRepository = performanceRepository;
        this.musicalAPIClient = musicalAPIClient;
    }

//    @Scheduled(cron = "0 0 1 * * ?") 새벽 1시마다 db 자동 업데이트
//    @Scheduled(initialDelay = 5000, fixedDelay = 300000000)
    @Transactional
    @Override
    public void updateMusicalData() {
        try {
            List<MusicalItem> musicalItems = musicalAPIClient.fetchMusicalList();
            for (MusicalItem item : musicalItems) {
                processMusicalItem(item);
            }
        } catch (Exception e) {
            throw new CommonException(ErrorCode.API_LIST_BAD_REQUEST);
        }
    }

    private void processMusicalItem(MusicalItem item) {
        try {
            String OriginTitle = parseTitle(item.getTitle());
            log.info("제목 파싱 확인: {}", OriginTitle);
            Musical musical = getOrCreatedMusical(OriginTitle);
            PerformanceItem performanceItem =
                    musicalAPIClient.fetchPerformanceDetail(item.getExternalId());
            log.info("external Id: " + item.getExternalId());
            updateMusicalInfo(musical, performanceItem);
            log.info("업데이트된 뮤지컬 정보 확인: " + musical);
            Performance performance = getOrCreatedPerformance(musical, item.getArea());
            updatePerformance(performance, performanceItem);
            log.info("업데이트된 공연 정보 확인: " + performance);

            musicalRepository.save(musical);
            performanceRepository.save(performance);
        } catch (Exception e) {
            log.info("저장 실패한 item의 외부 externalId: " + item.getExternalId());
            log.info("저장에 실패한 item의 타이틀: " + item.getTitle());
            throw new CommonException(ErrorCode.ITEM_PROCESSING_ERROR);
        }
    }

    private void updatePerformance(Performance performance, PerformanceItem performanceItem) {
        performance.setActorList(performanceItem.getActorList());
        performance.setTheater(performanceItem.getTheater());
        performance.setEndDate(timeStampConverter(performanceItem.getEndDate()));
        performance.setStartDate(timeStampConverter(performanceItem.getStartDate()));
        performance.setRunTime(performanceItem.getRunTime());
        performance.setPoster(performanceItem.getPoster());
    }

    private void updateMusicalInfo(Musical musical, PerformanceItem performanceItem) {
        musical.setRating(performanceItem.getAge());
        if(musical.getPoster() == null)
            musical.setPoster(performanceItem.getPoster());
        if(musical.getProduction() == null)
            musical.setProduction(performanceItem.getEntrps());
    }

    private Performance getOrCreatedPerformance(Musical musical, String area) {
        return performanceRepository.findPerformanceByMusicalAndRegion(musical, area)
                .orElseGet(() -> {
                    Performance performance = new Performance();
                    performance.setMusical(musical);
                    performance.setRegion(area);
                    log.info("새로 생성된 공연 정보: " + performance);
                    return performance;
                });
    }

    private Musical getOrCreatedMusical(String originTitle) {
        return musicalRepository.findMusicalByExactTitle(originTitle).orElseGet(() -> {
           Musical musical = new Musical();
           musical.setTitle(originTitle);
           musicalRepository.save(musical);
           return musical;
        });
    }

    private String parseTitle(String title) {
        return title.replaceAll("\\[.*?\\]", "").trim();
    }

    private Timestamp timeStampConverter(String Date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        LocalDate localDate = LocalDate.parse(Date, formatter);

        LocalDateTime localDateTime = localDate.atStartOfDay();

        return Timestamp.valueOf(localDateTime);
    }
}
