package by.alexander.backend.service;

import by.alexander.backend.dto.UserDto;

public interface UserService {

    UserDto getById(Long id);
}
