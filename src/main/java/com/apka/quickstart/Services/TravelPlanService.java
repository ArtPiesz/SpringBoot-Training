package com.apka.quickstart.Services;

import com.apka.quickstart.DTO.RegisterRequestDTO;
import com.apka.quickstart.model.TravelPlan;
import com.apka.quickstart.model.User;
import com.apka.quickstart.repository.TravelPlanRepository;
import com.apka.quickstart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelPlanService {
    private final TravelPlanRepository travelPlanRepository;
    private final UserRepository userRepository;
    public TravelPlan createPlan(TravelPlan plan, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        plan.setUser(user);
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
    public TravelPlan updatePlan(Long planId, TravelPlan updatedPlan) {
        TravelPlan existingPlan = getPlanById(planId);
        existingPlan.setTitle(updatedPlan.getTitle());
        existingPlan.setDescription(updatedPlan.getDescription());
        existingPlan.setStartDate(updatedPlan.getStartDate());
        existingPlan.setEndDate(updatedPlan.getEndDate());
        return travelPlanRepository.save(existingPlan);
    }
    }

