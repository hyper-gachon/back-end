package com.gachon.hypergachon.domain.user.repository;

import com.gachon.hypergachon.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUserId(String userId);

    Boolean existsByUserId(String userId);
}
