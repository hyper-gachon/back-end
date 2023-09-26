package com.gachon.hypergachon.domain.advertise.repository;

import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import com.gachon.hypergachon.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertiseRepository extends JpaRepository<Advertise, Long> {
    List<Advertise> findByUserOrderByCreatedDateDesc(User user);

    List<Advertise> findAllByOrderByCreatedDateDesc();
}
