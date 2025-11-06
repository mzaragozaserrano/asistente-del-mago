# El Asistente del Mago

Aplicación Android para magos que permite gestionar su repertorio de trucos, buscar nuevos efectos mediante IA, y analizar su presentación vocal.

## Stack Tecnológico

- **Arquitectura**: MVVM (Model-View-ViewModel)
- **UI**: Jetpack Compose
- **Base de Datos Local**: Room
- **Inyección de Dependencias**: Hilt
- **Lenguaje**: Kotlin

## Estructura del Proyecto

El proyecto sigue una arquitectura modular:

- `:app` - Módulo principal de la aplicación
- `:core:model` - Modelos de datos
- `:core:domain` - Lógica de negocio (UseCases, Interfaces)
- `:core:data` - Implementación de datos (Room, Repositories)
- `:feature:repertory` - Feature de gestión de repertorio

## Funcionalidades Implementadas

### Issue #1: Crear un nuevo truco con todos los campos

- ✅ Pantalla "Mi Arsenal" que muestra la lista de trucos
- ✅ Formulario para crear nuevos trucos con campos:
  - Título (obligatorio)
  - Descripción
  - Materiales
  - Ángulos
  - Reseteo
- ✅ Persistencia local con Room
- ✅ Arquitectura MVVM con Jetpack Compose

## Requisitos

- Android Studio Hedgehog | 2023.1.1 o superior
- JDK 17
- Android SDK 24+

## Instalación

1. Clona el repositorio
2. Abre el proyecto en Android Studio
3. Sincroniza los proyectos Gradle
4. Ejecuta la aplicación

## Estado del Proyecto

Este es un proyecto en desarrollo. La funcionalidad básica de creación de trucos está implementada según el Issue #1.

