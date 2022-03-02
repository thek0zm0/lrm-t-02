package hfoods.demo.entities.pk;

import hfoods.demo.entities.Food;
import hfoods.demo.entities.Meal;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class FoodItemPk implements Serializable {

    private static final long serialVersionUID = 9178660439383356177L;
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
}
