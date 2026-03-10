package by.alexander.backend.mapper;

import by.alexander.backend.dto.ForkliftDto;
import by.alexander.backend.entity.Forklift;
import by.alexander.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static by.alexander.backend.util.DateUtil.LOCAL_DATE_TIME_FORMAT;

@Mapper(componentModel = "spring")
public interface ForkliftMapper {

    @Mapping(target = "fio", source = "user.fio")
    @Mapping(target = "loadCapacity", source = "loadCapacity", qualifiedByName = "mapLoadCapacity")
    @Mapping(target = "updateDate", source = "updateDate", dateFormat = LOCAL_DATE_TIME_FORMAT)
    ForkliftDto toDto(Forklift forklift);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "isActive", source = "isActive")
    Forklift toEntity(ForkliftDto forklift,
                      Boolean isActive,
                      User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updateDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "user", source = "user")
    Forklift toEntityUpdate(@MappingTarget Forklift entity,
                            ForkliftDto forklift,
                            User user);

    @Named("mapLoadCapacity")
    default String mapLoadCapacity(Double loadCapacity) {
        return new BigDecimal(loadCapacity).setScale(3, RoundingMode.HALF_UP).toString();
    }
}
