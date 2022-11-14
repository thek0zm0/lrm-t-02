package hfoods.demo.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import hfoods.demo.services.InformationService;
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

import static hfoods.demo.tests.Helper.umaInformationDTO;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class InformationResourceTest {

    @MockBean
    private InformationService service;

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

        mockMvc.perform(get("/information/{id}", 1L)
                .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void findById_deveRetornarInformation_quandoEncontrado() {
        when(service.findById(1L)).thenReturn(umaInformationDTO(1L));

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        mockMvc.perform(get("/information/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("sex").exists())
                .andExpect(jsonPath("age").exists())
                .andExpect(jsonPath("activityStatus").exists());
    }

    @Test
    @SneakyThrows
    public void insert_deveRetornarUnauthorized_quandoNaoAutenticado() {
        when(service.insert(umaInformationDTO(1L))).thenReturn(umaInformationDTO(1L));

        String jsonBody = objectMapper.writeValueAsString(umaInformationDTO(1L));

        mockMvc.perform(post("/information/")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SneakyThrows
    public void insert_deveRetornarInformation_quandoInserir() {
        when(service.insert(umaInformationDTO(1L))).thenReturn(umaInformationDTO(1L));

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        String jsonBody = objectMapper.writeValueAsString(umaInformationDTO(1L));

        mockMvc.perform(post("/information/")
                        .header("Authorization", "Bearer " + accessToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("sex").exists())
                .andExpect(jsonPath("age").exists())
                .andExpect(jsonPath("activityStatus").exists());
    }

    @Test
    @SneakyThrows
    public void update_deveRetornarInformation_quandoAtualizar() {
        when(service.update(1L, umaInformationDTO(1L))).thenReturn(umaInformationDTO(1L));

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        String jsonBody = objectMapper.writeValueAsString(umaInformationDTO(1L));

        mockMvc.perform(put("/information/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("sex").exists())
                .andExpect(jsonPath("age").exists())
                .andExpect(jsonPath("activityStatus").exists());
    }

    @Test
    @SneakyThrows
    public void update_deveRetornarResourceNotFound_quandoNaoEncontrado() {
        when(service.update(1L, umaInformationDTO(1L))).thenThrow(ResourceNotFoundException.class);

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");

        String jsonBody = objectMapper.writeValueAsString(umaInformationDTO(1L));

        mockMvc.perform(put("/information/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void update_deveRetornarUnauthorized_quandoNaoAutenticado() {
        when(service.update(1L, umaInformationDTO(1L))).thenReturn(umaInformationDTO(1L));

        String jsonBody = objectMapper.writeValueAsString(umaInformationDTO(1L));

        mockMvc.perform(put("/information/{id}", 1L)
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

        mockMvc.perform(delete("/information/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    public void delete_deveRetornarResourceNotFound_quandoNaoEncontrado() {
        doThrow(ResourceNotFoundException.class).when(service).delete(1L);

        var accessToken = tokenUtil.obtainAccessToken(mockMvc, "john@gmail.com", "123456");


        mockMvc.perform(delete("/information/{id}", 1L)
                        .header("Authorization", "Bearer " + accessToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void delete_deveRetornarUnauthorized_quandoNaoAutenticado() {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/information/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
