package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.UserTicketsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTicketsRepository extends JpaRepository<UserTicketsEntity,Integer> {
}
