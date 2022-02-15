package hfoods.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_diet")
public class Diet implements Serializable {

    private static final long serialVersionUID = 9178661439383356177L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private Double totalCalories;
    private Double totalProtein;
    private Double totalCarbohydrates;
    private Double totalFat;
    private Double totalSodium;
    private Double totalSugar;
    private Double totalVitaminA;
    private Double totalVitaminC;
    private Double totalIron;

    @OneToMany(mappedBy = "diet")
    private List<User> users;
}
