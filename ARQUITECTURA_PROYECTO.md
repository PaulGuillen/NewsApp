# Arquitectura y Scope del Proyecto InfoXPeru

## 1. Resumen ejecutivo

`InfoXPeru` es una aplicacion Android multi-modulo orientada a informacion util para usuarios en Peru. El producto combina:

- autenticacion de usuarios,
- indicadores y datos de interes general,
- agregacion de noticias desde varias fuentes,
- directorio y acceso rapido a servicios de emergencia,
- perfil de usuario y espacio de comentarios,
- guardado local de noticias para lectura posterior.

La arquitectura actual mezcla una base de `Clean Architecture` por feature, UI en `Jetpack Compose`, manejo de estado tipo `MVI/MVVM`, inyeccion de dependencias con `Koin`, consumo de `REST APIs`, y uso intensivo de `Firebase Auth` + `Firestore`.

El proyecto ya esta modularizado y funcional en sus rutas principales, pero convive con piezas heredadas, modulos parcialmente implementados y algunos flujos todavia incompletos.

## 2. Stack tecnico real del repositorio

| Area | Implementacion actual |
|---|---|
| Lenguaje | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Navegacion activa | Navigation Compose |
| Navegacion heredada | Infraestructura basada en Fragment/NavHostFragment en `navigation` y `core-platform` |
| Estado UI | `StatefulViewModel`, `StatelessViewModel`, `ResultState`, `UiIntent`, `UiEvent`, `UiState` |
| DI | Koin con anotaciones KSP |
| Networking | Retrofit + OkHttp + Gson + converter SimpleXML |
| Persistencia local | DataStore Preferences |
| Backend cloud | Firebase Auth + Firebase Firestore |
| Logging | Timber |
| Inspeccion HTTP debug | Chucker |
| Build system | Gradle Kotlin DSL |
| SDK | `compileSdk 36`, `targetSdk 36`, `minSdk 28` |
| Java | Java 21 |
| Flavors | `dev` y `prod` |

## 3. Estructura modular

### 3.1 Modulos raiz

| Modulo | Rol principal | Observaciones |
|---|---|---|
| `app` | Punto de entrada, arranque de Koin, `MainActivity`, `NavHost` Compose | Orquesta todos los modulos funcionales |
| `core-domain` | Contratos base de dominio, `Output`, `Defaults`, contratos de use case | No depende de Android |
| `core-data` | Red, interceptores, `safeApiCall`, DataStore, Firebase providers, rutas `Screen` | Es la capa comun de datos e infraestructura de red |
| `core-platform` | Base de UI, tema, lifecycle helpers, fragment/activity legacy | Contiene la base MVI/MVVM usada por Compose |
| `navigation` | Infraestructura de navegacion modular legacy | La app actual usa Compose Navigation; este modulo queda como soporte heredado |
| `session` | Utilidades de sesion simples | Uso bajo; existe como modulo compartido |
| `feature:shared` | Componentes UI reutilizables, skeletons, store local de noticias | Modulo comun de presentacion |

### 3.2 Modulos por feature

| Modulo | Responsabilidad funcional | Estado |
|---|---|---|
| `feature:auth` | Login, registro, recuperacion de contrasena | Activo |
| `feature:home` | Home con indicadores, estaciones, feriados, accesos a emergencia y agradecimientos | Activo |
| `feature:news` | Catalogo de paises y noticias desde Google, Reddit y GDELT | Activo |
| `feature:emergency` | Pantalla de emergencias y detalle de servicios | Activo, con partes parciales |
| `feature:profile` | Perfil, edicion, sugerencias, comentarios, likes, logout | Activo, con ruta About vacia |
| `feature:mylist` | Noticias guardadas por leer / leidas | Funcional en UI, ViewModel incompleto |

### 3.3 Dependencias reales entre modulos

```text
app
|- core-domain
|- core-data
|- core-platform
|- navigation
|- feature:shared
|- feature:auth
|- feature:home
|- feature:news
|- feature:emergency
|- feature:mylist
`- feature:profile

