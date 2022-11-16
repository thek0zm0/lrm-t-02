package hfoods.demo.tests;

import hfoods.demo.dto.*;
import hfoods.demo.entities.*;
import hfoods.demo.entities.enums.ActivityStatus;
import hfoods.demo.entities.enums.FoodGroup;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
                .id(id)
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

    public static Meal umaMeal(Long id) {
        return Meal.builder()
                .id(id)
                .name("Uma Refeição Teste")
                .timeHour(LocalDateTime.now().toString())
                .build();
    }

    public static MealDTO umaMealDTO(Long id) {
        return MealDTO.builder()
                .id(id)
                .name("Uma Refeição Teste")
                .timeHour("")
                .foods(new ArrayList<>(List.of(umFoodDTO(1L))))
                .build();
    }

    public static DietDTO umaDietDTO(Long id) {
        return DietDTO.builder()
                .id(id)
                .name("Dieta teste")
                .description("Descrição dieta teste")
                .startDate("")
                .endDate("")
                .build();
    }

    public static Diet umaDiet(Long id) {
        return Diet.builder()
                .id(id)
                .name("Dieta teste")
                .description("Descrição dieta teste")
                .startDate("")
                .endDate("")
                .users(new ArrayList<>(List.of(umUsuario("ROLE_ADMIN"))))
                .meals(new ArrayList<>(List.of(umaMeal(1L))))
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

    public static UserDTO umUserDTO(Long id) {
        return UserDTO.builder()
                .id(1L)
                .name("Usuario teste")
                .cpf("34523412398")
                .email("emailteste@test.com")
                .phone("1174329468")
                .roles(Set.of(umaRoleDto("ROLE_ADMIN")))
                .build();
    }

    public static UserInsertDTO umUserInsertDTO(Long id, String roleName) {
        var user = new UserInsertDTO();
        user.setId(id);
        user.setName("Usuario teste");
        user.setPassword("testeteste");
        user.setCpf("34523412398");
        user.setEmail("emailteste@test.com");
        user.setPhone("1174329468");
        user.setRoles(Set.of(umaRoleDto(roleName)));
        return user;
    }

    public static UserUpdateDTO umUserUpdateDTO(Long id, String roleName) {
        var user = new UserUpdateDTO();
        user.setId(id);
        user.setName("Usuario teste");
        user.setCpf("34523412398");
        user.setEmail("emailteste@test.com");
        user.setPhone("1174329468");
        user.setRoles(Set.of(umaRoleDto(roleName)));
        return user;
    }

    public static Role umaRole(String roleName) {
        return Role.builder()
                .id(1L)
                .authority(roleName)
                .build();
    }

    public static RoleDTO umaRoleDto(String roleName) {
        return RoleDTO.builder()
                .id(1L)
                .authority(roleName)
                .build();
    }
}
