package hfoods.demo.dto;

import hfoods.demo.entities.Diet;
import lombok.*;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class DietDTO {

    private Long id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private List<UserDTO> users;
    private List<MealDTO> meals;

    public DietDTO(Diet entity, List<UserDTO> usersDtos, List<MealDTO> mealsDtos) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        startDate = LocalDateTime.now().toString();
        endDate = LocalDateTime.now().plusMonths(6).toString();
        users = usersDtos;
        meals = mealsDtos;
    }

    public DietDTO(Diet entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        startDate = LocalDateTime.now().toString();
        endDate = LocalDateTime.now().plusMonths(6).toString();
    }
}