feature:home
|- feature:news
|- feature:emergency
|- feature:mylist
|- feature:profile
`- feature:shared
```

Ademas, todos los modulos `feature:*` heredan por plugin comun dependencias base hacia:

- `core-platform`
- `core-domain`
- `core-data`
- `navigation`
- `session`

Esto significa que la modularizacion es real, pero no completamente aislada. En particular, `feature:home` esta acoplado a otras features para integrarlas visualmente.

## 4. Arquitectura logica

### 4.1 Patron general por feature

Cada feature sigue de forma bastante consistente este flujo:

```text
Composable Screen
  -> UiIntent
  -> ViewModel
  -> UseCase
  -> Repository
  -> DataSource
  -> Retrofit / Firebase / DataStore
  -> Mapper
  -> Entidad de dominio
  -> ResultState / UiState
  -> Render UI
```

### 4.2 Capas

#### Presentacion

- `Screen.kt` o `*Screen.kt`: composables de pantalla.
- `*UiState`, `*UiIntent`, `*UiEvent`: contrato de estado/eventos por pantalla.
- `*ViewModel`: coordinacion de casos de uso, manejo de carga, errores y eventos de navegacion.
- `feature:shared`: scaffolds, bottom bar, headers, dialogs, skeletons, source selector, coachmark.

#### Dominio

- `domain/entity/*`: entidades consumidas por UI.
- `domain/repository/*`: contratos.
- `domain/usecase/*`: logica de negocio por feature.
- `core-domain`: contratos base (`Output`, `Defaults`, `SimpleUC`, `FlowUC`).

#### Datos

- `data/datasource/remote/*`: acceso a Retrofit o Firebase.
- `data/datasource/mapper/*`: mapeo DTO -> entidad.
- `data/repository/*`: implementacion de repositorios.
- `core-data`: `safeApiCall`, interceptores, Retrofit, DataStore, Firebase DI.

## 5. Manejo de estado y ciclo de vida

La base de estado propia vive en `core-platform`:

- `StatelessViewModel`: recibe intents por `Channel`, emite eventos por `SharedFlow`, expone loading y errores por defecto.
- `StatefulViewModel`: agrega `UiState`, `LiveData`, `StateFlow` y persistencia opcional con `SavedStateHandle`.
- `ResultState`: modela `Idle`, `Loading`, `Success`, `Error`.
- `BaseScreenWithState`: conecta ViewModel, init, eventos, snackbar, resultados de navegacion y back handling.
- `BaseContentLayout`: layout reusable para header/body/footer con soporte a status bar, IME y navigation bars.

En la practica, el proyecto implementa un patron cercano a:

- `MVI` para el contrato `Intent -> State/Event`
- `MVVM` para la organizacion por `ViewModel`
- `Clean Architecture` para la separacion entre UI, dominio y datos

## 6. Arranque de la aplicacion

El flujo de arranque es:

1. `AndroidApplication` inicia Koin.
2. `AppModule` agrega modulos anotados de `core-data`, `navigation`, `shared` y todas las features.
3. `TimberFactory.setup()` habilita logging.
4. `MainActivity` crea un `NavController` Compose.
5. `UpdateChecker` revisa si existe update forzado.
6. Si no hay bloqueo por update, `MainGraph` monta el `NavHost`.

Nota importante:

- La verificacion de update forzado esta conectada en UI, pero `FORCE_UPDATE_URL` esta vacio. En el estado actual no bloquea nunca la app.

## 7. Navegacion actual

### 7.1 Rutas Compose activas

Las rutas definidas en `core-data/Screen.kt` y registradas en `MainGraph` son:

```text
login
register
home
acknowledgment
news
emergency
emergency_detail/{type}
myList
profile
profile/update
profile/suggestions
profile/about
```

### 7.2 Start destination

- `login`

### 7.3 Navegacion principal de usuario

- Bottom navigation: `Inicio`, `Noticias`, `Mi Lista`, `Perfil`
- FAB central: acceso rapido a `Emergency`

### 7.4 Infraestructura heredada

El modulo `navigation` mantiene:

