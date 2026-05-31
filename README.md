# 📱 NewsApp / InfoPerú

Aplicación Android modular que consume APIs gratuitas para ofrecer:

- 📰 Noticias nacionales (Perú) e internacionales  
- 💱 Tipo de cambio del dólar  
- 📊 Valor de la UIT (Unidad Impositiva Tributaria)  
- 🆘 Directorio de distritos de Lima (Emergencias: policía, bomberos, serenazgo)  

<br><br>

## 🧭 Tecnologías principales

- **Kotlin** con **Jetpack Compose** — UI declarativa moderna  
- **Arquitectura limpia (Clean Architecture)** con múltiples módulos (`core-domain`, `core-data`, `feature`, etc.)  
- **MVI (Model‑View‑Intent)** — gestión fluida del estado UI  
- **Koin** — inyección de dependencias más ligera que Hilt  
- **MVVM** — integración coordinada con MVI y UseCases  
- **Node.js** — backend REST personalizado (propio API) [Backend](https://github.com/PaulGuillen/InfoPeru-BE)
- **Modularización** — división clara por características: `auth`, `home`, `navigation`, `session`, `core-*`  
- APIs REST: tipo de cambio, UIT, noticias (peruana/internacional), servicios municipales

<br><br>

## 🚀 Requisitos para ejecutar el proyecto

- **Android Studio**
- **Java 17** (`JAVA_HOME` configurado)
- `minSdk 26`, `compileSdk 34`, `targetSdk 34`
- Emulador o dispositivo con **API 27 (Oreo 8.1)** o superior

---

<br><br>

## 🛠️ Estructura de módulos

| Módulo             | Propósito                                 |
|--------------------|-------------------------------------------|
| `app`              | Punto de entrada, configuración de Koin   |
| `feature/auth`     | Pantallas y lógica de autenticación       |
| `feature/home`     | Pantalla principal y consumo de APIs      |
| `core-domain`      | Modelos, casos de uso, interfaces         |
| `core-data`        | Repositorios, retrofit, fuentes de datos  |
| `core-platform`    | Utilities, extensions, helpers comunes    |
| `session`          | Gestión de sesión y almacenamiento local  |
| `navigation`       | Configuración de rutas y navegación UI    |

---

<br><br>


## 🎨 Diseño y demostración

### 🔐 Autenticación

<p align="center">
<img width="300" height="800" alt="Screenshot_1780270033" src="https://github.com/user-attachments/assets/2ffdb61f-682f-4f60-b88a-bd0b0d9b9ff5" />
<img width="300" height="800" alt="Screenshot_1780270355" src="https://github.com/user-attachments/assets/2d2e4de7-05cb-483c-8a0e-c16ebb2d4bd2" />
<img width="300" height="800" alt="Screenshot_1780270041" src="https://github.com/user-attachments/assets/533eb21f-6e3e-4a39-8110-5f35083f08db" />
<img width="300" height="800" alt="Screenshot_1780270363" src="https://github.com/user-attachments/assets/02f850d4-3577-4c10-8cd6-e0ecc1877552" />
</p>

<br><br>

_Navigation_

<p align="center">
<img width="300" height="800" alt="Screenshot_1780270300" src="https://github.com/user-attachments/assets/7ad22388-d748-4723-816c-446ed42d1eef" />
<img width="300" height="800" alt="Screenshot_1780270350" src="https://github.com/user-attachments/assets/73de964f-76e6-4140-b486-aefd33d1ea8c" />

<img width="300" height="800" alt="Screenshot_1780270304" src="https://github.com/user-attachments/assets/37eb8a6a-f666-4860-807f-6d6fc37ad720" />
<img width="300" height="800" alt="Screenshot_1780270347" src="https://github.com/user-attachments/assets/1b320be9-7702-4f84-8aba-3fc2287fdc26" />

<img width="300" height="800" alt="Screenshot_1780270307" src="https://github.com/user-attachments/assets/90e4481c-807a-4959-be42-f3d180e5c607" />
<img width="300" height="800" alt="Screenshot_1780270344" src="https://github.com/user-attachments/assets/559e8a82-b6f8-446d-aed9-36a74dde029a" />

<img width="300" height="800" alt="Screenshot_1780270309" src="https://github.com/user-attachments/assets/0ea32d4e-bc97-4bab-9054-3c4950dc6adb" />
<img width="300" height="800" alt="Screenshot_1780270342" src="https://github.com/user-attachments/assets/9f5ae406-1312-4a61-b069-55ebe6fec103" />

<img width="300" height="800" alt="Screenshot_1780270313" src="https://github.com/user-attachments/assets/c11e7e37-f8f7-4545-826f-ab84d8bb8311" />
<img width="300" height="800" alt="Screenshot_1780270340" src="https://github.com/user-attachments/assets/09eba53e-bb22-4894-be92-21ff224c9d04" />

</p>

<br><br>

_Noticias_

_Video avance funcionalidades_

https://github.com/user-attachments/assets/4d74e088-98a0-4ba6-84f3-1825d33271a7



## Autor ✒️

* **Paul Guillen Acuña** - *Mi Repositorio* - [PaulGuillen](https://github.com/PaulGuillen?tab=repositories)
