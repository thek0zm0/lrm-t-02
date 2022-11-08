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
    public ResponseEntity<DietDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(dietService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DietDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(dietService.findAll(pageable));
    }

    @GetMapping
    public ResponseEntity<Page<DietDTO>> dietForCurrentUser(Pageable pageable) {
        var page = dietService.dietForCurrentyUser(pageable);
        return ResponseEntity.ok().body(page);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    public ResponseEntity<DietDTO> insert(@RequestBody DietDTO dto) {
        dto = dietService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    public ResponseEntity<DietDTO> update(@PathVariable Long id, @RequestBody DietDTO dto) {
        dto = dietService.update(id, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/insert-meal/{dietId}/{mealId}")
    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    public ResponseEntity<Void> insertMealInDiet(@PathVariable Long dietId,
                                                 @PathVariable Long mealId) {
        dietService.insertMeals(dietId, mealId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/insert-user/{dietId}/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    public ResponseEntity<Void> insertUserInDiet(@PathVariable Long dietId,
                                                 @PathVariable Long userId) {
        dietService.insertUsers(dietId, userId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dietService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
