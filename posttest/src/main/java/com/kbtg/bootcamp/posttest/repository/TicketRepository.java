package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketEntity,Integer> {

    TicketEntity findByTicket(Integer ticket);

    TicketEntity findFirstByTicket(Integer ticket);
}
