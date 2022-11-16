package hfoods.demo.dto;

import hfoods.demo.entities.Meal;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class MealDTO {

    private Long id;
    private String name;
    private String timeHour;
    private List<FoodDTO> foods;

    public MealDTO(Meal entity, List<FoodDTO> entityFoods) {
        id = entity.getId();
        name = entity.getName();
        timeHour = entity.getTimeHour();
        foods = entityFoods;
    }

    public MealDTO(Meal entity) {
        id = entity.getId();
        name = entity.getName();
        timeHour = entity.getTimeHour();
    }
}
