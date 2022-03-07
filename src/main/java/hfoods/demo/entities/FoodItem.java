package hfoods.demo.entities;

import hfoods.demo.entities.pk.FoodItemPk;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_foodItem")
public class FoodItem {

    @EmbeddedId
    private FoodItemPk id = new FoodItemPk();
    private Integer quantity;
    private Double totalCalories;

    public FoodItem(Meal meal, Food food) {
        id.setMeal(meal);
        id.setFood(food);
        this.quantity = quantity;
        this.totalCalories = totalCalories;
    }

    public Meal getMeal() {
        return id.getMeal();
    }

    public Food getFood() {
        return id.getFood();
    }
}
