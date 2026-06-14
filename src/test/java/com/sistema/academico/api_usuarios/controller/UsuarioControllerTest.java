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
import usuario.usuarios.controller.UsuarioController;
import usuario.usuarios.dto.UsuarioDTO;
import usuario.usuarios.models.Usuario;
import usuario.usuarios.service.UsuarioService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    private MockMvc mockMvc;

    @Mock 
    private UsuarioService usuarioService;

    @InjectMocks 
    private UsuarioController usuarioController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Usuario usuario;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("juan@example.com");
        usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre("Juan");
        usuarioDTO.setApellido("Perez");
        usuarioDTO.setEmail("juan@example.com");
        usuarioDTO.setUsername("juanito");
        usuarioDTO.setPassword("123456");
    }

    @Test
    void crearUsuario_ShouldReturnOk() throws Exception {
        when(usuarioService.guardarUsuario(any(Usuario.class))).thenReturn(usuario);
        mockMvc.perform(post("/api/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void listar_ShouldReturnList() throws Exception {
        when(usuarioService.listarUsuarios()).thenReturn(Collections.singletonList(usuario));
        mockMvc.perform(get("/api/v1/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void obtener_WhenExists_ShouldReturnUsuario() throws Exception {
        when(usuarioService.obtenerPorId(1L)).thenReturn(Optional.of(usuario));
        mockMvc.perform(get("/api/v1/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void buscarPorUsername_WhenExists_ShouldReturnUsuario() throws Exception {
        when(usuarioService.obtenerPorUsername("juanito")).thenReturn(Optional.of(usuario));
        mockMvc.perform(get("/api/v1/usuarios/buscar").param("username", "juanito"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @Test
    void actualizarUsuario_WhenExists_ShouldReturnUpdated() throws Exception {
        when(usuarioService.actualizarUsuario(eq(1L), any(Usuario.class))).thenReturn(usuario);
        mockMvc.perform(put("/api/v1/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarUsuario_ShouldReturnNoContent() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(1L);
        mockMvc.perform(delete("/api/v1/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}