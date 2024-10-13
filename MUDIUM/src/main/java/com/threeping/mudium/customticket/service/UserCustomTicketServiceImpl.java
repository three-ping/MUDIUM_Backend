package com.threeping.mudium.customticket.service;

import com.threeping.mudium.customticket.aggregate.dto.UserCustomTicketDTO;
import com.threeping.mudium.customticket.aggregate.entity.UserCustomTicketEntity;
import com.threeping.mudium.customticket.aggregate.entity.UserCustomTicketId;
import com.threeping.mudium.customticket.repository.UserCustomTicketRepository;
import org.springframework.stereotype.Service;

@Service
public class UserCustomTicketServiceImpl implements UserCustomTicketService {

    private final UserCustomTicketRepository userCustomTicketRepository;

    public UserCustomTicketServiceImpl(UserCustomTicketRepository userCustomTicketRepository) {
        this.userCustomTicketRepository = userCustomTicketRepository;
    }

    @Override
    public UserCustomTicketDTO createUserCustomTicket(UserCustomTicketDTO userCustomTicketDTO) {
        UserCustomTicketEntity userCustomTicketEntity = new UserCustomTicketEntity(
                userCustomTicketDTO.getUserId(),
                userCustomTicketDTO.getCustomTicketId(),
                userCustomTicketDTO.getPhotoUrl(),
                userCustomTicketDTO.getTicketAttributers()
        );
        UserCustomTicketEntity savedEntity = userCustomTicketRepository.save(userCustomTicketEntity);
        return new UserCustomTicketDTO(
                savedEntity.getUserId(),
                savedEntity.getCustomTicketId(),
                savedEntity.getPhotoUrl(),
                savedEntity.getTicketAttributers()
        );
    }

    @Override
    public UserCustomTicketDTO updateUserCustomTicket(Long userId, Long customTicketId, UserCustomTicketDTO userCustomTicketDTO) {
        UserCustomTicketId compositeKey = new UserCustomTicketId(userId, customTicketId);

        UserCustomTicketEntity entity = userCustomTicketRepository.findById(compositeKey)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        entity.setPhotoUrl(userCustomTicketDTO.getPhotoUrl());
        entity.setTicketAttributers(userCustomTicketDTO.getTicketAttributers());

        UserCustomTicketEntity updatedEntity = userCustomTicketRepository.save(entity);
        return new UserCustomTicketDTO(
                updatedEntity.getUserId(),
                updatedEntity.getCustomTicketId(),
                updatedEntity.getPhotoUrl(),
                updatedEntity.getTicketAttributers()
        );
    }

    @Override
    public void deleteUserCustomTicket(Long userId, Long customTicketId) {
        UserCustomTicketId compositeKey = new UserCustomTicketId(userId, customTicketId);
        userCustomTicketRepository.deleteById(compositeKey);
    }
}