- `MainNavigator`
- `FragmentNavigator`
- `ModularActivity`
- builders para fragment navigation

Sin embargo, la app visible hoy navega con `Navigation Compose`. Por eso este modulo debe entenderse como infraestructura heredada o base para migraciones, no como el motor principal actual.

## 8. Estrategia de datos e integraciones

### 8.1 DataStore local

Se usan al menos dos stores locales:

1. `settings`
   - `logIn`
   - `userUID`
   - `coachMarkNews`
   - `profile_data`

2. `mylist_datastore`
   - `saved_news_json_array`

Observacion tecnica:

- `DataStoreUseCaseImpl` encapsula lecturas/escrituras con `runBlocking`, por lo que la interfaz queda simple pero sincrona.

### 8.2 Retrofit y base URLs

El proyecto usa un `Retrofit` principal con `BuildConfig.BASE_URL`, pero la resolucion final de host se cambia por `BaseUrlInterceptor` segun el path solicitado.

#### Flavors

| Flavor | BASE_URL principal |
|---|---|
| `prod` | `https://infoperu-be.fly.dev` |
| `dev` | `http://192.168.100.6:3000/` |

#### Hosts adicionales decididos por interceptor

- `https://newsapi.org/`
- `https://deperu.com/api/rest/`
- `https://api.apis.net.pe/`
- `https://news.google.com/`
- `https://api.gdeltproject.org/api/v2/`
- `https://www.reddit.com/`

Este enfoque permite reutilizar un `OkHttpClient` y un `Retrofit` base, aunque concentra bastante logica de integracion en el interceptor.

### 8.3 Firebase

#### Firebase Auth

- login
- registro
- recuperacion de contrasena

#### Firestore

Colecciones/documentos identificados en codigo:

- `users`
- `country`
- `sectionItems`
- `gratitude`
- `district/section`
- `district/general`
- `district/civil_defense`
- `district/{police type}`
- `comments`
- `posts`

### 8.4 Observabilidad y red

- `HttpLoggingInterceptor`
- `Timber`
- `Chucker` solo en debug

## 9. Scope funcional actual

### 9.1 Auth

Implementado:

- login con Firebase Auth
- registro con creacion de documento `users` en Firestore
- recordar sesion con DataStore (`logIn`, `userUID`)
- recuperacion de contrasena
- auto-redireccion cuando hay sesion recordada

No implementado / no visible:

- login social
- MFA
- expiracion o refresh de sesion avanzada

### 9.2 Home

Implementado:

- valor de la UIT
- tipo de cambio dolar compra/venta
- tipo de cambio SUNAT
- alerta de feriados
- informacion de estaciones
- secciones dinamicas desde Firestore
- bottom sheet de accesos rapidos a emergencia
- ruta a agradecimientos

Fuentes:

- `deperu.com`
- `apis.net.pe`
- Firestore

### 9.3 News

Implementado:

- carga de paises desde Firestore
- priorizacion inicial de Peru, Argentina y Mexico
- seleccion de fuente por pais
- Google News RSS
- Reddit
- GDELT Project
- paginacion visual en UI por carga incremental
- coachmark de onboarding
- guardado local de articulos

Observaciones:

- se abre la noticia en navegador externo
- la ruta `NewsDetail` esta declarada pero no esta registrada ni usada

### 9.4 Emergency

Implementado:

- numeros criticos nacionales: Policia, Bomberos, SAMU
- otros accesos: Linea 100, Indeci, EsSalud, Cruz Roja
- pantalla detalle para:
  - servicios generales,
  - defensa civil,
  - policia por region
- busqueda en listados por distrito o direccion
- apertura de marcador telefonico

Parcial:

- `firefighter` tiene placeholder sin implementacion real
- `local_security` tiene placeholder sin implementacion real

### 9.5 MyList

Implementado funcionalmente desde UI:

- listado de noticias guardadas
- tabs `Por leer` / `Leidas`
- abrir articulo guardado
- marcar articulo como leido
- eliminar articulo con swipe o accion directa

Limitacion:

