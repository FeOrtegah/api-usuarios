package com.sistema.academico.api_usuarios.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import usuario.usuarios.models.Credenciales;
import usuario.usuarios.models.Direccion;
import usuario.usuarios.models.Usuario;
import usuario.usuarios.repository.UsuarioRepository;
import usuario.usuarios.service.UsuarioService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Credenciales credenciales;

    @BeforeEach
    void setUp() {
        credenciales = new Credenciales("juanito_dev", "pass123");
        Direccion direccion = new Direccion("Alameda", "456", "Santiago", "Metropolitana", "Chile", "1234");
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan@example.com");
        usuario.setCredenciales(credenciales);
        usuario.setDireccion(direccion);
    }

    @Test
    void guardarUsuario_WhenUsernameDoesNotExist_ShouldSaveSuccessfully() {
        when(usuarioRepository.existsByCredencialesUsername("juanito_dev")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        Usuario guardado = usuarioService.guardarUsuario(usuario);
        assertNotNull(guardado);
        assertEquals("Juan", guardado.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void guardarUsuario_WhenUsernameExists_ShouldThrowException() {
        when(usuarioRepository.existsByCredencialesUsername("juanito_dev")).thenReturn(true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.guardarUsuario(usuario);
        });
        assertEquals("El nombre de usuario ya existe", exception.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void listarUsuarios_ShouldReturnList() {
        when(usuarioRepository.findAll()).thenReturn(Collections.singletonList(usuario));
        List<Usuario> resultado = usuarioService.listarUsuarios();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void obtenerPorId_ShouldReturnOptional() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        Optional<Usuario> resultado = usuarioService.obtenerPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("juanito_dev", resultado.get().getCredenciales().getUsername());
    }

    @Test
    void obtenerPorUsername_ShouldReturnOptional() {
        when(usuarioRepository.findByCredencialesUsername("juanito_dev")).thenReturn(Optional.of(usuario));
        Optional<Usuario> resultado = usuarioService.obtenerPorUsername("juanito_dev");
        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    void actualizarUsuario_WhenExistsAndUsernameValid_ShouldUpdateSuccessfully() {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Juan Carlos");
        usuarioActualizado.setCredenciales(credenciales);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        Usuario resultado = usuarioService.actualizarUsuario(1L, usuarioActualizado);
        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void eliminarUsuario_WhenExists_ShouldDeleteSuccessfully() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(1L);
        assertDoesNotThrow(() -> usuarioService.eliminarUsuario(1L));
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarUsuario_WhenDoesNotExist_ShouldThrowException() {
        when(usuarioRepository.existsById(1L)).thenReturn(false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.eliminarUsuario(1L);
        });
        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioRepository, never()).deleteById(anyLong());
    }
}