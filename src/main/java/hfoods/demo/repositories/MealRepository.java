package hfoods.demo.repositories;

import hfoods.demo.entities.Food;
import hfoods.demo.entities.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findAllByIdIn(List<Long> id);
}
