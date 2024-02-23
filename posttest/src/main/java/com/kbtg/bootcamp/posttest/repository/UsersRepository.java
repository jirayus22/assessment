package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity,Long> {

    UsersEntity findByUsername(String username);

    @Query(value = "SELECT MAX(user_id) + 1 FROM users", nativeQuery = true)
    Integer findMaxUsersId();
}
