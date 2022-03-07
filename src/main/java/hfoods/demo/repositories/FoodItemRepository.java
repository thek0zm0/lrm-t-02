package hfoods.demo.repositories;

import hfoods.demo.entities.FoodItem;
import hfoods.demo.entities.pk.FoodItemPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, FoodItemPk> {
}
