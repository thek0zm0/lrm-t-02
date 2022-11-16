package hfoods.demo.services;

import hfoods.demo.entities.Information;
import hfoods.demo.entities.enums.ActivityStatus;
import hfoods.demo.repositories.InformationRepository;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InformationServiceTest {

    @InjectMocks
    private InformationService service;

    @Mock
    private InformationRepository repository;

    @Mock
    private AuthService authService;

    @Test
    public void findById_deveRetornarInformationDTO_quandoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.of(umaInformation(1L)));

        assertThat(service.findById(1L))
                .extracting("id", "sex", "age", "activityStatus")
                .containsExactly(1L, 'M', 20, ActivityStatus.MEDIUM);

        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void findById_deveRetornarNotFound_quandoNaoEncontrado() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.findById(1L))
                .withMessage("Food not found");

        verify(repository, times(1)).findById(1L);
    }

    @Test
    public void insert_deveRetornarUnauthorized_quandoNaoAutenticado() {
        when(authService.authenticated()).thenThrow(UnauthorizedException.class);

        assertThatExceptionOfType(UnauthorizedException.class)
                .isThrownBy(() -> service.insert(umaInformationDTO(1L)));

        verify(repository, never()).save(any(Information.class));
    }

    @Test
    public void insert_deveRetornarInformation_quandoInserir() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_ADMIN"));
        when(repository.save(any(Information.class))).thenReturn(umaInformation(1L));

        assertThatCode(() -> service.insert(umaInformationDTO(1L)))
                .doesNotThrowAnyException();

        verify(repository, times(1)).save(any(Information.class));
    }

    @Test
    public void update_deveRetornarNotFound_quandoNaoEncontrado() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_ADMIN"));
        when(repository.getOne(1L)).thenThrow(EntityNotFoundException.class);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.update(1L, umaInformationDTO(1L)));

        verify(repository, times(1)).getOne(1L);
        verify(repository, never()).save(any(Information.class));
    }

    @Test
    public void update_deveRetornarInformation_quandoAtualizar() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_ADMIN"));
        when(repository.getOne(1L)).thenReturn(umaInformation(1L));
        when(repository.save(any(Information.class))).thenReturn(umaInformation(1L));

        assertThatCode(() -> service.update(1L, umaInformationDTO(1L)))
                .doesNotThrowAnyException();

        verify(repository, times(1)).getOne(1L);
        verify(repository, times(1)).save(any(Information.class));
    }

    @Test
    public void update_deveRetornarForbidden_quandoUsuarioNaoNutricionistaOuAdmin() {
        when(authService.authenticated()).thenReturn(umUsuario("ROLE_TEST"));
        doThrow(ForbiddenException.class).when(authService).validateAdminOrNutritionist(1L);

        assertThatExceptionOfType(ForbiddenException.class)
                .isThrownBy(() -> service.update(1L, umaInformationDTO(1L)));

        verify(repository, never()).getOne(1L);
        verify(repository, never()).save(any(Information.class));
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
        when(repository.findById(1L)).thenReturn(Optional.of(umaInformation(1L)));

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
