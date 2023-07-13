package com.gachon.hypergachon.advertise.repository;

import com.gachon.hypergachon.advertise.entity.Advertise;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertiseRepository extends JpaRepository<Advertise, Long> {
    
}
