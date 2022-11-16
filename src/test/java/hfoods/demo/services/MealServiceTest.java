package hfoods.demo.services;

import hfoods.demo.entities.Meal;
import hfoods.demo.entities.enums.ActivityStatus;
import hfoods.demo.repositories.FoodItemRepository;
import hfoods.demo.repositories.FoodRepository;
import hfoods.demo.repositories.MealRepository;
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
import static hfoods.demo.tests.Helper.umUsuario;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@RunWith(MockitoJUnitRunner.class)
public class MealServiceTest {

    @InjectMocks
    private MealService service;

    @Mock
    private MealRepository repository;

    @Mock
    private FoodItemRepository foodItemRepository;

    @Mock
    private FoodRepository foodRepository;

    @Mock
    private AuthService authService;

    @Test
    public void findById_deveRetornarMealDTO_quandoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(umaMeal(1L)));

        assertThat(service.findById(1L))
                .extracting("id", "name")
                .containsExactly(1L, "Uma Refeição Teste");

        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void findById_deveRetornarNotFound_quandoNaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.findById(1L))
                .withMessage("Meal not found");

        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void insert_deveRetornarUnauthorized_quandoNaoAutenticado() {
        when(authService.authenticated()).thenThrow(UnauthorizedException.class);

        assertThatExceptionOfType(UnauthorizedException.class)
                .isThrownBy(() -> service.insert(umaMealDTO(1L)));

        verify(repository, never()).save(any(Meal.class));
    }

    @Test
    public void insert_deveRetornarMeal_quandoInserir() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_ADMIN"));
        when(repository.save(any(Meal.class))).thenReturn(umaMeal(1L));

        assertThatCode(() -> service.insert(umaMealDTO(1L)))
                .doesNotThrowAnyException();

        verify(repository, times(1)).save(any(Meal.class));
    }

    @Test
    public void update_deveRetornarNotFound_quandoNaoEncontrado() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_ADMIN"));
        when(repository.getOne(1L)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.update(1L, umaMealDTO(1L)));

        verify(repository, times(1)).getOne(1L);
        verify(repository, never()).save(any(Meal.class));
    }

    @Test
    public void update_deveRetornarMeal_quandoAtualizar() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_ADMIN"));
        when(repository.getOne(1L)).thenReturn(umaMeal(1L));
        when(repository.save(any(Meal.class))).thenReturn(umaMeal(1L));

        assertThatCode(() -> service.update(1L, umaMealDTO(1L)))
                .doesNotThrowAnyException();

        verify(repository, times(1)).getOne(1L);
        verify(repository, times(1)).save(any(Meal.class));
    }

    @Test
    public void update_deveRetornarForbidden_quandoUsuarioNaoNutricionistaOuAdmin() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_TEST"));
        doThrow(ForbiddenException.class).when(authService).validateAdminOrNutritionist(1L);

        assertThatExceptionOfType(ForbiddenException.class)
                .isThrownBy(() -> service.update(1L, umaMealDTO(1L)));

        verify(repository, never()).getOne(1L);
        verify(repository, never()).save(any(Meal.class));
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
        when(repository.findById(1L)).thenReturn(Optional.of(umaMeal(1L)));

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
