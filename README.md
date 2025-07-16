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

<img width="320" height="1400" alt="Screenshot_1752696025" src="https://github.com/user-attachments/assets/0a337098-53f5-4951-8eab-41d616db0f6e" />
<img width="320" height="1400" alt="Screenshot_1752696028" src="https://github.com/user-attachments/assets/07ea8180-d20b-491b-bbf1-2b523688fa01" />
<img width="320" height="1400" alt="Screenshot_1752695926" src="https://github.com/user-attachments/assets/f0df4c27-ca52-481b-a7fc-ab25c82fd57c" />

<br><br>

_Inicio_

<img width="320" height="1400" alt="Screenshot_1752696124" src="https://github.com/user-attachments/assets/71fa303c-076e-4a75-8ab7-26e35e59e497" />
<img width="320" height="1400" alt="Screenshot_1752696126" src="https://github.com/user-attachments/assets/284dde6b-52f7-47f9-9292-e2345b047154" />
<img width="320" height="1400" alt="Screenshot_1752696208" src="https://github.com/user-attachments/assets/8d0c1cdf-4aab-49ff-a012-44e44d660318" />
<img width="320" height="1400" alt="Screenshot_1752696202" src="https://github.com/user-attachments/assets/1d151d75-8a87-49e2-9348-038cadcd8287" />
<img width="320" height="1400" alt="Screenshot_1752696133" src="https://github.com/user-attachments/assets/2b09863a-ca0c-47b5-9e7d-fd4f2bce9b02" />

<br><br>

_Noticias_

<img width="320" height="1400" alt="Screenshot_1752696382" src="https://github.com/user-attachments/assets/ea523e1d-20b4-4c17-932b-4f7c19db2830" />
<img width="320" height="1400" alt="Screenshot_1752696388" src="https://github.com/user-attachments/assets/eaaa4a3d-2d2e-4e6e-9ac7-5ed30a489c11" />
<img width="320" height="1400" alt="Screenshot_1752696390" src="https://github.com/user-attachments/assets/4e77b8f7-ab97-48e9-808b-3030add2eb15" />
<img width="320" height="1400" alt="Screenshot_1752696401" src="https://github.com/user-attachments/assets/c38c1133-6080-494f-b194-07a2512617f1" />
<img width="320" height="1400" alt="Screenshot_1752696403" src="https://github.com/user-attachments/assets/952f315d-b552-4f43-a614-ba56d94677a8" />
<img width="320" height="1400" alt="Screenshot_1752696414" src="https://github.com/user-attachments/assets/dcc29d73-a3ad-46ab-bc42-38d6c3385605" />
<img width="320" height="1400" alt="Screenshot_1752696419" src="https://github.com/user-attachments/assets/c6d56aee-eaeb-4178-bfd4-557a1ba64050" />
<img width="320" height="1400" alt="Screenshot_1752696427" src="https://github.com/user-attachments/assets/e39ac56a-6549-4a8b-bd85-f18892d7647b" />
<img width="320" height="1400" alt="Screenshot_1752696434" src="https://github.com/user-attachments/assets/3a11aea8-cc22-4fb8-b1d9-061eb716daf9" />
<img width="320" height="1400" alt="Screenshot_1752696437" src="https://github.com/user-attachments/assets/5ae937ab-cb46-4996-b3ad-31873e439f84" />




_Video avance funcionalidades_

## Autor ✒️

* **Paul Guillen Acuña** - *Mi Repositorio* - [PaulGuillen](https://github.com/PaulGuillen?tab=repositories)
