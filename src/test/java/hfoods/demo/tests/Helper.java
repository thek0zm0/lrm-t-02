package hfoods.demo.tests;

import hfoods.demo.dto.FoodDTO;
import hfoods.demo.dto.InformationDTO;
import hfoods.demo.entities.Food;
import hfoods.demo.entities.Information;
import hfoods.demo.entities.Role;
import hfoods.demo.entities.User;
import hfoods.demo.entities.enums.ActivityStatus;
import hfoods.demo.entities.enums.FoodGroup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Helper {

    public static Information umaInformation(Long id) {
        return Information.builder()
                .id(id)
                .sex('M')
                .activityStatus(ActivityStatus.MEDIUM)
                .age(20)
                .createdDate(LocalDate.now().minusMonths(6).toString())
                .weight(90.0)
                .height(1.80)
                .basalMetabolicRate(1600D)
                .user(umUsuario("ROLE_ADMIN"))
                .build();
    }

    public static InformationDTO umaInformationDTO(Long id) {
        return InformationDTO.builder()
                .id(id)
                .sex('M')
                .activityStatus(ActivityStatus.MEDIUM)
                .age(20)
                .createdDate(LocalDate.now().minusMonths(6).toString())
                .weight(90.0)
                .height(1.80)
                .basalMetabolicRate(1600D)
                .build();
    }

    public static Food umFood(Long id) {
        return Food.builder()
                .id(1L)
                .name("Food test")
                .calorie(120D)
                .protein(10D)
                .carbohydrate(5D)
                .fat(1D)
                .quantity(50)
                .sugar(1D)
                .sodium(2D)
                .vitaminA(0.1D)
                .vitaminC(0.2D)
                .iron(0.5D)
                .imgUrl("url/test")
                .foodGroup(FoodGroup.INNATURA)
                .build();
    }

    public static FoodDTO umFoodDTO(Long id) {
        return FoodDTO.builder()
                .id(1L)
                .name("Food test")
                .calorie(120D)
                .protein(10D)
                .carbohydrate(5D)
                .fat(1D)
                .quantity(50)
                .sugar(1D)
                .sodium(2D)
                .vitaminA(0.1D)
                .vitaminC(0.2D)
                .iron(0.5D)
                .imgUrl("url/test")
                .foodGroup(FoodGroup.INNATURA)
                .build();
    }

    public static User umUsuario(String roleName) {
        return User.builder()
                .id(1L)
                .name("Usuario teste")
                .cpf("34523412398")
                .email("emailteste@test.com")
                .phone("1174329468")
                .roles(Set.of(umaRole(roleName)))
                .information(new ArrayList<>(List.of()))
                .build();
    }

    public static Role umaRole(String roleName) {
        return Role.builder()
                .id(1L)
                .authority(roleName)
                .build();
    }

}
