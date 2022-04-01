package hfoods.demo.entities;

import hfoods.demo.entities.enums.FoodGroup;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "tb_food")
public class Food implements Serializable {

    private static final long serialVersionUID = 9178661439383356177L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private FoodGroup foodGroup;
    private String imgUrl;
    private Integer quantity;
    private Double calorie;
    private Double protein;
    private Double carbohydrate;
    private Double fat;
    private Double sodium;
    private Double sugar;
    private Double vitaminA;
    private Double vitaminC;
    private Double iron;
}
