package com.apka.quickstart.controller;

import com.apka.quickstart.DTO.RegisterRequestDTO;
import com.apka.quickstart.DTO.TravelPlanRequestDTO;
import com.apka.quickstart.Services.TravelPlanService;
import com.apka.quickstart.model.TravelPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travel-plans")
@RequiredArgsConstructor
public class TravelPlanController {
    private final TravelPlanService travelPlanService;
    @PostMapping
    public ResponseEntity<String> createTravelPlan(@RequestBody TravelPlanRequestDTO travelPlanRequestDTO) {
        try {
            travelPlanService.createPlan(travelPlanRequestDTO);
            return ResponseEntity.ok("Plan created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<TravelPlan>> getAllPlans(@RequestBody TravelPlanRequestDTO travelPlanRequestDTO){
        try {
           List<TravelPlan> userPlans = travelPlanService.getAllPlansForUser(travelPlanRequestDTO.getUserId());
            return ResponseEntity.ok(userPlans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<TravelPlan> getPlan(@PathVariable long planId){
        try {
            TravelPlan travelPlan = travelPlanService.getPlanById(planId);
            return ResponseEntity.ok(travelPlan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelPlan> updatePlan(@RequestBody TravelPlanRequestDTO travelPlanRequestDTO, @PathVariable long planId) {
        try {
            TravelPlan travelPlan = travelPlanService.updatePlan(planId,travelPlanRequestDTO);
            return ResponseEntity.ok(travelPlan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlan(@RequestBody TravelPlanRequestDTO travelPlanRequestDTO, @PathVariable long planId){
        try {
            travelPlanService.deletePlan(planId);
            return ResponseEntity.ok("Travel plan deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
