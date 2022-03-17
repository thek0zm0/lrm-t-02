package hfoods.demo.dto;

import hfoods.demo.entities.Information;
import hfoods.demo.entities.enums.ActivityStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class InformationDTO {

    private Long id;
    private Double height;
    private Double weight;
    private Integer age;
    private Character sex;
    private String createdDate;
    private ActivityStatus activityStatus;
    private Double basalMetabolicRate;
    private Long userId;

    public InformationDTO(Information entity) {
        id = entity.getId();
        height = entity.getHeight();
        weight = entity.getWeight();
        age = entity.getAge();
        sex = entity.getSex();
        createdDate = entity.getCreatedDate();
        activityStatus = entity.getActivityStatus();
        basalMetabolicRate = entity.getBasalMetabolicRate();
        userId = entity.getUser().getId();
    }
}
