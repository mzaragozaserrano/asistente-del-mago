Plan Técnico: "El Asistente del Mago" (v1.0)
Basado en la "Especificación de Producto v1.0" y las decisiones de stack.
1. Stack Tecnológico (Confirmado)
Plataforma Backend (BaaS): Firebase
Autenticación: Firebase Authentication (Email, Google)
Base de Datos (Nube): Cloud Firestore
BBDD Local: Android Room
Funciones Serverless: Firebase Cloud Functions (en TypeScript)
Arquitectura (Android): MVVM (Model-View-ViewModel)
UI (Android): Jetpack Compose
Red (Android): Retrofit (con corutinas de Kotlin)
Inyección de Dependencias: Koin
API de Búsqueda IA: Google Gemini (con Google Search Tool)
API de Análisis de Voz: AssemblyAI
Gestión de Suscripciones: RevenueCat
2. Arquitectura General de la App (Android MVVM)
La aplicación seguirá una arquitectura MVVM limpia, separada por capas (UI, ViewModel, Dominio, Datos) y modularizada por funcionalidad (feature).
UI Layer (Compose): Contiene los @Composable (pantallas y componentes). Solo observa el StateFlow del ViewModel y le notifica eventos (clicks, entradas de texto).
ViewModel Layer: Contiene la lógica de UI. Expone un único StateFlow con el estado de la pantalla y maneja los eventos de la UI. Llama a los UseCases (Casos de Uso) del Dominio.
Domain Layer: Contiene la lógica de negocio pura (en UseCases). No depende de Android ni de librerías externas. Coordina los Repositories.
Data Layer: Contiene las implementaciones de los Repositories. Decide si obtener datos de la fuente local (Room) o remota (Firestore/Retrofit).
3. Modelos de Datos (Data Models)
Definimos los modelos principales. Se usarán modelos DTO (Data Transfer Object) para la red y modelos de Entidad para Room/Firestore.
// --- Modelo Principal (Dominio y Firestore) ---
data class MagicTrick(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val title: String,
    val description: String? = null,
    val materials: String? = null,
    val angles: String? = null,
    val resetTime: String? = null,
    val videoLinks: List<String> = emptyList(), // Para URLs de referencia
    val tags: List<String> = emptyList(), // Para filtros
    val createdAt: Long = System.currentTimeMillis()
)

// --- Modelo Principal (Room Entity) ---
@Entity(tableName = "tricks")
data class TrickEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    // ... otros campos
    val tags: List<String>,
    val lastSynced: Long // Para sincronización
)

// --- Modelo para Sets/Carpetas (Firestore) ---
data class MagicSet(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val name: String,
    val trickIds: List<String> // Lista ordenada de IDs de trucos
)


