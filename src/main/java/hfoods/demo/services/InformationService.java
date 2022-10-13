package hfoods.demo.services;

import hfoods.demo.dto.InformationDTO;
import hfoods.demo.entities.Information;
import hfoods.demo.repositories.InformationRepository;
import hfoods.demo.services.exceptions.DatabaseException;
import hfoods.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

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

    @Transactional
    public InformationDTO insert(InformationDTO dto) {
        var user = authService.authenticated();

        var information = new Information();
        copyDtoToEntity(dto, information);
        information.setUser(user);
        information.updateAge();
        information.updateBasalMetabolicRate();
        information.setCreatedDate(LocalDateTime.now().toString());
        information = informationRepository.save(information);
        user.addInformation(information);
        return new InformationDTO(information);
    }

    @Transactional
    public InformationDTO update(Long id, InformationDTO dto) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());
        try {
            var information = informationRepository.getOne(id);
            copyDtoToEntity(dto, information);
            information.setUser(information.getUser());
            information.setId(id);
            information.updateAge();
            information.updateBasalMetabolicRate();
            information.setCreatedDate(LocalDateTime.now().toString());
            information = informationRepository.save(information);
            return new InformationDTO(information);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("information not found. ID: " + id);
        }
    }

    private void copyDtoToEntity(InformationDTO dto, Information entity) {
        BeanUtils.copyProperties(dto, entity);
    }

    @Transactional
    public void delete(Long id) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());

        try {
            informationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Information not found"));
            informationRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
