package hfoods.demo.resources;

import hfoods.demo.dto.DietDTO;
import hfoods.demo.services.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/diet")
public class DietResource {

    @Autowired
    private DietService dietService;

    @GetMapping("/{id}")
    public ResponseEntity<DietDTO> findMealById(@PathVariable Long id) {
        return ResponseEntity.ok().body(dietService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DietDTO>> findAllMeal(Pageable pageable) {
        return ResponseEntity.ok().body(dietService.findAll(pageable));
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    public ResponseEntity<DietDTO> insertDiet(@RequestBody DietDTO dto) {
        dto = dietService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
