package by.alexander.backend.mapper;

import by.alexander.backend.dto.ForkliftDto;
import by.alexander.backend.entity.Forklift;
import by.alexander.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ForkliftMapper {

    @Mapping(target = "fio", source = "user.fio")
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
}
