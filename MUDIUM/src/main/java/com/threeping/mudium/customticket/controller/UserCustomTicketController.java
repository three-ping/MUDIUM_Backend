//package com.threeping.mudium.customticket.controller;
//
//import com.threeping.mudium.customticket.aggregate.dto.UserCustomTicketDTO;
//import com.threeping.mudium.customticket.service.UserCustomTicketService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/user-custom-ticket")
//public class UserCustomTicketController {
//
//    private final UserCustomTicketService userCustomTicketService;
//
//    public UserCustomTicketController(UserCustomTicketService userCustomTicketService) {
//        this.userCustomTicketService = userCustomTicketService;
//    }
//
//    @PostMapping("/create")
//    public UserCustomTicketDTO createUserCustomTicket(@RequestBody UserCustomTicketDTO userCustomTicketDTO) {
//        return userCustomTicketService.createUserCustomTicket(userCustomTicketDTO);
//    }
//
//    @PutMapping("/update/{userCustomTicketId}")
//    public UserCustomTicketDTO updateUserCustomTicket(@PathVariable Long userCustomTicketId,
//                                                      @RequestBody UserCustomTicketDTO userCustomTicketDTO) {
//        return userCustomTicketService.updateUserCustomTicket(userCustomTicketId, userCustomTicketDTO);
//    }
//
//    @DeleteMapping("/delete/{userCustomTicketId}")
//    public void deleteUserCustomTicket(@PathVariable Long userCustomTicketId) {
//        userCustomTicketService.deleteUserCustomTicket(userCustomTicketId);
//    }
//}

package com.threeping.mudium.customticket.controller;
import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.customticket.aggregate.dto.UserCustomTicketDTO;
import com.threeping.mudium.customticket.service.UserCustomTicketService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-custom-ticket")
@Transactional
public class UserCustomTicketController {

    private final UserCustomTicketService userCustomTicketService;

    public UserCustomTicketController(UserCustomTicketService userCustomTicketService) {
        this.userCustomTicketService = userCustomTicketService;
    }

//    @PostMapping("/create")
//    public UserCustomTicketDTO createUserCustomTicket(@RequestBody UserCustomTicketDTO userCustomTicketDTO) {
//        return userCustomTicketService.createUserCustomTicket(userCustomTicketDTO);
//    }

    @PostMapping("/create")
    public ResponseDTO<?> createUserCustomTicket(@RequestBody UserCustomTicketDTO userCustomTicketDTO) {
        UserCustomTicketDTO createdTicket = userCustomTicketService.createUserCustomTicket(userCustomTicketDTO);
        return ResponseDTO.ok(createdTicket);
    }

//    @PutMapping("/update/{userId}/{customTicketId}")
//    public UserCustomTicketDTO updateUserCustomTicket(@PathVariable Long userId,
//                                                      @PathVariable Long customTicketId,
//                                                      @RequestBody UserCustomTicketDTO userCustomTicketDTO) {
//        return userCustomTicketService.updateUserCustomTicket(userId, customTicketId, userCustomTicketDTO);
//    }

    @PutMapping("/update/{userId}/{customTicketId}")
    public ResponseDTO<?> updateUserCustomTicket(
            @PathVariable Long userId,
            @PathVariable Long customTicketId,
            @RequestBody UserCustomTicketDTO userCustomTicketDTO) {
        UserCustomTicketDTO updatedTicket = userCustomTicketService.updateUserCustomTicket(userId, customTicketId, userCustomTicketDTO);
        return ResponseDTO.ok(updatedTicket);
    }

//    @DeleteMapping("/delete/{userId}/{customTicketId}")
//    public void deleteUserCustomTicket(@PathVariable Long userId, @PathVariable Long customTicketId) {
//        userCustomTicketService.deleteUserCustomTicket(userId, customTicketId);
//    }
    @DeleteMapping("/delete/{userId}/{customTicketId}")
    public ResponseDTO<?> deleteUserCustomTicket(
        @PathVariable Long userId,
        @PathVariable Long customTicketId) {
        userCustomTicketService.deleteUserCustomTicket(userId, customTicketId);
    return ResponseDTO.ok(null); // 삭제 시 데이터가 없으므로 null 반환
    }
}