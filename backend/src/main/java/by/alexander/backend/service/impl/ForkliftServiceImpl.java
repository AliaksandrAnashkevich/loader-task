package by.alexander.backend.service.impl;

import by.alexander.backend.auth.CustomUserDetails;
import by.alexander.backend.auth.JwtUserDetailsService;
import by.alexander.backend.dao.ForkliftRepository;
import by.alexander.backend.dao.UserRepository;
import by.alexander.backend.dto.ForkliftDto;
import by.alexander.backend.entity.Forklift;
import by.alexander.backend.entity.User;
import by.alexander.backend.mapper.ForkliftMapper;
import by.alexander.backend.service.ForkliftService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ForkliftServiceImpl implements ForkliftService {

    ForkliftRepository forkliftRepository;
    ForkliftMapper forkliftMapper;
    UserRepository userRepository;

    public ForkliftDto getById(Long id) {
        Forklift forklift = forkliftRepository.findById(id)
                .orElseThrow();
        return forkliftMapper.toDto(forklift);
    }

    public List<ForkliftDto> getAll() {
        return forkliftRepository.findAll().stream()
                .map(forkliftMapper::toDto)
                .toList();
    }

    public void add(ForkliftDto forkliftDto) {
        User user = getUserById();
        Forklift forklift = forkliftMapper.toEntity(forkliftDto, true, user);
        forkliftRepository.save(forklift);
    }

    public void edit(Long id, ForkliftDto forkliftDto) {
        Forklift forklift = forkliftRepository.findById(id)
                .orElseThrow();
        User user = getUserById();
        forklift = forkliftMapper.toEntityUpdate(forklift, forkliftDto, user);
        forkliftRepository.save(forklift);
    }

    public void delete(Long id) {
        Forklift forklift = forkliftRepository.findById(id)
                .orElseThrow();
        forkliftRepository.deleteById(forklift.getId());
    }

    private User getUserById() {
        CustomUserDetails auth = JwtUserDetailsService.getAuthentication();
        return userRepository.findById(auth.getId())
                .orElseThrow();
    }
}
