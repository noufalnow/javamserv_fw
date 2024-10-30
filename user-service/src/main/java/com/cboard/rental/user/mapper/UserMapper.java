package com.cboard.rental.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.cboard.rental.user.dto.UserDTO;
import com.cboard.rental.user.entity.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Map roles and password explicitly if necessary
    @Mapping(target = "roles", source = "roles")
    @Mapping(target = "password", source = "password")
    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
