package org.marcinski.chickenHouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marcinski.chickenHouse.dto.UserDto;
import org.marcinski.chickenHouse.entity.User;

@Mapper(componentModel = "spring", uses = {ChickenHouseMapper.class})
public interface UserMapper {

    @Mapping(source = "roleDto", target = "role")
    @Mapping(source = "chickenHouseDtos", target = "chickenHouses")
    @Mapping(target = "uuid", ignore = true)
    User mapTo(UserDto userDto);

    @Mapping(source = "role", target = "roleDto")
    @Mapping(source = "chickenHouses", target = "chickenHouseDtos")
    @Mapping(target = "confirmedPassword", ignore = true)
    UserDto mapTo(User user);
}
