package hfoods.demo.services;

import hfoods.demo.entities.enums.ActivityStatus;
import hfoods.demo.repositories.InformationRepository;
import hfoods.demo.services.exceptions.ResourceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static hfoods.demo.tests.Helper.umaInformation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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
}
