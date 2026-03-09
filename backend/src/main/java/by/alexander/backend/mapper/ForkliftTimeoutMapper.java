package by.alexander.backend.mapper;

import by.alexander.backend.dto.ForkliftTimeoutDto;
import by.alexander.backend.entity.Forklift;
import by.alexander.backend.entity.ForkliftTimeout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.Duration;
import java.time.LocalDateTime;

import static by.alexander.backend.util.DateUtil.TIMEOUT_MESSAGE;

@Mapper(componentModel = "spring")
public interface ForkliftTimeoutMapper {

    @Mapping(target = "timeout", qualifiedByName = "mapTimeout")
    ForkliftTimeoutDto toDto(ForkliftTimeout forkliftTimeout);

    @Mapping(target = "id", ignore = true)
    ForkliftTimeout toEntity(ForkliftTimeoutDto dto, Forklift forklift);

    @Mapping(target = "id", ignore = true)
    ForkliftTimeout toEntityUpdate(@MappingTarget ForkliftTimeout target, ForkliftTimeoutDto dto, Forklift forklift);

    @Named("mapStatementTypeId")
    default String mapTimeout(ForkliftTimeout timeout) {
        LocalDateTime start = timeout.getDetectedDate();
        LocalDateTime end = timeout.getDetectedDate() != null ? timeout.getDetectedDate() : LocalDateTime.now();

        Duration duration = Duration.between(start, end);

        Long hours = duration.toHours();
        Long minutes = duration.toMinutes() % 60;

        return String.format(TIMEOUT_MESSAGE, hours, minutes);
    }
}
