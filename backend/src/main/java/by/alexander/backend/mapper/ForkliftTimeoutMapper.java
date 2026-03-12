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

import static by.alexander.backend.util.DateUtil.*;

@Mapper(componentModel = "spring")
public interface ForkliftTimeoutMapper {

    @Mapping(target = "timeout", source = "forkliftTimeout", qualifiedByName = "mapTimeout")
    @Mapping(target = "forkliftId", source = "forkliftTimeout.forklift.id")
    @Mapping(target = "detectedDate", source = "detectedDate", dateFormat = MESSAGE_LOCAL_DATE_TIME_FORMAT)
    @Mapping(target = "solutionDate", source = "solutionDate", dateFormat = MESSAGE_LOCAL_DATE_TIME_FORMAT)
    ForkliftTimeoutDto toDto(ForkliftTimeout forkliftTimeout);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "detectedDate", source = "dto.detectedDate", dateFormat = LOCAL_DATE_TIME_FORMAT)
    @Mapping(target = "solutionDate", source = "dto.solutionDate", dateFormat = LOCAL_DATE_TIME_FORMAT)
    ForkliftTimeout toEntity(ForkliftTimeoutDto dto, Forklift forklift);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "detectedDate", source = "dto.detectedDate", dateFormat = LOCAL_DATE_TIME_FORMAT)
    @Mapping(target = "solutionDate", source = "dto.solutionDate", dateFormat = LOCAL_DATE_TIME_FORMAT)
    ForkliftTimeout toEntityUpdate(@MappingTarget ForkliftTimeout target, ForkliftTimeoutDto dto, Forklift forklift);

    @Named("mapTimeout")
    default String mapTimeout(ForkliftTimeout forkliftTimeout) {
        LocalDateTime start = forkliftTimeout.getDetectedDate();
        LocalDateTime end = forkliftTimeout.getSolutionDate() != null ? forkliftTimeout.getSolutionDate() : LocalDateTime.now();

        if (start == null){
            return "";
        }

        Duration duration = Duration.between(start, end);

        Long hours = duration.toHours();
        Long minutes = duration.toMinutes() % 60;

        return String.format(TIMEOUT_MESSAGE, hours, minutes);
    }
}
