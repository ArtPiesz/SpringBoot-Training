package com.apka.quickstart.DTO;

import com.apka.quickstart.Services.UserService;
import com.apka.quickstart.model.TravelPlan;
import com.apka.quickstart.model.User;
import com.apka.quickstart.repository.UserRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import com.apka.quickstart.DTO.TravelPlanRequestDTO;
import com.apka.quickstart.DTO.TravelPlanResponseDTO;

@Mapper(componentModel = "spring")
public interface TravelPlanMapper {
    @Mapping(target = "user.id", source = "userId")
    TravelPlan TravelPlanRequestToPlan(TravelPlanRequestDTO request, @Context User user);

    @Mapping(target = "userId", source = "user.id")
    TravelPlanResponseDTO PlanToTravelPlanResponse(TravelPlan travelPlan);

   // @Mapping(target = "user.id", source = "userId")
    void updateTravelPlanFromDTO(TravelPlanRequestDTO dto, @MappingTarget TravelPlan entity);
}

