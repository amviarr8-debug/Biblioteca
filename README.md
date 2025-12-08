Este proyecto implementa una API REST que sirve como el backend (servidor) central para la gestión de una biblioteca. Está construido utilizando el framework Spring Boot y el sistema de gestión de persistencia Spring Data JPA con una base de datos MySQL.

Funcionalidades Clave de la API:

- Gestión de Entidades: CRUD (Creación, Lectura, Actualización y Eliminación) para Socios, Libros y Préstamos.
- Reglas de Negocio: Implementación de la lógica de un préstamo (ej. cálculo de la fecha límite de devolución a +2 días).
- Documentación: Uso de Springdoc OpenAPI (Swagger UI) para exponer y documentar todos los endpoints de la API.

Tecnologías Utilizadas:

- Backend: Java 17/21, Spring Boot 3.x
- Base de Datos: MySQL (con driver JDBC)
- Persistencia: Spring Data JPA / Hibernate
- Utilidades: Lombok, Springdoc OpenAPI (Swagger)
