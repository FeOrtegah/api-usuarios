package com.sistema.academico.api_usuarios.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import usuario.usuarios.models.Perfil;
import usuario.usuarios.repository.PerfilRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerfilRepositoryTest {

    @Mock
    private PerfilRepository perfilRepository;

    private Perfil perfil;

    @BeforeEach
    void setUp() {
        perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNombre("ADMINISTRADOR");
        perfil.setDescripcion("Acceso total al sistema académico");
    }

    @Test
    void findByNombre_WhenProfileExists_ShouldReturnProfile() {
        when(perfilRepository.findByNombre("ADMINISTRADOR")).thenReturn(Optional.of(perfil));
        Optional<Perfil> resultado = perfilRepository.findByNombre("ADMINISTRADOR");
        assertTrue(resultado.isPresent());
        assertEquals("ADMINISTRADOR", resultado.get().getNombre());
        assertEquals(1L, resultado.get().getId());
        verify(perfilRepository, times(1)).findByNombre("ADMINISTRADOR");
    }

    @Test
    void findByNombre_WhenProfileDoesNotExist_ShouldReturnEmpty() {
        when(perfilRepository.findByNombre("INEXISTENTE")).thenReturn(Optional.empty());
        Optional<Perfil> resultado = perfilRepository.findByNombre("INEXISTENTE");
        assertFalse(resultado.isPresent());
        verify(perfilRepository, times(1)).findByNombre("INEXISTENTE");
    }

    @Test
    void existsByNombre_WhenProfileExists_ShouldReturnTrue() {
        when(perfilRepository.existsByNombre("ADMINISTRADOR")).thenReturn(true);
        boolean existe = perfilRepository.existsByNombre("ADMINISTRADOR");
        assertTrue(existe);
        verify(perfilRepository, times(1)).existsByNombre("ADMINISTRADOR");
    }

    @Test
    void existsByNombre_WhenProfileDoesNotExist_ShouldReturnFalse() {
        when(perfilRepository.existsByNombre("INVITADO")).thenReturn(false);
        boolean existe = perfilRepository.existsByNombre("INVITADO");
        assertFalse(existe);
        verify(perfilRepository, times(1)).existsByNombre("INVITADO");
    }
}