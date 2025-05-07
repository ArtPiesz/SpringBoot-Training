package com.apka.quickstart.Services;

import com.apka.quickstart.DTO.RegisterRequestDTO;
import com.apka.quickstart.DTO.TravelPlanMapper;
import com.apka.quickstart.DTO.TravelPlanRequestDTO;
import com.apka.quickstart.DTO.UserMapper;
import com.apka.quickstart.model.TravelPlan;
import com.apka.quickstart.model.User;
import com.apka.quickstart.repository.TravelPlanRepository;
import com.apka.quickstart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelPlanService {
    private final TravelPlanRepository travelPlanRepository;
    private final UserRepository userRepository;

    private final TravelPlanMapper travelPlanMapper;
    public TravelPlan createPlan(TravelPlanRequestDTO planRequestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        TravelPlan plan = travelPlanMapper.TravelPlanRequestToPlan(planRequestDTO,user);
        plan.setCreatedAt(LocalDate.now());
        return travelPlanRepository.save(plan);
    }

    public List<TravelPlan> getAllPlansForUser(Long userId){
        return travelPlanRepository.findByUserId(userId);

        }
    public TravelPlan getPlanById(Long planId){
    return travelPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    public void deletePlan(Long planId){
        travelPlanRepository.deleteById(planId);
    }
    public TravelPlan updatePlan(Long planId, TravelPlanRequestDTO updatedPlanDTO) {
        TravelPlan existingPlan = getPlanById(planId);
        existingPlan.setTitle(updatedPlanDTO.getTitle());
        existingPlan.setDescription(updatedPlanDTO.getDescription());
        existingPlan.setDestination(updatedPlanDTO.getDescription());
        existingPlan.setStartDate(updatedPlanDTO.getStartDate());
        existingPlan.setEndDate(updatedPlanDTO.getEndDate());
        return travelPlanRepository.save(existingPlan);
    }
    }

