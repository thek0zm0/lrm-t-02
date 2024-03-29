package hfoods.demo.dto;

import hfoods.demo.entities.User;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 9178661439383356177L;

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String birthDate;

    Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        cpf = user.getCpf();;
        phone = user.getPhone();
        birthDate = user.getBirthDate();
        user.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
    }
}
