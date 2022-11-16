package hfoods.demo.tests;

import hfoods.demo.dto.InformationDTO;
import hfoods.demo.dto.RoleDTO;
import hfoods.demo.entities.Information;
import hfoods.demo.entities.Role;
import hfoods.demo.entities.User;
import hfoods.demo.entities.enums.ActivityStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
