package hfoods.demo.resources;

import hfoods.demo.dto.FoodDTO;
import hfoods.demo.dto.RoleDTO;
import hfoods.demo.services.FoodService;
import hfoods.demo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleResource {

    @Autowired
    private RoleService foodService;

    @GetMapping("/all")
    public ResponseEntity<Page<RoleDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok().body(foodService.findAll(pageable));
    }
}
