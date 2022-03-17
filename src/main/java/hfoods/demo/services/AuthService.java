package hfoods.demo.services;

import hfoods.demo.entities.User;
import hfoods.demo.repositories.UserRepository;
import hfoods.demo.services.exceptions.ForbiddenException;
import hfoods.demo.services.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // Resgatar usuário logado
    @Transactional(readOnly = true)
    public User authenticated() {
        try {
            var username = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(username);
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid User");
        }
    }

    public void validateSelfOrAdmin(Long userId) {
        var user = authenticated();

        if (!Objects.equals(user.getId(), userId)
                && !user.hasRole("ROLE_ADMIN")
                && !user.hasRole("ROLE_NUTRITIONIST")) {
            throw new ForbiddenException("Access denied");
        }
    }
}
