package by.alexander.backend.dto;

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
    String detectedDate;
    String solutionDate;
    String timeout;
    String description;
    Long forkliftId;
}
