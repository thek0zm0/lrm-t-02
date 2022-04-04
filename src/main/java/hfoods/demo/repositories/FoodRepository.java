package hfoods.demo.repositories;

import hfoods.demo.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByIdIn(List<Long> id);
}
