package com.cboard.rental.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.cboard.rental.user.dto.UserDTO;
import com.cboard.rental.user.entity.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", source = "user.roles") // Map roles if necessary
    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
