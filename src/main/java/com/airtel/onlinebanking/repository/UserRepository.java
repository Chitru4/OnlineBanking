package com.airtel.onlinebanking.repository;

import com.airtel.onlinebanking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean deleteByUsername(String username);
}
