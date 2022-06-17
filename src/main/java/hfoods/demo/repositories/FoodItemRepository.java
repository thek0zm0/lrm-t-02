package hfoods.demo.repositories;

import hfoods.demo.entities.Food;
import hfoods.demo.entities.FoodItem;
import hfoods.demo.entities.Meal;
import hfoods.demo.entities.pk.ItemPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, ItemPk> {
    List<FoodItem> findByItemPkMeal(Meal meal);

    List<FoodItem> findByItemPkFood(Food food);
}
