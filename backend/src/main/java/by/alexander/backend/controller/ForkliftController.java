package by.alexander.backend.controller;

import by.alexander.backend.dto.ForkliftDto;
import by.alexander.backend.service.ForkliftService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forklift")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ForkliftController {

    ForkliftService forkliftService;

    @GetMapping("/{id}")
    public ResponseEntity<ForkliftDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(forkliftService.getById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ForkliftDto>> findById() {
        return ResponseEntity.ok(forkliftService.getAll());
    }

    @PostMapping
    public void add(@RequestBody ForkliftDto forkliftDto) {
        forkliftService.add(forkliftDto);
    }

    @PatchMapping("/{id}")
    public void edit(@PathVariable Long id,
                     @RequestBody ForkliftDto forkliftDto) {
        forkliftService.edit(id, forkliftDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        forkliftService.delete(id);
    }
}
