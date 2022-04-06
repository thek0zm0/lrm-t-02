package hfoods.demo.resources;

import hfoods.demo.dto.MealDTO;
import hfoods.demo.services.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/meal")
public class MealResource {

    @Autowired
    private MealService mealService;

    @GetMapping("/{id}")
    public ResponseEntity<MealDTO> findMealById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mealService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<MealDTO>> findAllMeal(Pageable pageable) {
        return ResponseEntity.ok().body(mealService.findAll(pageable));
    }

    @PutMapping("/{mealId}/{foodId}")
    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    public ResponseEntity<Void> insertFoodInMeal(@PathVariable Long mealId, @PathVariable Long foodId) {
        mealService.insertFood(mealId, foodId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    public ResponseEntity<MealDTO> insertMeal(@RequestBody MealDTO dto) {
        dto = mealService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    public ResponseEntity<MealDTO> updateMeal(@PathVariable Long id, @RequestBody MealDTO dto) {
        dto = mealService.update(id, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
