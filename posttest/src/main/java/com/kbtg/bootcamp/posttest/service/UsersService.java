package com.kbtg.bootcamp.posttest.service;

import com.google.gson.Gson;
import com.kbtg.bootcamp.posttest.dto.TicketsDto;
import com.kbtg.bootcamp.posttest.entity.OrderHistoryMappingEntity;
import com.kbtg.bootcamp.posttest.entity.TicketEntity;
import com.kbtg.bootcamp.posttest.entity.UserTicketsEntity;
import com.kbtg.bootcamp.posttest.entity.UsersEntity;
import com.kbtg.bootcamp.posttest.exception.DuplicateUsernameException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.repository.OrderHistoryMappingRepository;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import com.kbtg.bootcamp.posttest.repository.UserTicketsRepository;
import com.kbtg.bootcamp.posttest.repository.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.beans.Transient;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Log4j2
public class UsersService {

    private final UsersRepository usersRepository;
    private final TicketRepository ticketRepository;
    private final UserTicketsRepository userTicketsRepository;
    private final OrderHistoryMappingRepository orderHistoryMappingRepository;

    private static final int USER_ID_LENGTH = 10;
    private final Random random = new Random();

    public UsersService(UsersRepository usersRepository, TicketRepository ticketRepository, UserTicketsRepository userTicketsRepository, OrderHistoryMappingRepository orderHistoryMappingRepository) {
        this.usersRepository = usersRepository;
        this.ticketRepository = ticketRepository;
        this.userTicketsRepository = userTicketsRepository;
        this.orderHistoryMappingRepository = orderHistoryMappingRepository;
    }

    public TicketsDto getAllLotteries(){
        ArrayList<Integer> arr = new ArrayList<>();
        List<TicketEntity> ticketEntity = ticketRepository.findAll();
        for (TicketEntity entity : ticketEntity) {
            arr.add(entity.getTicket());
        }
        TicketsDto lotteryDto = new TicketsDto();
        lotteryDto.setLotteryNo(arr.toString());
        return lotteryDto;
    }

    public TicketsDto getMyLotteries(Long userId){
        Optional<UsersEntity> checkUser = usersRepository.findById(userId);
        if(checkUser.isEmpty()){
            throw new NotFoundException("This userId is not found : " + userId);
        }
        List<OrderHistoryMappingEntity> orderHistoryMappingEntities = orderHistoryMappingRepository.findAllByUserIdAndStatusId(userId,1);
        ArrayList<Integer> arrTicket = new ArrayList<>();
        ArrayList<Integer> arrPrice = new ArrayList<>();
        TicketsDto ticketsDto = new TicketsDto();
        if(orderHistoryMappingEntities != null){
            int price = 0;
            for (OrderHistoryMappingEntity entity : orderHistoryMappingEntities) {
                arrTicket.add(entity.getTicketEntity().getTicket());
                price++;
                arrPrice.add(entity.getTicketEntity().getPrice());
            }
            ticketsDto.setLotteryNo(arrTicket.toString());
            int cost = 0;
            for (Integer number : arrPrice) {
                cost += number;
            }
            ticketsDto.setCost(String.valueOf(cost));
            ticketsDto.setCount(String.valueOf(price));
            return ticketsDto;
        }
        return ticketsDto;
    }

    @Transient
    public ResponseEntity<String> usersRegister(UsersEntity usersEntity){
        UsersEntity findUserName = usersRepository.findByUsername(usersEntity.getUsername());
        if(findUserName != null){
            throw new DuplicateUsernameException("this username is duplicate : " + usersEntity.getUsername());
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usersEntity.setUserId(generateRandomUserId());
        generateRandomUserId();
        usersEntity.setRoleId(2);
        usersEntity.setPassword(encoder.encode(usersEntity.getPassword()));
        usersEntity.setCreateAt(new Timestamp(System.currentTimeMillis()));
        usersEntity.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        usersRepository.save(usersEntity);
        return ResponseEntity.ok("Your userId is : " + usersEntity.getUserId());
    }

    private Long generateRandomUserId() {
        Random random = new Random();
        long lowerBound = 1000000000L;
        long upperBound = 9999999999L;
        return lowerBound + (long) (random.nextDouble() * (upperBound - lowerBound + 1));
    }

    @Transient
    public UserTicketsEntity buyLotteries(Long userId,Integer ticket){
        checkNotFoundUser(userId);
        TicketEntity findTicket = checkNotFoundTicket(ticket);
        OrderHistoryMappingEntity orderHistoryMappingEntity = new OrderHistoryMappingEntity();
        Integer getMaxOrderId = orderHistoryMappingRepository.findMaxOrderId();
        Integer orderId = (getMaxOrderId == null) ? 1 : getMaxOrderId;
        orderHistoryMappingEntity.setOrderId(orderId);
        orderHistoryMappingEntity.setUserId(userId);
        orderHistoryMappingEntity.setTicketId(findTicket.getTicketId());
        orderHistoryMappingEntity.setCreateAt(new Timestamp(System.currentTimeMillis()));
        orderHistoryMappingEntity.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        orderHistoryMappingEntity.setStatusId(1);
        OrderHistoryMappingEntity orderHistoryMappingSave = orderHistoryMappingRepository.save(orderHistoryMappingEntity);
        UserTicketsEntity userTicketsEntity = new UserTicketsEntity();
        userTicketsEntity.setOrderId(orderHistoryMappingSave.getOrderId());
        userTicketsEntity.setUserId(userId);
        userTicketsEntity.setCreateAt(new Timestamp(System.currentTimeMillis()));
        userTicketsRepository.save(userTicketsEntity);
        return userTicketsEntity;
    }

    @Transient
    public TicketsDto sellLotteries(Long userId,Integer ticket){
        checkNotFoundUser(userId);
        TicketEntity findTicket = checkNotFoundTicket(ticket);
        OrderHistoryMappingEntity checkOrderHistory = orderHistoryMappingRepository
                .findFirstByUserIdAndTicketId(userId,findTicket.getTicketId());
        if(checkOrderHistory == null){
            throw new NotFoundException("You Don't have this ticket : " + ticket);
        }
        orderHistoryMappingRepository.deleteById(checkOrderHistory.getOrderId());
        userTicketsRepository.deleteById(checkOrderHistory.getOrderId());
        TicketsDto ticketsDto = new TicketsDto();
        ticketsDto.setLotteryNo(ticket.toString());
        return ticketsDto;
    }

    public UsersEntity checkNotFoundUser(Long userId){
        Optional<UsersEntity> usersEntity = usersRepository.findById(userId);
        if(usersEntity.isEmpty()){
            throw new NotFoundException("This userId is not found : " + userId);
        }
        return usersEntity.get();
    }
    public TicketEntity checkNotFoundTicket(Integer ticket){
        TicketEntity findTicket = ticketRepository.findByTicket(ticket);
        if(findTicket == null){
            throw new NotFoundException("This ticket is not found : " + ticket);
        }
        return findTicket;
    }

}
