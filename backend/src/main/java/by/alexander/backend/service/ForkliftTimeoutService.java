package by.alexander.backend.service;

import by.alexander.backend.dto.ForkliftTimeoutDto;

import java.util.List;

public interface ForkliftTimeoutService {

    ForkliftTimeoutDto getById(Long id);

    List<ForkliftTimeoutDto> getAllByForkliftId(Long forkliftId, String sortBy, String direction);

    void add(ForkliftTimeoutDto forkliftTimeoutDto);

    void edit(Long id, ForkliftTimeoutDto forkliftTimeoutDto);

    void delete(Long id);
}
