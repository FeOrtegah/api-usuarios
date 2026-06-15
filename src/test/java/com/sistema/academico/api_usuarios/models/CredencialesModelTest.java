package com.sistema.academico.api_usuarios.models;

import org.junit.jupiter.api.Test;
import usuario.usuarios.models.Credenciales;

import static org.junit.jupiter.api.Assertions.*;

class CredencialesTest {

    @Test
    void testConstructorVacioYSetters() {
        Credenciales credenciales = new Credenciales();        
        credenciales.setUsername("admin_test");
        credenciales.setPassword("securePassword123");
        assertEquals("admin_test", credenciales.getUsername());
        assertEquals("securePassword123", credenciales.getPassword());
    }

    @Test
    void testConstructorConArgumentos() {
        Credenciales credenciales = new Credenciales("user_test", "password123");
        assertEquals("user_test", credenciales.getUsername());
        assertEquals("password123", credenciales.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        Credenciales cred1 = new Credenciales("usuario1", "pass1");
        Credenciales cred2 = new Credenciales("usuario1", "pass1");
        Credenciales cred3 = new Credenciales("usuario2", "pass2");
        assertEquals(cred1, cred2);
        assertNotEquals(cred1, cred3);
        assertEquals(cred1.hashCode(), cred2.hashCode());
    }

    @Test
    void testToString() {
        Credenciales credenciales = new Credenciales("user", "pass");
        String toStringResult = credenciales.toString();
        assertTrue(toStringResult.contains("username=user"));
        assertTrue(toStringResult.contains("password=pass"));
    }
}