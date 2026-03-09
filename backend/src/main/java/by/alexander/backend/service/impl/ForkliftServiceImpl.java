package by.alexander.backend.service.impl;

import by.alexander.backend.auth.CustomUserDetails;
import by.alexander.backend.auth.JwtUserDetailsService;
import by.alexander.backend.dao.ForkliftRepository;
import by.alexander.backend.dao.ForkliftTimeoutRepository;
import by.alexander.backend.dao.UserRepository;
import by.alexander.backend.dao.specification.ForkliftSpecification;
import by.alexander.backend.dto.ForkliftDto;
import by.alexander.backend.entity.Forklift;
import by.alexander.backend.entity.User;
import by.alexander.backend.exception.BadRequestException;
import by.alexander.backend.exception.ResourceNotFoundException;
import by.alexander.backend.mapper.ForkliftMapper;
import by.alexander.backend.service.ForkliftService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static by.alexander.backend.util.ConstantsUtil.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ForkliftServiceImpl implements ForkliftService {

    ForkliftRepository forkliftRepository;
    ForkliftMapper forkliftMapper;
    UserRepository userRepository;
    ForkliftTimeoutRepository forkliftTimeoutRepository;

    public ForkliftDto getById(Long id) {
        Forklift forklift = forkliftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FORKLIFT, ID, id));
        return forkliftMapper.toDto(forklift);
    }

    public List<ForkliftDto> getAll(String number, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return forkliftRepository.findAll(ForkliftSpecification.withFilter(number), sort).stream()
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
                .orElseThrow(() -> new ResourceNotFoundException(FORKLIFT, ID, id));
        User user = getUserById();
        forklift = forkliftMapper.toEntityUpdate(forklift, forkliftDto, user);
        forkliftRepository.save(forklift);
    }

    public void delete(Long id) {
        Forklift forklift = forkliftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FORKLIFT, ID, id));
        boolean isHasTimeout = forkliftTimeoutRepository.existsByForkliftIdAndSolutionDateIsNotNull(id);
        if (isHasTimeout) {
            throw new BadRequestException("Forklift has been active timeout");
        }
        forkliftRepository.deleteById(forklift.getId());
    }

    private User getUserById() {
        CustomUserDetails auth = JwtUserDetailsService.getAuthentication();
        return userRepository.findById(auth.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USERS, ID, auth.getId()));
    }
}
