# pedidos-api
Gestor de Pedidos – Backend
1. Resumen del proyecto

API REST para la gestión de pedidos con CRUD completo, validaciones estrictas y documentación Swagger/OpenAPI.

2. Tecnologías

Java 21

Spring Boot 3.2.x

Spring Data JPA

Jakarta Bean Validation

Springdoc OpenAPI 2.3.x

3. Funcionalidades implementadas

CRUD de Pedidos

Crear (POST /api/pedidos)

Listar todos (GET /api/pedidos)

Obtener por ID (GET /api/pedidos/{id})

Actualizar (PUT /api/pedidos/{id})

Actualizar solo estado (PATCH /api/pedidos/{id}/estado)

Eliminar (DELETE /api/pedidos/{id})

DTOs y Validaciones

PedidoDTO y EstadoDTO con validaciones: @NotBlank, @NotNull, @Size

Uso de enum Estado (PENDIENTE, EN_PROCESO, COMPLETADO) para restringir valores válidos

Errores claros en caso de datos inválidos (IllegalArgumentException, PedidoNotFoundException)

Service y Logging

Métodos transaccionales (@Transactional) para update/delete

Logging con SLF4J (info, debug, error) para trazabilidad

Swagger / OpenAPI

Documentación completa con ejemplos y valores permitidos para enums

@Schema para DTOs, @Operation y @ApiResponse para endpoints

Ajustes de versión y dependencias para que /v3/api-docs funcione correctamente

4. Problemas solucionados durante el desarrollo

Springdoc no generaba documentación correctamente → solución: Spring Boot 3.2.x + Springdoc 2.3.x

Validaciones sobre enum generaban errores → reemplazado @Pattern por enum y @NotNull

Antes se podían ingresar estados inválidos → ahora solo acepta los definidos en el enum

Logging detallado para operaciones y errores

Limpieza de proyecto y sincronización Maven para que Swagger y la app funcionen correctamente

5. Ejemplos de Requests / Responses

Crear Pedido

POST /api/pedidos
{
  "descripcion": "Compra de insumos",
  "estado": "PENDIENTE"
}


Response 200

{
  "id": 1,
  "descripcion": "Compra de insumos",
  "estado": "PENDIENTE",
  "fechaCreacion": "2025-08-28T20:21:02.245Z"
}


Actualizar solo Estado

PATCH /api/pedidos/1/estado
{
  "estado": "EN_PROCESO"
}


Response 200

{
  "id": 1,
  "descripcion": "Compra de insumos",
  "estado": "EN_PROCESO",
  "fechaCreacion": "2025-08-28T20:21:02.245Z"
}


Error de validación

POST /api/pedidos
{
  "descripcion": "",
  "estado": null
}


Response 400

{
  "timestamp": "2025-08-28T20:25:00.000Z",
  "status": 400,
  "error": "Error de validación",
  "message": "La descripción no puede estar vacía"
}