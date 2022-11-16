package hfoods.demo.services;

import hfoods.demo.dto.DietDTO;
import hfoods.demo.dto.MealDTO;
import hfoods.demo.dto.UserDTO;
import hfoods.demo.entities.Diet;
import hfoods.demo.repositories.DietRepository;
import hfoods.demo.repositories.MealRepository;
import hfoods.demo.repositories.UserRepository;
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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DietService {

    @Autowired
    private DietRepository dietRepository;

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MealService mealService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public DietDTO findById(Long id) {
        var obj = dietRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Diet not found"));
        List<MealDTO> meals = List.of();
        List<UserDTO> users = List.of();

        if (Objects.nonNull(obj.getMeals())) {
            meals = obj.getMeals().stream().map(uniq -> mealService.findById(uniq.getId())).collect(Collectors.toList());
        }

        if (Objects.nonNull(obj.getUsers())) {
            users = obj.getUsers().stream().map(uniq -> userService.findById(uniq.getId())).collect(Collectors.toList());
        }

        return new DietDTO(obj, users, meals);
    }

    @Transactional(readOnly = true)
    public Page<DietDTO> findAll(Pageable pageable) {
        var diets = dietRepository.findAll(pageable);

        return diets.map(obj -> {
            List<MealDTO> meals = List.of();
            List<UserDTO> users = List.of();

            if (Objects.nonNull(obj.getMeals())) {
                meals = obj.getMeals().stream().map(uniq -> mealService.findById(uniq.getId())).collect(Collectors.toList());
            }

            if (Objects.nonNull(obj.getUsers())) {
                users = obj.getUsers().stream().map(uniq -> userService.findById(uniq.getId())).collect(Collectors.toList());
            }

            return new DietDTO(obj, users, meals);
        });
    }

    @Transactional
    public DietDTO insert(DietDTO dto) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());
        var diet = new Diet();
        copyDtoToEntity(dto, diet);
        diet = dietRepository.save(diet);
        return new DietDTO(diet);
    }

    @Transactional
    public DietDTO update(Long id, DietDTO dto) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());

        try {
            Diet entity = dietRepository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity.setId(id);
            entity = dietRepository.save(entity);
            return new DietDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional
    public void insertMeals(Long dietId, Long mealId) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());

        System.out.println(mealId);

        var diet = dietRepository.findById(dietId).orElseThrow(() -> new ResourceNotFoundException("Diet not found"));
        var meal = mealRepository.findById(mealId).orElseThrow(() -> new ResourceNotFoundException("Meal not found"));

        meal.setDiet(diet);

        dietRepository.save(diet);
        mealRepository.save(meal);
    }

    @Transactional
    public void delete(Long id) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());

        try {
            findById(id);
            dietRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    @Transactional
    public void insertUsers(Long dietId, Long userId) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());

        System.out.println(userId);

        var diet = dietRepository.findById(dietId).orElseThrow(() -> new ResourceNotFoundException("Diet not found"));
        var userClient = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setDiet(diet);

        dietRepository.save(diet);
        userRepository.save(userClient);
    }

    @Transactional(readOnly = true)
    public Page<DietDTO> dietForCurrentyUser(Pageable pageable) {
        var user = authService.authenticated();
        var diets = dietRepository.findByUsers(user, pageable);

        return diets.map(obj -> {
            List<MealDTO> meals = List.of();
            List<UserDTO> users = List.of();

            if (Objects.nonNull(obj.getMeals())) {
                meals = obj.getMeals().stream().map(uniq -> mealService.findById(uniq.getId())).collect(Collectors.toList());
            }

            if (Objects.nonNull(obj.getUsers())) {
                users = obj.getUsers().stream().map(uniq -> userService.findById(uniq.getId())).collect(Collectors.toList());
            }

            return new DietDTO(obj, users, meals);
        });
    }

    private void copyDtoToEntity(DietDTO dto, Diet entity) {
        BeanUtils.copyProperties(dto, entity);
    }
}
