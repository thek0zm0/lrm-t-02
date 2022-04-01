package hfoods.demo.dto;

import hfoods.demo.entities.Meal;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class MealDTO {

    private Long id;
    private String name;
    private String timeHour;

    public MealDTO(Meal entity) {
        id = entity.getId();
        name = entity.getName();
        timeHour = entity.getTimeHour();
    }
}
