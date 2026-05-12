# Infalible Real Estate - Android App

**Autor:** Ramón Emilio López
---

## 📱 Sobre la Aplicación

**Infalible Real Estate App** es la aplicación móvil nativa para Android del ecosistema Infalible Real Estate.  Desarrollada con tecnologías modernas de Android, proporciona una experiencia móvil optimizada para la gestión, búsqueda y visualización de propiedades inmobiliarias, permitiendo a los usuarios gestionar sus intereses y coordinar visitas desde cualquier lugar.

---

## ✨ Funcionalidades Principales

### 🏠 **Gestión de Propiedades**
- Crear, modificar y eliminar propiedades inmobiliarias
- Subir y gestionar múltiples imágenes por propiedad
- Filtrado avanzado por:
  - Precio (mínimo y máximo)
  - Número de habitaciones
  - Número de baños
  - Espacios de parqueo
  - Tipo de transacción (venta/alquiler)
  - Categoría de propiedad
- Visualización detallada de propiedades con galería de imágenes
- Catálogo completo con búsqueda y ordenamiento

### 🛒 **Carrito de Interesados**
- Agregar propiedades al carrito para seguimiento
- Gestionar lista de propiedades de interés
- Eliminar propiedades específicas o vaciar el carrito completo
- Visualización de detalles desde el carrito
- Solicitud de compra directa vía WhatsApp

### 🏡 **Navegación Intuitiva**
- Pantalla principal con acceso rápido a categorías
- Vista de propiedades destacadas
- Navegación fluida entre secciones
- Bottom navigation bar persistente
- Animaciones y transiciones suaves

### 👤 **Gestión de Perfil**
- Visualización y edición de información personal
- Actualización de nombre, apellido y teléfono
- Sincronización automática con el servidor
- Cierre de sesión seguro

### 🔄 **Sincronización Automática**
- Work Manager para sincronización en segundo plano
- Actualización periódica de datos cada 24 horas
- Sincronización automática cuando hay conexión
- Persistencia local con Room Database

---

## 🛠️ Tecnologías y Arquitectura

### **Stack Tecnológico**

#### Lenguaje y Framework
- **Kotlin** - Lenguaje principal (100% Kotlin)
- **Jetpack Compose** - UI moderna y declarativa
- **Material Design 3** - Sistema de diseño

#### Arquitectura
- **MVI (Model-View-Intent)** - Patrón arquitectónico
- **Clean Architecture** - Separación de capas (Data, Domain, Presentation)
- **Single Activity** - Navegación con Navigation Compose

#### Inyección de Dependencias
- **Dagger Hilt** - DI framework
- **Hilt Navigation Compose** - Integración con ViewModels

#### Persistencia y Red
- **Room Database** - Base de datos local SQLite
- **Retrofit** - Cliente HTTP para API REST
- **Moshi** - Serialización JSON
- **OkHttp** - Logging y manejo de requests

#### Asincronía y Reactividad
- **Kotlin Coroutines** - Programación asíncrona
- **Flow** - Manejo de streams reactivos
- **StateFlow** - Estado reactivo en ViewModels

#### Background Processing
- **WorkManager** - Tareas en segundo plano
- **Hilt Work** - Integración de Hilt con WorkManager

#### Imágenes
- **Coil** - Carga y caché de imágenes

#### Testing
- **JUnit** - Testing unitario
- **MockK** - Mocking para tests
- **Coroutines Test** - Testing de coroutines
- **Espresso** - Testing de UI

---

## 📂 Estructura del Proyecto

```
app/src/main/java/com/infaliblerealestate/
│
├── data/                          # Capa de Datos
│   ├── local/                     # Base de datos local (Room)
│   ├── remote/                    # API REST (Retrofit)
│   └── repository/                # Implementación de repositorios
│
├── dominio/                       # Capa de Dominio
│   ├── model/                     # Modelos de dominio
│   └── usecase/                   # Casos de uso (lógica de negocio)
│       ├── propiedades/
│       ├── usuarios/
│       └── workerhelper/
│
├── presentation/                  # Capa de Presentación
│   ├── home/                      # Pantalla principal
│   ├── catalogo/                  # Catálogo de propiedades
│   ├── carrito/                   # Carrito de interesados
│   ├── settings/                  # Configuración de usuario
│   ├── login/                     # Autenticación
│   ├── upsertPropiedad/          # Crear/Editar propiedades
│   └── util/                      # Utilidades compartidas
│       ├── components/            # Componentes reutilizables
│       ├── navigation/            # Sistema de navegación
│       └── validation/            # Validaciones
│
├── worker/                        # Workers para tareas en background
│   ├── SyncUsuarioWorker.kt
│   └── WorkManagerHelper.kt
│
├── ui/theme/                      # Tema y estilos
│
├── MainActivity.kt                # Activity principal
└── MyApplication.kt              # Application class con Hilt
```

