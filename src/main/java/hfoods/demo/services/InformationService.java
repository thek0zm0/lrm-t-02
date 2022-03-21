package hfoods.demo.services;

import hfoods.demo.dto.InformationDTO;
import hfoods.demo.repositories.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InformationService {

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public Page<InformationDTO> informationForCurrentyUser(Pageable pageable) {
        var user = authService.authenticated();
        var information = informationRepository.findByUser(user, pageable);

        return information.map(InformationDTO::new);
    }

    @Transactional(readOnly = true)
    public Page<InformationDTO> informationForAllUsers(Pageable pageable) {
        var information = informationRepository.findAll(pageable);

        return information.map(InformationDTO::new);
    }
}
