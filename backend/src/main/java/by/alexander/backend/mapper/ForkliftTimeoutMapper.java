package by.alexander.backend.mapper;

import by.alexander.backend.dto.ForkliftTimeoutDto;
import by.alexander.backend.entity.Forklift;
import by.alexander.backend.entity.ForkliftTimeout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ForkliftTimeoutMapper {

    ForkliftTimeoutDto toDto(ForkliftTimeout forkliftTimeout);

    @Mapping(target = "id", ignore = true)
    ForkliftTimeout toEntity(ForkliftTimeoutDto dto, Forklift forklift);
}
