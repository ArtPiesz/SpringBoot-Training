package com.apka.quickstart.Services;

import com.apka.quickstart.DTO.RegisterRequestDTO;
import com.apka.quickstart.DTO.TravelPlanMapper;
import com.apka.quickstart.DTO.TravelPlanRequestDTO;
import com.apka.quickstart.DTO.UserMapper;
import com.apka.quickstart.model.TravelPlan;
import com.apka.quickstart.model.User;
import com.apka.quickstart.repository.TravelPlanRepository;
import com.apka.quickstart.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelPlanService {
    private final TravelPlanRepository travelPlanRepository;
    private final UserService userService;

    private final TravelPlanMapper travelPlanMapper;
    @Transactional
    public TravelPlan createPlan(TravelPlanRequestDTO planRequestDTO) {
        User user = userService.getUserFromContext();
        TravelPlan plan = travelPlanMapper.TravelPlanRequestToPlan(planRequestDTO,user);
        plan.setCreatedAt(LocalDate.now());
        plan.setUser(user);
        return travelPlanRepository.save(plan);
    }

    public List<TravelPlan> getAllPlansForUser(){
        User user = userService.getUserFromContext();
        return travelPlanRepository.findByUserId(user.getId());

        }
    public TravelPlan getPlanById(Long planId){
    return travelPlanRepository.findPlanById(planId);

    }

    public void deletePlan(Long planId){
        travelPlanRepository.deleteById(planId);
    }
    public TravelPlan updatePlan(Long planId, TravelPlanRequestDTO updatedPlanDTO) {

        TravelPlan existingPlan = travelPlanRepository.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Plan not found with id: " + planId));
        travelPlanMapper.updateTravelPlanFromDTO(updatedPlanDTO, existingPlan);
        User user = userService.getUserFromContext();
        existingPlan.setUser(user);
        return travelPlanRepository.save(existingPlan);
    }
    }

