package by.alexander.backend.controller;

import by.alexander.backend.dto.ForkliftTimeoutDto;
import by.alexander.backend.service.ForkliftTimeoutService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeout")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ForkliftTimeoutController {

    ForkliftTimeoutService forkliftTimeoutService;

    @GetMapping("/{id}")
    public ResponseEntity<ForkliftTimeoutDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(forkliftTimeoutService.getById(id));
    }

    @GetMapping("/forklift/{forkliftId}")
    public ResponseEntity<List<ForkliftTimeoutDto>> getByForklift(@PathVariable Long forkliftId,
                                                                  @RequestParam(defaultValue = "detectedDate") String sortBy,
                                                                  @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.ok(forkliftTimeoutService.getAllByForkliftId(forkliftId, sortBy, direction));
    }

    @PostMapping
    public void add(@RequestBody ForkliftTimeoutDto dto) {
        forkliftTimeoutService.add(dto);
    }

    @PutMapping("/{id}")
    public void edit(@PathVariable Long id, @RequestBody ForkliftTimeoutDto dto) {
        forkliftTimeoutService.edit(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        forkliftTimeoutService.delete(id);
    }
}
