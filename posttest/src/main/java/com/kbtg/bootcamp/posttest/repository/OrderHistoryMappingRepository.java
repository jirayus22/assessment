package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.OrderHistoryMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryMappingRepository extends JpaRepository<OrderHistoryMappingEntity , Integer> {

    @Query(value = "SELECT MAX(order_id) + 1 FROM order_history_mapping", nativeQuery = true)
    Integer findMaxOrderId();

    List<OrderHistoryMappingEntity> findAllByUserIdAndStatusId(Long userId, Integer statusId);

    OrderHistoryMappingEntity findFirstByUserIdAndTicketId(Long userId, Integer ticketId);
}
