package hfoods.demo.entities;

import hfoods.demo.entities.pk.MealPk;
import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_meal")
public class Meal implements Serializable {

    private static final long serialVersionUID = 9178661439383356177L;
    @EmbeddedId
    private MealPk id = new MealPk();
    private String name;

    public Meal(Diet diet, Food food, String name) {
        id.setDiet(diet);
        id.setFood(food);
        this.name = name;
    }

    public Diet getDiet() {
        return id.getDiet();
    }

    public void setDiet(Diet diet) {
        id.setDiet(diet);
    }

    public Food getFood() {
        return id.getFood();
    }

    public void setFood(Food food) {
        id.setFood(food);
    }
}
