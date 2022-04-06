package hfoods.demo.services;

import hfoods.demo.dto.DietDTO;
import hfoods.demo.dto.MealDTO;
import hfoods.demo.dto.UserDTO;
import hfoods.demo.entities.Diet;
import hfoods.demo.repositories.DietRepository;
import hfoods.demo.repositories.MealRepository;
import hfoods.demo.repositories.UserRepository;
import hfoods.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            users = obj.getUsers().stream().map(uniq -> userService.findbyId(uniq.getId())).collect(Collectors.toList());
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
                users = obj.getUsers().stream().map(uniq -> userService.findbyId(uniq.getId())).collect(Collectors.toList());
            }

            return new DietDTO(obj, users, meals);
        });
    }

    public DietDTO insert(DietDTO dto) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());
        var diet = new Diet();
        copyDtoToEntity(dto, diet);
        diet = dietRepository.save(diet);
        return new DietDTO(diet);
    }

    public void insertMeals(Long dietId, List<Long> mealIds) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());

        System.out.println(mealIds);

        var diet = dietRepository.findById(dietId).orElseThrow(() -> new ResourceNotFoundException("Diet not found"));
        var meals = mealRepository.findAllByIdIn(mealIds);

        diet.setMeals(meals);

        dietRepository.save(diet);
        meals.forEach(meal -> mealRepository.save(meal));
    }

    public DietDTO insertUsers(DietDTO dto) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());
        var diet = new Diet();
        copyDtoToEntity(dto, diet);
        diet = dietRepository.save(diet);
        return new DietDTO(diet);
    }

    private void copyDtoToEntity(DietDTO dto, Diet entity) {
        BeanUtils.copyProperties(dto, entity);
    }
}
