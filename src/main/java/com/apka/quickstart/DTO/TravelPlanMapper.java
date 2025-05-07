package com.apka.quickstart.DTO;

import com.apka.quickstart.model.TravelPlan;
import com.apka.quickstart.model.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TravelPlanMapper {

    TravelPlan TravelPlanRequestToPlan(TravelPlanRequestDTO request, @Context User user);

    @Mapping(target = "userId", source = "user.id")
    TravelPlanResponseDTO PlanToTravelPlanResponse(TravelPlan travelPlan);
}
