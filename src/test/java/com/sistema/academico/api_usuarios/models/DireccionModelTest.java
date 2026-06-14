package com.sistema.academico.api_usuarios.models;

import org.junit.jupiter.api.Test;
import usuario.usuarios.models.Direccion;

import static org.junit.jupiter.api.Assertions.*;

class DireccionTest {

    @Test
    void testConstructorVacioYSetters() {
        Direccion dir = new Direccion();
        dir.setCalle("Av. Concha y Toro");
        dir.setNumero("1234");
        dir.setCiudad("Puente Alto");
        dir.setRegion("Metropolitana");
        dir.setPais("Chile");
        dir.setCodigoPostal("8150000");
        assertEquals("Av. Concha y Toro", dir.getCalle());
        assertEquals("1234", dir.getNumero());
        assertEquals("Puente Alto", dir.getCiudad());
        assertEquals("Metropolitana", dir.getRegion());
        assertEquals("Chile", dir.getPais());
        assertEquals("8150000", dir.getCodigoPostal());
    }

    @Test
    void testConstructorConArgumentos() {
        Direccion dir = new Direccion("Alameda", "340", "Santiago", "Metropolitana", "Chile", "8320000");
        assertEquals("Alameda", dir.getCalle());
        assertEquals("340", dir.getNumero());
        assertEquals("Santiago", dir.getCiudad());
        assertEquals("Metropolitana", dir.getRegion());
        assertEquals("Chile", dir.getPais());
        assertEquals("8320000", dir.getCodigoPostal());
    }

    @Test
    void testEqualsAndHashCode() {
        Direccion dir1 = new Direccion("Calle A", "10", "Valpo", "Valparaiso", "Chile", "2340000");
        Direccion dir2 = new Direccion("Calle A", "10", "Valpo", "Valparaiso", "Chile", "2340000");
        Direccion dir3 = new Direccion("Calle B", "20", "Valpo", "Valparaiso", "Chile", "2340000");
        assertEquals(dir1, dir2);
        assertNotEquals(dir1, dir3);
        assertEquals(dir1.hashCode(), dir2.hashCode());
    }

    @Test
    void testToString() {
        Direccion dir = new Direccion("Calle Falsa", "123", "Springfield", "Región", "País", "00000");
        String txt = dir.toString();
        assertTrue(txt.contains("calle=Calle Falsa"));
        assertTrue(txt.contains("numero=123"));
        assertTrue(txt.contains("ciudad=Springfield"));
    }
}