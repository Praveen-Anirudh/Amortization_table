package com.ults.amortization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ults.amortization.model.MonthlyAmortizationSchedule;

@Repository
public interface DataRepository extends JpaRepository<MonthlyAmortizationSchedule, Long> {

}
