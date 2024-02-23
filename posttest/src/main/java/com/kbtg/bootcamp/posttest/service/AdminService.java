package com.kbtg.bootcamp.posttest.service;

import com.kbtg.bootcamp.posttest.entity.TicketEntity;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.sql.Timestamp;

@Service
public class AdminService {

    @Autowired
    private TicketRepository lotteryRepository;

    @Transient
    public TicketEntity createLottery(TicketEntity ticketEntity){
        if(ticketEntity != null){
            ticketEntity.setCreateAt(new Timestamp(System.currentTimeMillis()));
            lotteryRepository.save(ticketEntity);
            return ticketEntity;
        }
        return null;
    }
}
