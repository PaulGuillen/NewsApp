# ==========================
# 📦 PROYECTO BASE
# ==========================
-keep class com.devpaul.** { *; }
-keepclassmembers class com.devpaul.** { *; }
-dontwarn com.devpaul.**

# ==========================
# 🧬 GSON
# ==========================
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# ==========================
# 🧪 Retrofit / OkHttp
# ==========================
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-keepattributes RuntimeVisibleAnnotations

# ==========================
# 💉 Koin (inyección)
# ==========================
-keep class org.koin.** { *; }
-dontwarn org.koin.**
-keepclassmembers class * {
    public <init>(...);  # necesario para instancias inyectadas
}

# ==========================
# 📦 Firebase
# ==========================
-dontwarn com.google.firebase.**
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-keepattributes *Annotation*

# ==========================
# 🖼️ Coil
# ==========================
-dontwarn coil.**
-keep class coil.** { *; }

# ==========================
# 🌊 Glide (por si acaso)
# ==========================
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.** { *; }
-dontwarn com.bumptech.glide.**

# ==========================
# 🏠 Room (si aplica)
# ==========================
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# ==========================
# 🧩 Jetpack Compose
# ==========================
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# ==========================
# ⚙️ ViewModels, UseCases, UiIntent
# ==========================
-keep class **ViewModel { *; }
-keep class **UseCase { *; }
-keep class **UiIntent { *; }

# ==========================
# 🛠 Kotlin, Coroutines, Serialization
# ==========================
-dontwarn kotlin.**
-dontwarn kotlinx.coroutines.**
-dontwarn kotlinx.serialization.**

# ==========================
# 📈 Soporte para reflexión y anotaciones
# ==========================
-keepclassmembers class * {
    @kotlin.Metadata *;
    @kotlinx.serialization.* <fields>;
    @com.google.gson.annotations.SerializedName <fields>;
    public <init>(...);
}

# ==========================
# 📉 Optimización general
# ==========================
-dontwarn javax.annotation.**
-dontwarn org.xmlpull.v1.XmlPullParser
-dontnote **

# Evita eliminar métodos anotados con @Keep
-keepclassmembers class * {
    @androidx.annotation.Keep *;
}

# Evita eliminar ViewModels con Koin o Hilt
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# Evita eliminar clases que usan reflexión con anotaciones
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# Protege funciones lambda (útil para Compose y Coroutines)
-dontwarn kotlin.Metadata
-keepclassmembers class ** {
    *** lambda*(...);
}

# Evita problemas con deserialización
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}


# -------------------------------
# KOIN & KSP
# -------------------------------
# Protege todas las clases de Koin
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# Protege módulos y todas las clases que definen inyecciones
-keep class * extends org.koin.core.module.Module { *; }

# Conserva los constructores públicos (Koin lo necesita para instanciar con reflexión)
-keepclassmembers class * {
    public <init>(...);
}

# Protege los ViewModels
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# Protege todas las clases de tu dominio/inyección
-keep class com.devpaul.** { *; }
-dontwarn com.devpaul.**

# -------------------------------
# PROTEGE LAS ANOTACIONES (si KSP las usa)
# -------------------------------
-keepattributes *Annotation*
-keepclasseswithmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Evita que ProGuard remueva los campos de BuildConfig
-keep class **.BuildConfig {
    public static final <fields>;
}
