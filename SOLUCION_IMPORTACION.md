# Solución para Problemas de Importación de Gradle

## Problema
Android Studio no puede completar la importación del proyecto de Gradle, lo que causa que los archivos aparezcan como "untracked" aunque estén correctamente en el repositorio.

## Soluciones

### 1. Verificar Conexión a Internet
- Gradle necesita descargar dependencias desde internet
- Verifica que tengas conexión estable
- Si estás detrás de un proxy, configúralo en Android Studio: **File > Settings > Appearance & Behavior > System Settings > HTTP Proxy**

### 2. Limpiar Caché de Gradle
```bash
# Desde la terminal en la carpeta del proyecto
rm -rf ~/.gradle/caches/
rm -rf .gradle
rm -rf build
rm -rf */build
```

### 3. Invalidar Cachés de Android Studio
1. **File > Invalidate Caches / Restart**
2. Selecciona **"Invalidate and Restart"**
3. Espera a que Android Studio se reinicie completamente

### 4. Verificar Configuración de JDK
1. **File > Project Structure > SDK Location**
2. Verifica que **JDK location** esté configurado (JDK 17 recomendado)
3. Si no está configurado, selecciona la ruta a tu JDK

### 5. Verificar Configuración de Gradle en Android Studio
1. **File > Settings > Build, Execution, Deployment > Build Tools > Gradle**
2. Verifica que:
   - **Gradle JDK** esté configurado (JDK 17)
   - **Build and run using** esté en "Gradle"
   - **Run tests using** esté en "Gradle"

### 6. Forzar Sincronización Manual
1. Cierra Android Studio completamente
2. Elimina las carpetas `.idea` y `.gradle` del proyecto (si existen)
3. Abre Android Studio
4. **File > Open** y selecciona la carpeta del proyecto
5. Espera a que Android Studio detecte el proyecto
6. Si aparece un banner, haz clic en **"Sync Now"**
7. O ve a **File > Sync Project with Gradle Files**

### 7. Verificar que los Archivos del Wrapper Estén Presentes
Los siguientes archivos deben existir en el proyecto:
- `gradlew` (ejecutable)
- `gradlew.bat`
- `gradle/wrapper/gradle-wrapper.jar`
- `gradle/wrapper/gradle-wrapper.properties`

### 8. Si el Problema Persiste
1. Cierra Android Studio
2. Elimina completamente las carpetas:
   - `.idea/`
   - `.gradle/`
   - `build/`
   - `*/build/`
3. Abre Android Studio
4. **File > Open** y selecciona la carpeta del proyecto
5. Espera pacientemente a que se complete la importación (puede tardar varios minutos)

## Verificación
Una vez que la importación se complete, deberías ver:
- Los módulos en el panel de proyecto
- Sin errores en la barra de estado
- La opción de ejecutar la app disponible

