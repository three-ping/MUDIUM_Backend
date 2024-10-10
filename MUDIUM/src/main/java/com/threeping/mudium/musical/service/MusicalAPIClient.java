package com.threeping.mudium.musical.service;


import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.musical.dto.MusicalItem;
import com.threeping.mudium.musical.dto.MusicalListResponse;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
public class MusicalAPIClient {

    @Value("${kopis.api.key}")
    private String apiKey;

    @Value("${kopis.api.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public MusicalAPIClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<MusicalItem> fetchMusicalList() {
        String startDate = "20220101";
        String endDate = "20240930";
        int cPage = 1;
        List<MusicalItem> allMusicals = new ArrayList<>();

        while(true) {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("service", apiKey)
                    .queryParam("stdate", startDate)
                    .queryParam("eddate", endDate)
                    .queryParam("cpage", cPage + "")
                    .queryParam("rows", "100")
                    .queryParam("shcate", "GGGA")
                    .toUriString();

            String response = restTemplate.getForObject(url, String.class);
            MusicalListResponse parsedResponse = parseXmlResponse(response);
            List<MusicalItem> musicalItems = parsedResponse.getMusicalItems();

            if (musicalItems == null || musicalItems.isEmpty()) {
                break; // 결과가 없으면 루프 종료
            }
            allMusicals.addAll(musicalItems);
            cPage++;
        }
        return allMusicals;
    }

//    public

    private MusicalListResponse parseXmlResponse(String response) {
        try {
            return JAXBManager.getInstance().unmarshal(response, MusicalListResponse.class);
        } catch (JAXBException e) {
            throw new CommonException(ErrorCode.JAXB_CONTEXT_ERROR);
        }
    }

    private MusicalItem parseXmlItem(String response) {
        try {
            return JAXBManager.getInstance().unmarshal(response, MusicalItem.class);
        } catch (JAXBException e) {
            throw new CommonException(ErrorCode.JAXB_CONTEXT_ERROR);
        }
    }
}
