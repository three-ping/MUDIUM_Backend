package com.threeping.mudium.customticket.service;

import com.threeping.mudium.customticket.aggregate.dto.CustomTicketDTO;
import com.threeping.mudium.customticket.aggregate.entity.CustomTicketEntity;
import com.threeping.mudium.customticket.repository.CustomTicketRepository;
import com.threeping.mudium.musical.aggregate.Musical;
import com.threeping.mudium.musical.repository.MusicalRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomTicketServiceImpl implements CustomTicketService {

    private final CustomTicketRepository customTicketRepository;
    private final MusicalRepository musicalRepository;

    public CustomTicketServiceImpl(CustomTicketRepository customTicketRepository,
                                   MusicalRepository musicalRepository) {
        this.customTicketRepository = customTicketRepository;
        this.musicalRepository = musicalRepository;
    }

    @Override
    public CustomTicketDTO createCustomTicket(CustomTicketDTO customTicketDTO) {
        Musical musical = musicalRepository.findById(customTicketDTO.getMusicalId())
                .orElseThrow(() -> new RuntimeException("해당 뮤지컬을 찾을 수 없습니다."));

        CustomTicketEntity ticket = new CustomTicketEntity();
        ticket.setTicketImage(customTicketDTO.getTicketImage());
        ticket.setThemeName(customTicketDTO.getThemeName());
        ticket.setMusical(musical);

        CustomTicketEntity savedTicket = customTicketRepository.save(ticket);
        return new CustomTicketDTO(
                savedTicket.getCustomTicketId(),
                savedTicket.getTicketImage(),
                savedTicket.getThemeName(),
                savedTicket.getMusical().getMusicalId()
        );
    }

    @Override
    public CustomTicketDTO updateCustomTicket(Long ticketId, CustomTicketDTO customTicketDTO) {
        CustomTicketEntity ticket = customTicketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("티켓을 찾을 수 없습니다."));

        ticket.setTicketImage(customTicketDTO.getTicketImage());
        ticket.setThemeName(customTicketDTO.getThemeName());

        Musical musical = musicalRepository.findById(customTicketDTO.getMusicalId())
                .orElseThrow(() -> new RuntimeException("해당 뮤지컬을 찾을 수 없습니다."));
        ticket.setMusical(musical);

        CustomTicketEntity updatedTicket = customTicketRepository.save(ticket);
        return new CustomTicketDTO(
                updatedTicket.getCustomTicketId(),
                updatedTicket.getTicketImage(),
                updatedTicket.getThemeName(),
                updatedTicket.getMusical().getMusicalId()
        );
    }

    @Override
    public void deleteCustomTicket(Long ticketId) {
        customTicketRepository.deleteById(ticketId);
    }
}
