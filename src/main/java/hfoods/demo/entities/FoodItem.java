package hfoods.demo.entities;

import hfoods.demo.entities.pk.ItemPk;
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
    private ItemPk itemPk = new ItemPk();
    private Integer quantity;
    private Double totalCalories;

    public FoodItem(Meal meal, Food food) {
        itemPk.setMeal(meal);
        itemPk.setFood(food);
    }

    public Meal getMeal() {
        return itemPk.getMeal();
    }

    public Food getFood() {
        return itemPk.getFood();
    }
}
