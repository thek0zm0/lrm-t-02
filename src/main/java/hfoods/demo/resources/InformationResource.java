package hfoods.demo.resources;

import hfoods.demo.dto.InformationDTO;
import hfoods.demo.services.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/information")
public class InformationResource {

    @Autowired
    private InformationService informationService;

    @GetMapping
    public ResponseEntity<Page<InformationDTO>> informationForCurrentUser(Pageable pageable) {
        var page = informationService.informationForCurrentyUser(pageable);
        return ResponseEntity.ok().body(page);
    }

    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    @GetMapping("/all")
    public ResponseEntity<Page<InformationDTO>> informationForAllUsers(Pageable pageable) {
        var page = informationService.informationForAllUsers(pageable);
        return ResponseEntity.ok().body(page);
    }

    @PostMapping
    public ResponseEntity<InformationDTO> insertInformation(@RequestBody InformationDTO dto) {
        dto = informationService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','NUTRITIONIST')")
    public ResponseEntity<InformationDTO> updateInformation(@PathVariable Long id, @RequestBody InformationDTO dto) {
        dto = informationService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
}
