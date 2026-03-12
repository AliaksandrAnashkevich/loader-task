package by.alexander.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ForkliftDto {

    Long id;

    @NotNull(message = "Марка не может быть пустой")
    String brand;

    @NotNull(message = "Номер не может быть пустым")
    String number;

    @NotNull(message = "Грузоподъемность не может быть пустой")
    String loadCapacity;

    Boolean isActive;
    String updateDate;
    String fio;
}
