package by.alexander.backend.mapper;

import by.alexander.backend.dto.ForkliftDto;
import by.alexander.backend.entity.Forklift;
import by.alexander.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ForkliftMapper {

    @Mapping(target = "fio", source = "user.fio")
    ForkliftDto toDto(Forklift forklift);

    @Mapping(target = "updateDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "user", source = "user")
    Forklift toEntity(ForkliftDto forklift, User user);
}
