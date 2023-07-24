package com.gachon.hypergachon.domain.advertise.repository;

import com.gachon.hypergachon.domain.advertise.entity.Advertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertiseRepository extends JpaRepository<Advertise, Long> {
    
}
