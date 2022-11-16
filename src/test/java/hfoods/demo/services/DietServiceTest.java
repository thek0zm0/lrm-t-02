package hfoods.demo.services;

import hfoods.demo.entities.Diet;
import hfoods.demo.repositories.DietRepository;
import hfoods.demo.repositories.UserRepository;
import hfoods.demo.services.exceptions.ForbiddenException;
import hfoods.demo.services.exceptions.ResourceNotFoundException;
import hfoods.demo.services.exceptions.UnauthorizedException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static hfoods.demo.tests.Helper.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DietServiceTest {

    @InjectMocks
    private DietService service;

    @Mock
    private DietRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MealService mealService;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @Test
    public void findById_deveRetornarDietDTO_quandoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(umaDiet(1L)));

        assertThat(service.findById(1L))
                .extracting("id", "name")
                .containsExactly(1L, "Dieta teste");

        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void findById_deveRetornarNotFound_quandoNaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.findById(1L))
                .withMessage("Diet not found");

        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void insert_deveRetornarUnauthorized_quandoNaoAutenticado() {
        when(authService.authenticated()).thenThrow(UnauthorizedException.class);

        assertThatExceptionOfType(UnauthorizedException.class)
                .isThrownBy(() -> service.insert(umaDietDTO(1L)));

        verify(repository, never()).save(any(Diet.class));
    }

    @Test
    public void insert_deveRetornarDiet_quandoInserir() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_ADMIN"));
        when(repository.save(any(Diet.class))).thenReturn(umaDiet(1L));

        assertThatCode(() -> service.insert(umaDietDTO(1L)))
                .doesNotThrowAnyException();

        verify(repository, times(1)).save(any(Diet.class));
    }

    @Test
    public void update_deveRetornarNotFound_quandoNaoEncontrado() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_ADMIN"));
        when(repository.getOne(1L)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.update(1L, umaDietDTO(1L)));

        verify(repository, times(1)).getOne(1L);
        verify(repository, never()).save(any(Diet.class));
    }

    @Test
    public void update_deveRetornarDiet_quandoAtualizar() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_ADMIN"));
        when(repository.getOne(1L)).thenReturn(umaDiet(1L));
        when(repository.save(any(Diet.class))).thenReturn(umaDiet(1L));

        assertThatCode(() -> service.update(1L, umaDietDTO(1L)))
                .doesNotThrowAnyException();

        verify(repository, times(1)).getOne(1L);
        verify(repository, times(1)).save(any(Diet.class));
    }

    @Test
    public void update_deveRetornarForbidden_quandoUsuarioNaoNutricionistaOuAdmin() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_TEST"));
        doThrow(ForbiddenException.class).when(authService).validateAdminOrNutritionist(1L);

        assertThatExceptionOfType(ForbiddenException.class)
                .isThrownBy(() -> service.update(1L, umaDietDTO(1L)));

        verify(repository, never()).getOne(1L);
        verify(repository, never()).save(any(Diet.class));
    }

    @Test
    public void delete_deveRetornarNotFound_quandoNaoEncontrado() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_ADMIN"));
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.delete(1L));

        verify(repository, times(1)).findById(1L);
        verify(repository, never()).deleteById(1L);
    }

    @Test
    public void delete_deveRetornarVoid_quandoDeletar() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_ADMIN"));
        when(repository.findById(1L)).thenReturn(Optional.of(umaDiet(1L)));

        assertThatCode(() -> service.delete(1L))
                .doesNotThrowAnyException();

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void delete_deveRetornarForbidden_quandoUsuarioNaoNutricionistaOuAdmin() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_TEST"));
        doThrow(ForbiddenException.class).when(authService).validateAdminOrNutritionist(1L);

        assertThatExceptionOfType(ForbiddenException.class)
                .isThrownBy(() -> service.delete(1L));

        verify(repository, never()).findById(1L);
        verify(repository, never()).deleteById(1L);
    }
}
