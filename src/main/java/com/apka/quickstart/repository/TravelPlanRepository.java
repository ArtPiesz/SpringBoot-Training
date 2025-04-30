package com.apka.quickstart.repository;

import com.apka.quickstart.model.TravelPlan;
import com.apka.quickstart.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TravelPlanRepository extends CrudRepository<TravelPlan, Long> {


    List<TravelPlan> findByUserId(Long userId);


    List<TravelPlan> findByTitleContainingIgnoreCase(String title);


    List<TravelPlan> findByStartDateBetween(LocalDate start, LocalDate end);


    boolean existsByTitleAndUserId(String title, Long userId);
}
