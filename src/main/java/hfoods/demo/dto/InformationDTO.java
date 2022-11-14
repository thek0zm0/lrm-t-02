package hfoods.demo.dto;

import hfoods.demo.entities.Information;
import hfoods.demo.entities.User;
import hfoods.demo.entities.enums.ActivityStatus;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
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
        userId = Optional.of(entity.getUser())
                .map(User::getId).orElse(null);
    }

    public static InformationDTO of(Information entity) {
        var informationDto = new InformationDTO();
        BeanUtils.copyProperties(entity, informationDto);
        return informationDto;
    }
}
