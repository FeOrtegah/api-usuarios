package com.sistema.academico.api_usuarios;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import usuario.usuarios.ApiUsuariosApplication;

@SpringBootTest(classes = ApiUsuariosApplication.class)
class ApiUsuariosApplicationTest {

    @Test
    void contextLoads() {
    }
}

// docker run --rm -v "%CD%:/app" -w /app maven:3.9.6-eclipse-temurin-17 mvn clean test codigo que hace compilar los test pq el lombox y mi local java version son distinto y no puedo poner normal