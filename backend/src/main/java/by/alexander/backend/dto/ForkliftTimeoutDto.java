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
public class ForkliftTimeoutDto {

    Long id;

    @NotNull(message = "Дата обнурожения проблемы не может быть пустой")
    String detectedDate;

    String solutionDate;

    String timeout;

    @NotNull(message = "Описание не может быть пустым")
    String description;

    @NotNull(message = "Id погрузчика не можеть быть пустым")
    Long forkliftId;
}
