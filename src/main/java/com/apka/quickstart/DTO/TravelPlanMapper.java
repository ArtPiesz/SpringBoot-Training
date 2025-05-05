package com.apka.quickstart.DTO;

import com.apka.quickstart.model.TravelPlan;
import com.apka.quickstart.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TravelPlanMapper {
    TravelPlanMapper INSTANCE = Mappers.getMapper(TravelPlanMapper.class);
    @Mapping(target = "user", source = "user")
    TravelPlan TravelPlanRequestToPlan(TravelPlanRequestDTO request, User user);

    @Mapping(target = "userId", source = "user.id")
    TravelPlanResponseDTO PlanToTravelPlanResponse(TravelPlan travelPlan);
}
