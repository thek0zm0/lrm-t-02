package hfoods.demo.services;

import hfoods.demo.dto.FoodDTO;
import hfoods.demo.dto.MealDTO;
import hfoods.demo.entities.Food;
import hfoods.demo.entities.FoodItem;
import hfoods.demo.entities.Meal;
import hfoods.demo.repositories.FoodItemRepository;
import hfoods.demo.repositories.FoodRepository;
import hfoods.demo.repositories.MealRepository;
import hfoods.demo.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Transactional(readOnly = true)
    public MealDTO findById(Long id) {
        var obj = mealRepository.findById(id);

        return new MealDTO(obj.orElseThrow(() -> new ResourceNotFoundException("Meal not found")));
    }

    public Page<MealDTO> findAll(Pageable pageable) {
        var meals = mealRepository.findAll(pageable);

        return meals.map(MealDTO::new);
    }

    public void insertFood(Long mealId, Long foodId) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());

        var meal = mealRepository.findById(mealId).orElseThrow(() -> new ResourceNotFoundException("Meal not found"));
        var food = foodRepository.findById(foodId).orElseThrow(() -> new ResourceNotFoundException("Food not found"));

        var foodItem = new FoodItem(meal, food);
        foodItem.setQuantity(food.getQuantity());
        foodItem.setTotalCalories(food.getQuantity() * food.getCalorie());

        foodItemRepository.save(foodItem);
        foodRepository.save(food);
        mealRepository.save(meal);
    }

    @Transactional
    public MealDTO update(Long id, MealDTO dto) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());
        try {
            var meal = mealRepository.getOne(id);
            dto.setTimeHour(LocalDateTime.now().toString());
            copyDtoToEntity(dto, meal);
            meal.setId(id);
            meal = mealRepository.save(meal);
            return new MealDTO(meal);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Food not found. ID: " + id);
        }
    }

    public MealDTO insert(MealDTO dto) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());
        var meal = new Meal();
        copyDtoToEntity(dto, meal);
        meal.setTimeHour(LocalDateTime.now().toString());
        meal = mealRepository.save(meal);
        return new MealDTO(meal);
    }

    private void copyDtoToEntity(MealDTO dto, Meal entity) {
        BeanUtils.copyProperties(dto, entity);
    }
}
