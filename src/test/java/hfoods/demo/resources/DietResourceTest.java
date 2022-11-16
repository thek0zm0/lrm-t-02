package hfoods.demo.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import hfoods.demo.services.DietService;
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

import static hfoods.demo.tests.Helper.umaDietDTO;
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
public class DietResourceTest {

    @MockBean
    private DietService service;

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

        mockMvc.perform(get("/diet/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void findById_deveRetornarDiet_quandoEncontrado() {
        when(service.findById(1L)).thenReturn(umaDietDTO(1L));

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        mockMvc.perform(get("/diet/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("description").exists());
    }

    @Test
    @SneakyThrows
    public void insert_deveRetornarUnauthorized_quandoNaoAutenticado() {
        when(service.insert(umaDietDTO(1L))).thenReturn(umaDietDTO(1L));

        String jsonBody = objectMapper.writeValueAsString(umaDietDTO(1L));

        mockMvc.perform(post("/diet/")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    public void insert_deveRetornarDiet_quandoInserir() {
        when(service.insert(umaDietDTO(1L))).thenReturn(umaDietDTO(1L));

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        String jsonBody = objectMapper.writeValueAsString(umaDietDTO(1L));

        mockMvc.perform(post("/diet/")
                        .header("Authorization", "Bearer " + accessToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("description").exists());
    }

    @Test
    @SneakyThrows
    public void update_deveRetornarDiet_quandoAtualizar() {
        when(service.update(1L, umaDietDTO(1L))).thenReturn(umaDietDTO(1L));

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        String jsonBody = objectMapper.writeValueAsString(umaDietDTO(1L));

        mockMvc.perform(put("/diet/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("description").exists());
    }

    @Test
    @SneakyThrows
    public void update_deveRetornarResourceNotFound_quandoNaoEncontrado() {
        when(service.update(1L, umaDietDTO(1L))).thenThrow(ResourceNotFoundException.class);

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        String jsonBody = objectMapper.writeValueAsString(umaDietDTO(1L));

        mockMvc.perform(put("/diet/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void update_deveRetornarUnauthorized_quandoNaoAutenticado() {
        when(service.update(1L, umaDietDTO(1L))).thenReturn(umaDietDTO(1L));

        String jsonBody = objectMapper.writeValueAsString(umaDietDTO(1L));

        mockMvc.perform(put("/diet/{id}", 1L)
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

        mockMvc.perform(delete("/diet/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    public void delete_deveRetornarResourceNotFound_quandoNaoEncontrado() {
        doThrow(ResourceNotFoundException.class).when(service).delete(1L);

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");


        mockMvc.perform(delete("/diet/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void delete_deveRetornarUnauthorized_quandoNaoAutenticado() {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/diet/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
