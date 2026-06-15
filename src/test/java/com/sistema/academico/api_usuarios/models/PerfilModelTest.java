package com.sistema.academico.api_usuarios.models;

import org.junit.jupiter.api.Test;
import usuario.usuarios.models.Perfil;
import usuario.usuarios.models.Rol;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PerfilTest {

    @Test
    void testConstructorVacioYSetters() {
        Perfil perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNombre("Estudiante");
        perfil.setDescripcion("Acceso limitado a la plataforma");
        Set<Rol> roles = new HashSet<>();
        if (Rol.values().length > 0) {
            roles.add(Rol.values()[0]); 
        }
        perfil.setRoles(roles);
        assertEquals(1L, perfil.getId());
        assertEquals("Estudiante", perfil.getNombre());
        assertEquals("Acceso limitado a la plataforma", perfil.getDescripcion());
        assertEquals(roles, perfil.getRoles());
    }

    @Test
    void testConstructorConArgumentos() {
        Set<Rol> roles = new HashSet<>();
        if (Rol.values().length > 0) {
            roles.add(Rol.values()[0]);
        }
        Perfil perfil = new Perfil(2L, "Docente", "Permisos de profesor", roles);
        assertEquals(2L, perfil.getId());
        assertEquals("Docente", perfil.getNombre());
        assertEquals("Permisos de profesor", perfil.getDescripcion());
        assertEquals(roles, perfil.getRoles());
    }

    @Test
    void testEqualsAndHashCode() {
        Perfil p1 = new Perfil(1L, "Admin", "Todo", null);
        Perfil p2 = new Perfil(1L, "Admin", "Todo", null);
        Perfil p3 = new Perfil(2L, "User", "Mínimo", null);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        Perfil perfil = new Perfil(1L, "Coordinador", "Gestión", null);
        String txt = perfil.toString();
        assertTrue(txt.contains("id=1"));
        assertTrue(txt.contains("nombre=Coordinador"));
        assertTrue(txt.contains("descripcion=Gestión"));
    }
}