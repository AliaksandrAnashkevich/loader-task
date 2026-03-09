package by.alexander.backend.service.impl;

import by.alexander.backend.dao.ForkliftRepository;
import by.alexander.backend.dao.ForkliftTimeoutRepository;
import by.alexander.backend.dto.ForkliftTimeoutDto;
import by.alexander.backend.entity.Forklift;
import by.alexander.backend.entity.ForkliftTimeout;
import by.alexander.backend.exception.ResourceNotFoundException;
import by.alexander.backend.mapper.ForkliftTimeoutMapper;
import by.alexander.backend.service.ForkliftTimeoutService;
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
public class ForkliftTimeoutServiceImpl implements ForkliftTimeoutService {

    ForkliftTimeoutRepository forkliftTimeoutRepository;
    ForkliftRepository forkliftRepository;
    ForkliftTimeoutMapper forkliftTimeoutMapper;

    @Override
    public ForkliftTimeoutDto getById(Long id) {
        ForkliftTimeout forkliftTimeout = forkliftTimeoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIMEOUT, ID, id));
        return forkliftTimeoutMapper.toDto(forkliftTimeout);
    }

    @Override
    public List<ForkliftTimeoutDto> getAllByForkliftId(Long forkliftId, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return forkliftTimeoutRepository.findAll(sort).stream()
                .map(forkliftTimeoutMapper::toDto)
                .toList();
    }

    @Override
    public void add(ForkliftTimeoutDto dto) {
        Forklift forklift = forkliftRepository.findById(dto.getForkliftId())
                .orElseThrow(() -> new ResourceNotFoundException(FORKLIFT, ID, dto.getForkliftId()));
        ForkliftTimeout forkliftTimeout = forkliftTimeoutMapper.toEntity(dto, forklift);
        forkliftTimeoutRepository.save(forkliftTimeout);
    }

    @Override
    public void edit(Long id, ForkliftTimeoutDto dto) {
        ForkliftTimeout forkliftTimeout = forkliftTimeoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIMEOUT, ID, id));
        Forklift forklift = forkliftRepository.findById(dto.getForkliftId())
                .orElseThrow(() -> new ResourceNotFoundException(FORKLIFT, ID, dto.getForkliftId()));
        forkliftTimeout = forkliftTimeoutMapper.toEntityUpdate(forkliftTimeout, dto, forklift);
        forkliftTimeoutRepository.save(forkliftTimeout);

    }

    @Override
    public void delete(Long id) {
        ForkliftTimeout forkliftTimeout = forkliftTimeoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(TIMEOUT, ID, id));
        forkliftTimeoutRepository.deleteById(forkliftTimeout.getId());
    }
}
