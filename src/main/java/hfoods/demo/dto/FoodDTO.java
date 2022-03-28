package hfoods.demo.dto;

import hfoods.demo.entities.Food;
import hfoods.demo.entities.enums.FoodGroup;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class FoodDTO {

    private Long id;
    private String name;
    private FoodGroup foodGroup;
    private String imgUrl;
    private Double quantity;
    private Double calorie;
    private Double protein;
    private Double carbohydrate;
    private Double fat;
    private Double sodium;
    private Double sugar;
    private Double vitaminA;
    private Double vitaminC;
    private Double iron;

    public FoodDTO(Food entity) {
        id = entity.getId();
        name = entity.getName();
        foodGroup = entity.getFoodGroup();
        imgUrl = entity.getImgUrl();
        quantity = entity.getQuantity();
        calorie = entity.getCalorie();
        protein = entity.getProtein();
        carbohydrate = entity.getCarbohydrate();
        fat = entity.getFat();
        sodium = entity.getSodium();
        sugar = entity.getSugar();
        vitaminA = entity.getVitaminA();
        vitaminC = entity.getVitaminC();
        iron = entity.getIron();
    }
}