---

## 🎨 Características de UI/UX

### Componentes Personalizados
- **PropiedadItem** - Card de propiedad con imágenes
- **PropiedadChip** - Chips de filtrado y categorías
- **SheetPropiedadDetalle** - Bottom sheet con detalles
- **CircularWavyProgressIndicator** - Indicador de carga personalizado
- **ThemedSnackbarHost** - Snackbars con tema consistente

### Experiencias Interactivas
- **Pull-to-Refresh** - Actualización manual de contenido
- **Bottom Navigation** - Navegación entre secciones principales
- **Modal Bottom Sheets** - Vistas detalladas sin cambiar de pantalla
- **Image Picker** - Selección múltiple de imágenes
- **Lazy Lists** - Listas optimizadas con scroll virtual

### Diseño Responsivo
- Edge-to-Edge display
- Material 3 Dynamic Colors
- Modo claro/oscuro (según tema del sistema)

---

## 👥 ¿Para quién es útil este proyecto?

### Usuarios Finales
- **Compradores/Arrendatarios**: Buscar propiedades fácilmente desde dispositivos móviles
- **Propietarios**: Publicar y gestionar sus propiedades en cualquier momento

### Profesionales
- **Agentes Inmobiliarios**: Gestionar carteras de propiedades sobre la marcha
- **Empresas de Bienes Raíces**: Ofrecer una experiencia móvil a sus clientes

### Desarrolladores
- **Estudiantes**: Aprender arquitectura moderna de Android
- **Desarrolladores Android**: Referencia de implementación con Clean Architecture y Jetpack Compose

---

## 📸 Capturas de Pantalla
<img width="259" height="541" alt="image" src="https://github.com/user-attachments/assets/569edddb-85f6-4bf8-887d-73a1cbebd393" />
<img width="258" height="544" alt="image" src="https://github.com/user-attachments/assets/62ad8adf-e34d-4fbf-bbe8-4e9368d3757c" /><img width="260" height="545" alt="image" src="https://github.com/user-attachments/assets/61e3e3a2-4068-4859-a7bd-981bacf80b96" /><img width="259" height="546" alt="image" src="https://github.com/user-attachments/assets/ddd86cda-6463-4abb-a4c5-d88ee8157d32" />
<img width="265" height="544" alt="image" src="https://github.com/user-attachments/assets/9eb9eeee-f80f-45e5-89f4-49e0737de233" />
<img width="262" height="545" alt="image" src="https://github.com/user-attachments/assets/d14ec8f1-8a31-4aad-88b4-4f690790dab9" /><img width="262" height="541" alt="image" src="https://github.com/user-attachments/assets/f0ed8b54-e88a-451f-8248-1f8fb9645712" /><img width="258" height="547" alt="image" src="https://github.com/user-attachments/assets/801247a4-dbae-412b-bbd8-8314a71b7d42" /><img width="262" height="547" alt="image" src="https://github.com/user-attachments/assets/8d5125e0-1842-4031-8538-fe3679f550ea" />










---

## 🔐 Seguridad

- Almacenamiento seguro de credenciales
- Validación de datos en cliente y servidor
- Manejo seguro de tokens de autenticación

---

## 📝 Licencia

Este proyecto es parte de un proyecto académico para la universidad. 

---

## 📧 Contacto

**Ramón Emilio López**  
[LinkedIn](www.linkedin.com/in/ramón-emilio-lopez-57a833211)

---

## 🙏 Agradecimientos

- Universidad Catolica Nordestana
- Profesor Enel Almonte
- Comunidad de Android Developers
- Documentación oficial de Android y Jetpack Compose

---

**Desarrollado con ❤️ usando Kotlin y Jetpack Compose**
