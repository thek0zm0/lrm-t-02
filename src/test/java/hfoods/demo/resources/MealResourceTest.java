package hfoods.demo.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import hfoods.demo.services.MealService;
import hfoods.demo.services.exceptions.ResourceNotFoundException;
import hfoods.demo.tests.TokenUtil;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static hfoods.demo.tests.Helper.umaMealDTO;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class MealResourceTest {

    @MockBean
    private MealService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Test
    @SneakyThrows
    public void findById_deveRetornarResourceNotFound_quandoNaoEncontrado() {
        when(service.findById(1L)).thenThrow(ResourceNotFoundException.class);

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        mockMvc.perform(get("/meal/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void findById_deveRetornarMeal_quandoEncontrado() {
        when(service.findById(1L)).thenReturn(umaMealDTO(1L));

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        mockMvc.perform(get("/meal/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("timeHour").exists());
    }

    @Test
    @SneakyThrows
    public void insert_deveRetornarUnauthorized_quandoNaoAutenticado() {
        when(service.insert(umaMealDTO(1L))).thenReturn(umaMealDTO(1L));

        String jsonBody = objectMapper.writeValueAsString(umaMealDTO(1L));

        mockMvc.perform(post("/meal/")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    public void insert_deveRetornarMeal_quandoInserir() {
        when(service.insert(umaMealDTO(1L))).thenReturn(umaMealDTO(1L));

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        String jsonBody = objectMapper.writeValueAsString(umaMealDTO(1L));

        mockMvc.perform(post("/meal/")
                        .header("Authorization", "Bearer " + accessToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("timeHour").exists());
    }

    @Test
    @SneakyThrows
    public void update_deveRetornarMeal_quandoAtualizar() {
        when(service.update(1L, umaMealDTO(1L))).thenReturn(umaMealDTO(1L));

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        String jsonBody = objectMapper.writeValueAsString(umaMealDTO(1L));

        mockMvc.perform(put("/meal/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("timeHour").exists());
    }

    @Test
    @SneakyThrows
    public void update_deveRetornarResourceNotFound_quandoNaoEncontrado() {
        when(service.update(1L, umaMealDTO(1L))).thenThrow(ResourceNotFoundException.class);

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        String jsonBody = objectMapper.writeValueAsString(umaMealDTO(1L));

        mockMvc.perform(put("/meal/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void update_deveRetornarUnauthorized_quandoNaoAutenticado() {
        when(service.update(1L, umaMealDTO(1L))).thenReturn(umaMealDTO(1L));

        String jsonBody = objectMapper.writeValueAsString(umaMealDTO(1L));

        mockMvc.perform(put("/meal/{id}", 1L)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    public void update_deveRetornarNoContent_quandoDeletar() {
        doNothing().when(service).delete(1L);

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        mockMvc.perform(delete("/meal/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    public void delete_deveRetornarResourceNotFound_quandoNaoEncontrado() {
        doThrow(ResourceNotFoundException.class).when(service).delete(1L);

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");


        mockMvc.perform(delete("/meal/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void delete_deveRetornarUnauthorized_quandoNaoAutenticado() {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/meal/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
