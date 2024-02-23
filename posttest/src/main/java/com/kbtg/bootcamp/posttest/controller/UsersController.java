package com.kbtg.bootcamp.posttest.controller;

import com.kbtg.bootcamp.posttest.dto.TicketsDto;
import com.kbtg.bootcamp.posttest.entity.TicketEntity;
import com.kbtg.bootcamp.posttest.entity.UserTicketsEntity;
import com.kbtg.bootcamp.posttest.entity.UsersEntity;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import com.kbtg.bootcamp.posttest.service.UsersService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@Log4j2
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/lotteries")
    public ResponseEntity<?> lotteries() {
        TicketsDto ticketsDto = usersService.getAllLotteries();
        return new ResponseEntity<>(ticketsDto, HttpStatus.OK);
    }
    @GetMapping("/myLotteries/{userId}")
    public TicketsDto myLotteries(@PathVariable Long userId) {
        return usersService.getMyLotteries(userId);
    }
    @PostMapping("/{userId}/lotteries/{ticketId}")
    public UserTicketsEntity buyLotteries(@PathVariable Long userId, @PathVariable Integer ticketId) {
        return usersService.buyLotteries(userId,ticketId);
    }

    @PostMapping("userRegister")
    public ResponseEntity<String> userRegister(@RequestBody UsersEntity usersEntity) {
        return usersService.usersRegister(usersEntity);
    }

    @DeleteMapping("/{userId}/lotteries/{ticketId}")
    public TicketsDto sellLotteries(@PathVariable Long userId,@PathVariable Integer ticketId) {
        return usersService.sellLotteries(userId,ticketId);
    }


}
