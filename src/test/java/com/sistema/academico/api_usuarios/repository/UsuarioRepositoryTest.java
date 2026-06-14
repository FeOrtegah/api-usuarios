package com.sistema.academico.api_usuarios.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import usuario.usuarios.models.Credenciales;
import usuario.usuarios.models.Usuario;
import usuario.usuarios.repository.UsuarioRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioRepositoryTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Carlos");
        usuario.setCredenciales(new Credenciales("carlitos_dev", "passwordEncrypt"));
    }

    @Test
    void findByCredencialesUsername_WhenUserExists_ShouldReturnUser() {
        when(usuarioRepository.findByCredencialesUsername("carlitos_dev")).thenReturn(Optional.of(usuario));
        Optional<Usuario> resultado = usuarioRepository.findByCredencialesUsername("carlitos_dev");
        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getNombre());
        assertEquals("carlitos_dev", resultado.get().getCredenciales().getUsername());
        verify(usuarioRepository, times(1)).findByCredencialesUsername("carlitos_dev");
    }

    @Test
    void findByCredencialesUsername_WhenUserDoesNotExist_ShouldReturnEmpty() {
        when(usuarioRepository.findByCredencialesUsername("inexistente")).thenReturn(Optional.empty());
        Optional<Usuario> resultado = usuarioRepository.findByCredencialesUsername("inexistente");
        assertFalse(resultado.isPresent());
        verify(usuarioRepository, times(1)).findByCredencialesUsername("inexistente");
    }

    @Test
    void existsByCredencialesUsername_WhenUserExists_ShouldReturnTrue() {
        when(usuarioRepository.existsByCredencialesUsername("carlitos_dev")).thenReturn(true);
        boolean existe = usuarioRepository.existsByCredencialesUsername("carlitos_dev");
        assertTrue(existe);
        verify(usuarioRepository, times(1)).existsByCredencialesUsername("carlitos_dev");
    }

    @Test
    void existsByCredencialesUsername_WhenUserDoesNotExist_ShouldReturnFalse() {
        when(usuarioRepository.existsByCredencialesUsername("usuario_falso")).thenReturn(false);
        boolean existe = usuarioRepository.existsByCredencialesUsername("usuario_falso");
        assertFalse(existe);
        verify(usuarioRepository, times(1)).existsByCredencialesUsername("usuario_falso");
    }
}