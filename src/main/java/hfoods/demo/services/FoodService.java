package hfoods.demo.services;

import hfoods.demo.dto.FoodDTO;
import hfoods.demo.entities.Food;
import hfoods.demo.repositories.FoodItemRepository;
import hfoods.demo.repositories.FoodRepository;
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

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Transactional(readOnly = true)
    public FoodDTO findById(Long id) {
        var obj = foodRepository.findById(id);

        return new FoodDTO(obj.orElseThrow(() -> new ResourceNotFoundException("Food not found")));
    }

    @Transactional(readOnly = true)
    public Page<FoodDTO> findAll(Pageable pageable) {
        var foods = foodRepository.findAll(pageable);

        return foods.map(FoodDTO::new);
    }

    @Transactional
    public FoodDTO insert(FoodDTO dto) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());
        var food = new Food();
        copyDtoToEntity(dto, food);
        food = foodRepository.save(food);
        return new FoodDTO(food);
    }

    @Transactional
    public void delete(Long id) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());

        try {
            deleteFoodItem(id);
            foodRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void deleteFoodItem(Long id) {
        var food = foodRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Food not found"));
        var foodItems = foodItemRepository.findByItemPkFood(food);
        foodItemRepository.deleteAll(foodItems);
    }

    @Transactional
    public FoodDTO update(Long id, FoodDTO dto) {
        var user = authService.authenticated();
        authService.validateAdminOrNutritionist(user.getId());
        try {
            var food = foodRepository.getOne(id);
            copyDtoToEntity(dto, food);
            food.setId(id);
            food = foodRepository.save(food);
            return new FoodDTO(food);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Food not found. ID: " + id);
        }
    }

    private void copyDtoToEntity(FoodDTO dto, Food entity) {
        BeanUtils.copyProperties(dto, entity);
    }
}