4. Flujo de Datos y Red (Data Flow)
4.1. Repositorio de Repertorio (TricksRepository)
Función: Sincronizar el "Arsenal" entre Room (local) y Firestore (nube).
Estrategia: Offline-first. La UI (vía ViewModel) siempre lee de Room (Flow<List<TrickEntity>>).
Escritura (Crear/Editar):
Escribir inmediatamente en Room (la UI se actualiza al instante).
Poner en cola la escritura en Firestore.
Sincronización:
Al iniciar la app (autenticado), onSnapshot de Firestore se activa.
Compara lastSynced local con los datos de Firestore y actualiza Room.
4.2. Flujo de Red 1: Búsqueda IA (Gemini)
El cliente (Android) nunca llamará a la API de Gemini directamente. Usará una Cloud Function.
App (Android):
SearchViewModel llama a SearchUseCase("...descripción...").
SearchRepository usa Retrofit para llamar a nuestra Cloud Function (ej. POST /api/searchEffect).
Firebase Cloud Function (searchEffect):
Recibe la descripción del usuario.
Valida la autenticación (comprueba el userId).
Verifica el Límite de Uso: Consulta Firestore para ver si el usuario (no-premium) ha superado sus 5 búsquedas/mes. Si lo supera, devuelve error 429.
Llama a la API de Gemini (con la herramienta de Google Search activada).
Formatea la respuesta de Gemini (extrayendo enlaces y títulos) y la devuelve como JSON a la app.
App (Android):
Recibe el JSON y lo muestra en la UI.
4.3. Flujo de Red 2: Análisis de Voz (AssemblyAI)
El proceso de AssemblyAI suele ser asíncrono (subir, procesar, consultar).
App (Android):
Graba el audio del usuario (ej. en formato .m4a o .wav).
VoiceViewModel llama a AnalyzeSpeechUseCase(audioFile).
App (Android) - Subida:
La app llama a una Cloud Function (getUploadUrl) para obtener una URL de subida segura (Signed URL) a un bucket de Google Cloud Storage (privado).
La app sube el archivo de audio directamente a esa URL en GCS.
App (Android) - Iniciar Análisis:
La app llama a una segunda Cloud Function (startAnalysis) con la URL del archivo en GCS.
Firebase Cloud Function (startAnalysis):
Recibe la URL del archivo.
Llama a la API de AssemblyAI (ej. POST /v2/transcript) pasando la URL del audio.
Guarda el transcript_id devuelto por AssemblyAI en Firestore, asociado al usuario y al truco.
AssemblyAI (Webhook):
Cuando AssemblyAI termina el análisis, llama a un Webhook (otra Cloud Function, ej. onAnalysisComplete).
Firebase Cloud Function (onAnalysisComplete):
Recibe el análisis completo de AssemblyAI (transcripción, muletillas, ritmo).
Guarda este informe de resultados en Firestore, en el documento correspondiente al transcript_id.
App (Android):
La app está escuchando (con onSnapshot) los cambios en el documento de análisis en Firestore.
Cuando el informe aparece, el ViewModel lo recibe y actualiza la UI de "Cargando..." a "¡Análisis Completo!".
4.4. Flujo de Red 3: Suscripciones (RevenueCat)
Usamos RevenueCat para abstraer la complejidad de Google Play Billing.
App (Android):
Inicializa el SDK de RevenueCat con la clave de API.
Obtiene los "Offerings" (Planes Premium) de RevenueCat y los muestra en la pantalla de pago.
App (Android) - Compra:
El usuario pulsa "Comprar".
La app llama a RevenueCat.purchasePackage(). RevenueCat gestiona el diálogo de Google Play.
RevenueCat (Webhook):
RevenueCat verifica la compra con Google Play.
Envía un Webhook (ej. SUBSCRIPTION_ACTIVATED) a nuestra Cloud Function.
Firebase Cloud Function (handleRevenueCatWebhook):
Recibe el evento de RevenueCat.
Valida el userId.
Actualiza el "Custom Claim" del usuario en Firebase Authentication (ej. customClaims: { premium: true }).
App (Android):
RevenueCat SDK actualiza el estado local (PurchaserInfo).
La app comprueba el Custom Claim (premium: true) o el estado de RevenueCat para desbloquear las funciones. Los "Security Rules" de Firestore también usarán este Claim.
5. Security Rules (Firestore)
Reglas clave para proteger los datos (Borrador):
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {

    // Los trucos y sets solo pueden ser leídos/escritos por su propietario
    match /tricks/{trickId} {
      allow read, write: if request.auth.uid == resource.data.userId;
    }
    match /magicSets/{setId} {
      allow read, write: if request.auth.uid == resource.data.userId;
    }
    
    // Los resultados del análisis de voz solo pueden ser leídos por su propietario
    match /speechAnalysis/{analysisId} {
      allow read: if request.auth.uid == resource.data.userId;
      // Solo el servidor (Cloud Functions) puede escribir resultados
      allow write: if false; 
    }
  }
}


6. Módulos de Android (Gradle Modules)
Para escalar y mantener la separación de capas:
:app (Contiene la UI de Compose, ViewModels, Koin)
:core:model (Contiene los modelos de datos puros)
:core:common (Utilidades, constantes)
:core:data (Implementación de Repositories, Room, Retrofit)
:core:domain (Interfaces de Repositories, UseCases)
:feature:repertory (Módulo para la Épica 1)
:feature:search (Módulo para la Épica 2)
:feature:voice (Módulo para la Épica 3)
:feature:premium (Módulo para la Épica 4)
