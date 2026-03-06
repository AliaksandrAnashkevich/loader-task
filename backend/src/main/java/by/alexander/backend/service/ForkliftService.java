package by.alexander.backend.service;

import by.alexander.backend.dto.ForkliftDto;

import java.util.List;

public interface ForkliftService {

    ForkliftDto getById(Long id);

    List<ForkliftDto> getAll();

    void add(ForkliftDto forkliftDto);

    void edit(Long id, ForkliftDto forkliftDto);

    void delete(Long id);
}
