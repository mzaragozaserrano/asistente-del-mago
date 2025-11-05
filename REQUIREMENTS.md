Archivo Gherkin para la Especificación de "El Asistente del Mago" v1.0
-----------------------------------------------------------------
ÉPICA 1: GESTIÓN DE REPERTORIO (CORE)
-----------------------------------------------------------------
@core @repertory
Feature: Gestión de Repertorio (El Arsenal)
Como mago, quiero una base de datos robusta
para catalogar y organizar mis trucos y sets de actuación.
Background: Usuario Autenticado
Given que el usuario está registrado y ha iniciado sesión en la aplicación.
@create_trick
Scenario: Crear un nuevo truco con todos los campos
Given que el usuario está en la pantalla "Mi Arsenal"
When pulsa el botón "Añadir Truco"
And rellena el título con "Carta Ambiciosa"
And rellena la descripción con "Una carta firmada sube repetidamente a la parte superior de la baraja."
And rellena los materiales con "Baraja normal"
And rellena los ángulos con "Bueno por delante, no rodear"
And rellena el reseteo con "Instantáneo"
And pulsa el botón "Guardar"
Then el truco "Carta Ambiciosa" debe aparecer en la lista de "Mi Arsenal".
@edit_trick @links
Scenario: Añadir un enlace de referencia a un truco existente
Given que el usuario tiene un truco guardado llamado "Carta Ambiciosa"
When navega al detalle del truco "Carta Ambiciosa"
And pulsa el botón "Editar"
And añade el enlace "https://www.google.com/search?q=https://www.youtube.com/watch%3Fv%3Dtutorial_ambiciosa" en la sección de enlaces
And pulsa "Guardar"
Then el enlace "https://www.google.com/search?q=https://www.youtube.com/watch%3Fv%3Dtutorial_ambiciosa" debe estar visible en el detalle del truco.
@edit_trick @tags
Scenario: Añadir un nuevo tag a un truco
Given que el usuario está editando el truco "Carta Ambiciosa"
When escribe el nuevo tag "Cartas" en el campo de tags
And pulsa "Añadir Tag"
And pulsa "Guardar"
Then el truco "Carta Ambiciosa" debe mostrar el tag "Cartas".
@filter @search
Scenario Outline: Buscar y filtrar el arsenal
Given que el usuario está en la pantalla "Mi Arsenal"
And tiene un truco "Matrix" con el tag "Monedas"
And tiene un truco "Carta Ambiciosa" con el tag "Cartas"
When aplica el <filtro> con el valor "<valor>"
Then solo debe ver el truco "<truco_esperado>" en la lista.
Examples:  | filtro        | valor           | truco_esperado   |  | "Búsqueda"    | "Matrix"        | "Matrix"         |  | "Filtro Tag"  | "Cartas"        | "Carta Ambiciosa"|  | "Filtro Tag"  | "Monedas"       | "Matrix"         |
@sets
Scenario: Crear un nuevo Set de actuación
Given que el usuario está en la pestaña "Sets"
When pulsa el botón "Crear Set"
And introduce el nombre "Show de Cerca 2025"
And pulsa "Guardar"
Then el set "Show de Cerca 2025" debe aparecer en la lista de sets.
@sets
Scenario: Añadir un truco existente a un Set
Given que el usuario tiene el set "Show de Cerca 2025"
And tiene el truco "Matrix" en su arsenal
When está viendo el detalle del set "Show de Cerca 2025"
And pulsa "Añadir Truco al Set"
And selecciona "Matrix" de su arsenal
Then el truco "Matrix" debe aparecer en la lista de trucos del set "Show de Cerca 2025".
-----------------------------------------------------------------
ÉPICA 2: BÚSQUEDA IA (DESCUBRIMIENTO)
-----------------------------------------------------------------
@ai_search @freemium
Feature: Búsqueda IA (El Oráculo)
Como mago, quiero poder describir un efecto en lenguaje natural
para encontrar dónde aprenderlo.
Background: Usuario Autenticado
Given que el usuario ha iniciado sesión.
@ai_search @success
Scenario: Realizar una búsqueda de un efecto y guardar un resultado
Given que el usuario es un "Usuario Gratuito" y le quedan búsquedas
When navega a la pestaña "Búsqueda IA"
And escribe la descripción "efecto de carta que viaja al bolsillo"
And pulsa el botón "Buscar"
Then debe ver una lista de resultados categorizados (YouTube, Tiendas, Foros).
When pulsa el botón "Guardar en Arsenal" en el primer resultado
Then se debe crear un nuevo truco en "Mi Arsenal"
And el nuevo truco debe contener el enlace del resultado de búsqueda.
@ai_search @limit
Scenario: Usuario gratuito excede el límite de búsqueda
Given que el usuario es un "Usuario Gratuito"
And ya ha realizado 5 búsquedas este mes (límite de 5)
When navega a la pestaña "Búsqueda IA"
And escribe la descripción "flotación de un billete"
And pulsa el botón "Buscar"
Then debe ver un mensaje informando que ha alcanzado su límite mensual
And debe ver un botón para "Hacerse Premium".
@ai_search @premium
Scenario: Usuario premium realiza una búsqueda
Given que el usuario es un "Usuario Premium"
When navega a la pestaña "Búsqueda IA"
And escribe la descripción "cómo desaparecer un pañuelo"
And pulsa el botón "Buscar"
Then debe ver la lista de resultados
And no debe ver ningún mensaje sobre límites de búsqueda.
-----------------------------------------------------------------
ÉPICA 3: ENTRENADOR DE VOZ IA (PREMIUM)
-----------------------------------------------------------------
@ai_voice @premium
Feature: Entrenador de Voz IA (El Director)
Como mago Premium, quiero grabar mi charla
para recibir un análisis objetivo sobre mi presentación vocal.
@ai_voice @premium_access
Scenario: Usuario premium graba y analiza su charla
Given que el usuario es un "Usuario Premium"
And está viendo el detalle del truco "El Viajero" en su arsenal
When navega a la pestaña "Entrenador"
And pulsa el botón "Grabar"
And habla durante 30 segundos recitando su charla
And pulsa el botón "Finalizar y Analizar"
Then debe ver un indicador de "Procesando análisis...".
When el análisis se completa
Then debe ver la "Transcripción" de su charla
And debe ver la sección "Muletillas" con palabras como "eh", "vale"
And debe ver su "Ritmo (PPM)" (Palabras por Minuto).
@ai_voice @gated
Scenario: Usuario gratuito intenta acceder al Entrenador de Voz
Given que el usuario es un "Usuario Gratuito"
And está viendo el detalle del truco "El Viajero" en su arsenal
When navega a la pestaña "Entrenador"
Then debe ver un mensaje indicando que esta es una función Premium
And no debe ver el botón de "Grabar"
And debe ver un botón para "Actualizar a Premium".
-----------------------------------------------------------------
ÉPICA 4: MONETIZACIÓN (MVP)
-----------------------------------------------------------------
@monetization @premium
Feature: Monetización y Suscripciones
Como usuario, quiero poder entender los beneficios de la versión Premium
y suscribirme fácilmente a través de Google Play Store.
@monetization @paywall
Scenario: Usuario gratuito ve la pantalla de suscripción
Given que el usuario es un "Usuario Gratuito"
When navega a su Perfil
And pulsa el botón "Ver beneficios Premium"
Then debe ver una pantalla que lista los beneficios:
| Beneficio |
| Búsquedas IA Ilimitadas |
| Acceso al Entrenador de Voz IA|
@monetization @purchase
Scenario: Usuario gratuito se suscribe a Premium
Given que el usuario es un "Usuario Gratuito"
And está en la pantalla de suscripción
When pulsa el botón "Suscribirse (XX.XX€ / mes)"
And completa el flujo de pago de Google Play Store con éxito
Then su estado de cuenta en la app debe actualizarse a "Usuario Premium".
@monetization @access_check
Scenario: Usuario Premium comprueba el desbloqueo de funciones
Given que el usuario acaba de suscribirse y es "Usuario Premium"
When navega a la pestaña "Entrenador" de un truco
Then puede ver y usar la interfaz de grabación
And cuando navega a "Búsqueda IA", no ve ningún mensaje de límite.