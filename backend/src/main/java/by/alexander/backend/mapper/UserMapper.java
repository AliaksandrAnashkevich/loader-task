package by.alexander.backend.mapper;

import by.alexander.backend.dto.UserDto;
import by.alexander.backend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);
}
