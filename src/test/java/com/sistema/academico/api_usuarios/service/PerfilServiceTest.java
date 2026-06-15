package com.sistema.academico.api_usuarios.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import usuario.usuarios.models.Perfil;
import usuario.usuarios.repository.PerfilRepository;
import usuario.usuarios.service.PerfilService;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerfilServiceTest {

    @Mock
    private PerfilRepository perfilRepository;

    @InjectMocks
    private PerfilService perfilService;

    private Perfil perfil;

    @BeforeEach
    void setUp() {
        perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNombre("DOCENTE");
        perfil.setDescripcion("Acceso para profesores");
        perfil.setRoles(new HashSet<>()); 
    }

    @Test
    void guardarPerfil_WhenNameDoesNotExist_ShouldSaveSuccessfully() {
        when(perfilRepository.existsByNombre("DOCENTE")).thenReturn(false);
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfil);
        Perfil guardado = perfilService.guardarPerfil(perfil);
        assertNotNull(guardado);
        assertEquals("DOCENTE", guardado.getNombre());
        verify(perfilRepository, times(1)).save(perfil);
    }

    @Test
    void guardarPerfil_WhenNameExists_ShouldThrowException() {
        when(perfilRepository.existsByNombre("DOCENTE")).thenReturn(true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perfilService.guardarPerfil(perfil);
        });
        assertEquals("El nombre del perfil ya existe", exception.getMessage());
        verify(perfilRepository, never()).save(any(Perfil.class));
    }

    @Test
    void listarPerfiles_ShouldReturnList() {
        when(perfilRepository.findAll()).thenReturn(Collections.singletonList(perfil));
        List<Perfil> resultado = perfilService.listarPerfiles();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPerfilPorId_ShouldReturnOptional() {
        when(perfilRepository.findById(1L)).thenReturn(Optional.of(perfil));
        Optional<Perfil> resultado = perfilService.obtenerPerfilPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("DOCENTE", resultado.get().getNombre());
    }

    @Test
    void actualizarPerfil_WhenExistsAndNameValid_ShouldUpdateSuccessfully() {
        Perfil perfilActualizado = new Perfil();
        perfilActualizado.setNombre("PROFESOR");
        perfilActualizado.setRoles(new HashSet<>()); 
        when(perfilRepository.findById(1L)).thenReturn(Optional.of(perfil));
        when(perfilRepository.existsByNombre("PROFESOR")).thenReturn(false);
        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfil);
        Perfil resultado = perfilService.actualizarPerfil(1L, perfilActualizado);
        assertNotNull(resultado);
        verify(perfilRepository, times(1)).save(perfil);
    }

    @Test
    void eliminarPerfil_WhenExists_ShouldDeleteSuccessfully() {
        when(perfilRepository.existsById(1L)).thenReturn(true);
        doNothing().when(perfilRepository).deleteById(1L);
        assertDoesNotThrow(() -> perfilService.eliminarPerfil(1L));
        verify(perfilRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarPerfil_WhenDoesNotExist_ShouldThrowException() {
        when(perfilRepository.existsById(1L)).thenReturn(false);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            perfilService.eliminarPerfil(1L);
        });
        assertEquals("Perfil no encontrado", exception.getMessage());
        verify(perfilRepository, never()).deleteById(anyLong());
    }
}