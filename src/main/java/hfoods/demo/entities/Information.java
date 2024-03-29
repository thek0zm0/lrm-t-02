package hfoods.demo.entities;

import hfoods.demo.entities.enums.ActivityStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Builder
@Table(name = "tb_information")
public class Information implements Serializable {

    private static final long serialVersionUID = 9178661439383356177L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double height;
    private Double weight;
    private Integer age;
    private Character sex;
    private String createdDate;
    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;
    private Double basalMetabolicRate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateAge() {
        if (user.getBirthDate() != null) {
            this.age = Period.between(LocalDate.parse(user.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE), LocalDate.now()).getYears();
        }
    }

    public void updateBasalMetabolicRate() {
        var basalMetabolicRate = 0.0;
        if (getSex()=='M') {
            basalMetabolicRate = getActivityNumber() * (66 + (13.7 * weight) + (5 * height - (6.8 * age)));
        } else if (getSex()=='F') {
            basalMetabolicRate = getActivityNumber() * (655 + (9.6 * weight) + (1.8 * height - (4.7 * age)));
        }
        this.basalMetabolicRate = basalMetabolicRate;
    }

    public Double getActivityNumber() {
        if (activityStatus==ActivityStatus.LOW) {
            return 1.2;
        } else if (activityStatus==ActivityStatus.MEDIUM) {
            return 1.5;
        } else if (activityStatus==ActivityStatus.HIGH) {
            return 1.9;
        } else {
            return 0.0;
        }
    }
}
