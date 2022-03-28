package hfoods.demo.resources;

import hfoods.demo.dto.FoodDTO;
import hfoods.demo.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/food")
public class FoodResource {

    @Autowired
    private FoodService foodService;

    @GetMapping("/{id}")
    public ResponseEntity<FoodDTO> findFoodById(@PathVariable Long id) {
        return ResponseEntity.ok().body(foodService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<FoodDTO>> findAllFood(Pageable pageable) {
        return ResponseEntity.ok().body(foodService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<FoodDTO> insertFood(@RequestBody FoodDTO dto) {
        dto = foodService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FoodDTO> updateFood(@PathVariable Long id, @RequestBody FoodDTO dto) {
        dto = foodService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        foodService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
