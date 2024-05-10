package com.att.tdp.bisbis10.repository;

import com.att.tdp.bisbis10.entity.BisOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<BisOrder, Long> {
}
