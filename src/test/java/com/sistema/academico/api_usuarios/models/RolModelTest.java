package com.sistema.academico.api_usuarios.models;

import org.junit.jupiter.api.Test;
import usuario.usuarios.models.Rol;

import static org.junit.jupiter.api.Assertions.*;

class RolTest {

    @Test
    void testEnumValues() {
        Rol[] roles = Rol.values();
        assertEquals(6, roles.length);
        assertEquals(Rol.PROFESOR, Rol.valueOf("PROFESOR"));
        assertEquals(Rol.ESTUDIANTE, Rol.valueOf("ESTUDIANTE"));
        assertEquals(Rol.APODERADO, Rol.valueOf("APODERADO"));
        assertEquals(Rol.ADMINISTRADOR, Rol.valueOf("ADMINISTRADOR"));
        assertEquals(Rol.COORDINADOR, Rol.valueOf("COORDINADOR"));
        assertEquals(Rol.DIRECTOR, Rol.valueOf("DIRECTOR"));
    }

    @Test
    void testEnumOrdinal() {
        assertEquals(0, Rol.PROFESOR.ordinal());
        assertEquals(1, Rol.ESTUDIANTE.ordinal());
        assertEquals(3, Rol.ADMINISTRADOR.ordinal());
    }
}