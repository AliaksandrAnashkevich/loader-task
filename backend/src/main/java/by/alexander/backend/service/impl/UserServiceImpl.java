package by.alexander.backend.service.impl;

import by.alexander.backend.dao.UserRepository;
import by.alexander.backend.dto.UserDto;
import by.alexander.backend.entity.User;
import by.alexander.backend.mapper.UserMapper;
import by.alexander.backend.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow();
        return userMapper.toDto(user);
    }
}
