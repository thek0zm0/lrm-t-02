package hfoods.demo.resources;

import hfoods.demo.dto.InformationDTO;
import hfoods.demo.services.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
