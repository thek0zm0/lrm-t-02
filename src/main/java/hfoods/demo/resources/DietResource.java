package hfoods.demo.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import java.util.List;

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

    @PutMapping("/{dietId}")
    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    // arrumar essa porcaria
    public ResponseEntity<Void> insertMealInDiet(@PathVariable Long dietId,
                                                 @RequestBody @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                                                 @JsonProperty("mealsIds")
                                                         List<Long> mealIds) {
        dietService.insertMeals(dietId, mealIds);

        return ResponseEntity.noContent().build();
    }
}
