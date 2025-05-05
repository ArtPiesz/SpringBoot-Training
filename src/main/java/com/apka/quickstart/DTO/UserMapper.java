package com.apka.quickstart.DTO;

import com.apka.quickstart.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    User registerRequestToUser(RegisterRequestDTO request);


    UserResponseDTO userToUserResponse(User user);
}
