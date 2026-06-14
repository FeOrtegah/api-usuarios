package com.sistema.academico.api_usuarios.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usuario.usuarios.models.*;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Credenciales credenciales;
    private Direccion direccion;
    private Perfil perfil;
    private LocalDateTime ahora;

    @BeforeEach
    void setUp() {
        ahora = LocalDateTime.now();
        credenciales = new Credenciales("juanito", "password123");
        direccion = new Direccion("Alameda", "123", "Santiago", "Metropolitana", "Chile", "123456");
        perfil = new Perfil(1L, "Estudiante", "Perfil de alumno", new HashSet<>());
    }

    @Test
    void testConstructorVacioYSetters() {
        Usuario usuario = new Usuario();
        usuario.setId(10L);
        usuario.setCredenciales(credenciales);
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juan.perez@example.com");
        usuario.setRol(Rol.ESTUDIANTE);
        usuario.setPerfil(perfil);
        usuario.setDireccion(direccion);
        usuario.setTelefono("+56912345678");
        usuario.setCursoId(5L);
        usuario.setFechaRegistro(ahora);
        usuario.setUltimaActualizacion(ahora);
        assertEquals(10L, usuario.getId());
        assertEquals(credenciales, usuario.getCredenciales());
        assertEquals("Juan", usuario.getNombre());
        assertEquals("Pérez", usuario.getApellido());
        assertEquals("juan.perez@example.com", usuario.getEmail());
        assertEquals(Rol.ESTUDIANTE, usuario.getRol());
        assertEquals(perfil, usuario.getPerfil());
        assertEquals(direccion, usuario.getDireccion());
        assertEquals("+56912345678", usuario.getTelefono());
        assertEquals(5L, usuario.getCursoId());
        assertEquals(ahora, usuario.getFechaRegistro());
        assertEquals(ahora, usuario.getUltimaActualizacion());
    }

    @Test
    void testConstructorConArgumentos() {
        Usuario usuario = new Usuario(
            1L, credenciales, "Ana", "García", "ana@example.com", 
            Rol.PROFESOR, perfil, direccion, "+56987654321", 2L, ahora, ahora
        );
        assertEquals(1L, usuario.getId());
        assertEquals("Ana", usuario.getNombre());
        assertEquals(Rol.PROFESOR, usuario.getRol());
        assertEquals(perfil, usuario.getPerfil());
        assertEquals(direccion, usuario.getDireccion());
        assertEquals(2L, usuario.getCursoId());
    }
    @Test
    void testEqualsAndHashCode() {
        Usuario u1 = new Usuario(1L, null, "Diego", "Silva", null, null, null, null, null, null, null, null);
        Usuario u2 = new Usuario(1L, null, "Diego", "Silva", null, null, null, null, null, null, null, null);
        Usuario u3 = new Usuario(2L, null, "Carlos", "Mora", null, null, null, null, null, null, null, null);
        assertEquals(u1, u2);
        assertNotEquals(u1, u3);
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    void testToString() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Marta");
        usuario.setEmail("marta@example.com");
        String txt = usuario.toString();
        assertTrue(txt.contains("id=1"));
        assertTrue(txt.contains("nombre=Marta"));
        assertTrue(txt.contains("email=marta@example.com"));
    }
}