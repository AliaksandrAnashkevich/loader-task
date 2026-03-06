package by.alexander.backend.service.impl;

import by.alexander.backend.dto.ForkliftDto;
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


    @Override
    public ForkliftDto getById(Long id) {
        return null;
    }

    @Override
    public List<ForkliftDto> getAll() {
        return List.of();
    }

    @Override
    public void add(ForkliftDto forkliftDto) {

    }

    @Override
    public void edit(Long id, ForkliftDto forkliftDto) {

    }

    @Override
    public void delete(Long id) {

    }
}