- `MyListViewModel` contiene `TODO("Not yet implemented")`
- la logica principal vive hoy en el composable y en `NewsSavedStore`, no en un flujo de dominio/presentacion completo

### 9.6 Profile

Implementado:

- carga de perfil desde Firestore
- cache del perfil en DataStore (`profile_data`)
- edicion de datos de perfil
- compartir la aplicacion
- apertura de terminos y politica de privacidad
- logout
- pantalla de sugerencias/comentarios
- creacion de comentarios
- carga de posts
- carga paginada de comentarios
- likes sobre posts/comentarios

Parcial:

- ruta `About` existe pero `AboutScreen` esta vacia

## 10. Scope tecnico y no funcional

### Incluido hoy

- Android app nativa
- multi-modulo Gradle
- dark/light theme
- debug de red
- seguridad basica de red via `network_security_config`
- `FileProvider`
- permisos para internet, camara, almacenamiento y llamada

### Fuera de alcance actual o no presente en codigo

- modo offline completo para datos remotos
- sincronizacion en background
- push notifications visibles en el codigo revisado
- deep links / app links
- analitica visible mas alla de dependencias Firebase
- test suite real de regresion

## 11. Riesgos, deuda tecnica y hallazgos

### 11.1 Inconsistencias funcionales o de implementacion

- `MyListViewModel` no esta implementado.
- `AboutScreen` esta vacia.
- `Screen.NewsDetail` no se usa.
- `UpdateChecker` esta conectado, pero desactivado de facto por URL vacia.
- `DetailsViewModel` deja sin implementar `firefighter` y `local_security`.

### 11.2 Deuda de arquitectura

- Conviven `Navigation Compose` y una capa de navegacion legacy por fragmentos.
- `feature:home` depende de otras features, por lo que no actua como modulo completamente desacoplado.
- `feature:emergency` y `feature:profile` declaran capas Retrofit (`EmergencyApi`, `ProfileApi`), pero sus `ServiceDS` actuales usan Firestore, dejando una capa preparada pero no activa.
- La resolucion de multiples hosts desde un solo interceptor simplifica wiring, pero centraliza demasiado conocimiento de integracion en `BaseUrlInterceptor`.

### 11.3 Deuda documental

- El `README.md` no refleja la configuracion actual del proyecto:
  - README indica `minSdk 26`, `compileSdk 34`, `targetSdk 34`, `Java 17`
  - el build actual usa `minSdk 28`, `compileSdk 36`, `targetSdk 36`, `Java 21`

### 11.4 Branding y naming

Se observan varias denominaciones para el mismo producto:

- `InfoXPeru`
- `InfoPeru`
- `Emergencias PE`

No rompe la arquitectura, pero si afecta consistencia funcional y mantenimiento.

## 12. Testing actual

El repositorio contiene la estructura de tests por modulo, pero la cobertura real es baja:

- la mayoria de tests son `ExampleUnitTest` o `ExampleInstrumentedTest`
- existe un `GDELTUseCaseTest`, pero esta comentado

Conclusion:

- el proyecto tiene una base productiva y modular,
- pero hoy depende mas de validacion manual que de automatizacion de pruebas.

## 13. Conclusiones

La arquitectura actual es suficiente para sostener una app Android de complejidad media con varias fuentes de datos y varias features, especialmente por:

- separacion modular,
- use cases por feature,
- base comun de estado UI,
- DI centralizada,
- reutilizacion de componentes compartidos.

Al mismo tiempo, el estado real del repositorio muestra una etapa intermedia de evolucion:

- Compose ya es la capa dominante,
- Firestore convive con REST,
- hay infraestructura heredada aun presente,
- y varias piezas del scope estan completas a nivel UX pero no cerradas a nivel arquitectura interna.

Si el objetivo es seguir escalando este proyecto, los siguientes candidatos naturales de refactor son:

1. cerrar `MyList` con dominio y ViewModel reales,
2. eliminar o aislar navegacion legacy no usada,
3. completar `About`, `firefighter` y `local_security`,
4. alinear README, branding y configuracion tecnica,
5. agregar tests reales sobre use cases y mappers clave.
