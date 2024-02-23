package com.kbtg.bootcamp.posttest.controller;

import com.google.gson.Gson;
import com.kbtg.bootcamp.posttest.entity.TicketEntity;
import com.kbtg.bootcamp.posttest.service.AdminService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@Log4j2
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/lotteries")
    public ResponseEntity<?> lotteries(@Validated @RequestBody TicketEntity ticketEntity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TicketEntity response = adminService.createLottery(ticketEntity);
        if(response != null){
            return ResponseEntity.ok(response.getTicket());
        }
        return ResponseEntity.badRequest().build();
    }

}
