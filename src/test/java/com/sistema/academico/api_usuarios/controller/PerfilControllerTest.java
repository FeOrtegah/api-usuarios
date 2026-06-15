package com.sistema.academico.api_usuarios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import usuario.usuarios.dto.PerfilDTO;
import usuario.usuarios.models.Perfil;
import usuario.usuarios.service.PerfilService;
import usuario.usuarios.controller.PerfilController;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PerfilControllerTest {

    private MockMvc mockMvc;
    @Mock private PerfilService perfilService;
    @InjectMocks private PerfilController perfilController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Perfil perfil;
    private PerfilDTO perfilDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(perfilController).build();
        perfil = new Perfil();
        perfil.setNombre("Admin");
        perfil.setDescripcion("Acceso total");
        perfilDTO = new PerfilDTO();
        perfilDTO.setNombre("Admin");
        perfilDTO.setDescripcion("Acceso total");
    }

    @Test
    void crearPerfil_ShouldReturnOk() throws Exception {
        when(perfilService.guardarPerfil(any(Perfil.class))).thenReturn(perfil);
        mockMvc.perform(post("/api/v1/perfiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(perfilDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Admin"));
    }

    @Test
    void listar_ShouldReturnList() throws Exception {
        when(perfilService.listarPerfiles()).thenReturn(Collections.singletonList(perfil));
        mockMvc.perform(get("/api/v1/perfiles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Admin"));
    }

    @Test
    void obtenerPerfilPorId_WhenExists_ShouldReturnPerfil() throws Exception {
        when(perfilService.obtenerPerfilPorId(1L)).thenReturn(Optional.of(perfil));
        mockMvc.perform(get("/api/v1/perfiles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Admin"));
    }

    @Test
    void obtenerPerfilPorNombre_WhenExists_ShouldReturnPerfil() throws Exception {
        when(perfilService.obtenerPerfilPorNombre("Admin")).thenReturn(Optional.of(perfil));
        mockMvc.perform(get("/api/v1/perfiles/buscar").param("nombre", "Admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Admin"));
    }

    @Test
    void actualizarPerfil_WhenExists_ShouldReturnUpdated() throws Exception {
        when(perfilService.actualizarPerfil(eq(1L), any(Perfil.class))).thenReturn(perfil);
        mockMvc.perform(put("/api/v1/perfiles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(perfilDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarPerfil_ShouldReturnNoContent() throws Exception {
        doNothing().when(perfilService).eliminarPerfil(1L);
        mockMvc.perform(delete("/api/v1/perfiles/1"))
                .andExpect(status().isNoContent());
    }
}