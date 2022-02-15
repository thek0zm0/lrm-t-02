package hfoods.demo.entities.pk;

import hfoods.demo.entities.Diet;
import hfoods.demo.entities.Food;
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
public class MealPk implements Serializable {

    private static final long serialVersionUID = 9178661439383356177L;
    @ManyToOne
    @JoinColumn(name = "diet_id")
    private Diet diet;
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;
}
