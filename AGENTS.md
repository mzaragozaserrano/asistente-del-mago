# El Asistente del Mago - Información del Proyecto

## Resumen Ejecutivo

"El Asistente del Mago" es una aplicación Android para magos que permite gestionar su repertorio de trucos, buscar nuevos efectos mediante IA, y analizar su presentación vocal. El proyecto sigue un modelo freemium con funciones Premium.

---

## Stack Tecnológico

### Backend y Servicios en la Nube
- **Plataforma BaaS**: Firebase
- **Autenticación**: Firebase Authentication (Email, Google)
- **Base de Datos en la Nube**: Cloud Firestore
- **Base de Datos Local**: Android Room
- **Funciones Serverless**: Firebase Cloud Functions (TypeScript)
- **Almacenamiento**: Google Cloud Storage (para archivos de audio)

### Frontend (Android)
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **UI Framework**: Jetpack Compose
- **Red**: Retrofit (con corutinas de Kotlin)
- **Inyección de Dependencias**: Hilt
- **Módulos**: Arquitectura modular por features (`:app`, `:core:model`, `:core:data`, `:core:domain`, `:feature:repertory`, `:feature:search`, `:feature:voice`, `:feature:premium`)

### APIs Externas
- **Búsqueda IA**: Google Gemini (con Google Search Tool)
- **Análisis de Voz**: AssemblyAI
- **Suscripciones**: RevenueCat (abstrae Google Play Billing)

### Estrategia de Datos
- **Offline-first**: La UI siempre lee de Room (local)
- **Sincronización**: Escritura inmediata en local, sincronización asíncrona con Firestore
- **Seguridad**: Firestore Security Rules basadas en Custom Claims de Firebase Auth

---

## Resumen de Requisitos (Épicas)

### ÉPICA 1: Gestión de Repertorio (CORE)
**Objetivo**: Base de datos robusta para catalogar y organizar trucos y sets de actuación.

**Funcionalidades principales**:
- Crear, editar y eliminar trucos con campos: título, descripción, materiales, ángulos, reseteo, enlaces de referencia, tags
- Buscar y filtrar trucos por texto o tags
- Crear sets de actuación y añadir trucos a sets
- Gestión de enlaces de referencia (YouTube, tutoriales, etc.)

**Etiquetas**: `@core`, `@repertory`, `@create_trick`, `@edit_trick`, `@filter`, `@search`, `@sets`, `@links`, `@tags`

---

### ÉPICA 2: Búsqueda IA (DESCUBRIMIENTO)
**Objetivo**: Permitir a los magos describir un efecto en lenguaje natural para encontrar dónde aprenderlo.

**Funcionalidades principales**:
- Búsqueda por descripción en lenguaje natural
- Resultados categorizados (YouTube, Tiendas, Foros)
- Guardar resultados directamente en el Arsenal
- **Límites Freemium**: 5 búsquedas/mes para usuarios gratuitos, ilimitadas para Premium

**Etiquetas**: `@ai_search`, `@freemium`, `@success`, `@limit`, `@premium`

---

### ÉPICA 3: Entrenador de Voz IA (PREMIUM)
**Objetivo**: Análisis objetivo de la presentación vocal del mago.

**Funcionalidades principales**:
- Grabación de charla del mago
- Análisis asíncrono que proporciona:
  - Transcripción completa
  - Detección de muletillas (ej: "eh", "vale")
  - Ritmo (Palabras por Minuto - PPM)
- **Acceso**: Solo usuarios Premium

**Etiquetas**: `@ai_voice`, `@premium`, `@premium_access`, `@gated`

---

### ÉPICA 4: Monetización (MVP)
**Objetivo**: Sistema de suscripciones Premium a través de Google Play Store.

**Funcionalidades principales**:
- Pantalla de beneficios Premium
- Integración con Google Play Billing (vía RevenueCat)
- Actualización automática del estado de cuenta tras la compra
- Verificación de acceso Premium en todas las funciones bloqueadas

**Beneficios Premium**:
- Búsquedas IA ilimitadas
- Acceso al Entrenador de Voz IA

**Etiquetas**: `@monetization`, `@premium`, `@paywall`, `@purchase`, `@access_check`

---

## Flujos Técnicos Clave

### Búsqueda IA
1. Usuario escribe descripción → Android llama a Cloud Function
2. Cloud Function valida límites (si no Premium) y llama a Gemini API
3. Gemini usa Google Search Tool para encontrar resultados
4. Resultados formateados se devuelven a la app
5. Usuario puede guardar resultados como trucos en su Arsenal

### Análisis de Voz
1. Usuario graba audio → App obtiene Signed URL de Cloud Function
2. App sube audio a Google Cloud Storage
3. App llama a Cloud Function para iniciar análisis en AssemblyAI
4. AssemblyAI procesa y envía webhook cuando termina
5. Cloud Function guarda resultados en Firestore
6. App escucha cambios en Firestore y actualiza UI

### Suscripciones
1. Usuario pulsa "Comprar" → RevenueCat gestiona Google Play Billing
2. RevenueCat envía webhook a Cloud Function
3. Cloud Function actualiza Custom Claim en Firebase Auth (`premium: true`)
4. App verifica Custom Claim para desbloquear funciones
5. Firestore Security Rules también usan el Custom Claim

---

## Modelos de Datos Principales

### MagicTrick
```kotlin
data class MagicTrick(
    val id: String,
    val userId: String,
    val title: String,
    val description: String?,
    val materials: String?,
    val angles: String?,
    val resetTime: String?,
    val videoLinks: List<String>,
    val tags: List<String>,
    val createdAt: Long
)
```

### MagicSet
```kotlin
data class MagicSet(
    val id: String,
    val userId: String,
    val name: String,
    val trickIds: List<String> // Lista ordenada
)
```

---

## Seguridad

- **Firestore Rules**: Los trucos y sets solo pueden ser leídos/escritos por su propietario (`userId`)
- **Análisis de Voz**: Solo lectura por propietario, escritura solo por Cloud Functions
- **Premium**: Verificado mediante Custom Claims en Firebase Auth

---

## Estado del Proyecto

- **Versión**: v1.0
- **Repositorio**: [asistente-del-mago](https://github.com/mzaragozaserrano/asistente-del-mago)
- **Issues**: 14 issues de tipo feature creados desde REQUIREMENTS.md
- **Documentación**: 
  - `REQUIREMENTS.md`: Especificación completa en Gherkin
  - `STACK.md`: Plan técnico detallado

