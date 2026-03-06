package by.alexander.backend.service.impl;

import by.alexander.backend.dto.ForkliftTimeoutDto;
import by.alexander.backend.service.ForkliftTimeoutService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ForkliftTimeoutServiceImpl implements ForkliftTimeoutService {


    @Override
    public ForkliftTimeoutDto getById(Long id) {
        return null;
    }

    @Override
    public List<ForkliftTimeoutDto> getAllByForkliftId(Long forkliftId) {
        return List.of();
    }

    @Override
    public void add(ForkliftTimeoutDto forkliftTimeoutDto) {

    }

    @Override
    public void edit(Long id, ForkliftTimeoutDto forkliftTimeoutDto) {

    }

    @Override
    public void delete(Long id) {

    }
}
