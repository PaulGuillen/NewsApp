# ==========================
# 📦 PROYECTO BASE
# ==========================
-keep class com.devpaul.** { *; }
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
-keepclassmembers,allowobfuscation class * {
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
# 🧠 Hilt (Dagger Hilt)
# ==========================
-keep class dagger.** { *; }
-dontwarn dagger.**
-keep class javax.inject.** { *; }
-dontwarn javax.inject.**
-keep class * {
    @dagger.* <methods>;
}

# ==========================
# 🧪 Koin
# ==========================
-keep class org.koin.** { *; }
-dontwarn org.koin.**

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
# 🏠 Room (por si se usa en algún módulo)
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
# 🛠 Kotlin, Coroutines y Serialization
# ==========================
-dontwarn kotlin.**
-dontwarn kotlinx.coroutines.**
-dontwarn kotlinx.serialization.**

# ==========================
# 📉 Optimización general
# ==========================
-dontwarn javax.annotation.**
-dontnote **

-dontwarn org.xmlpull.v1.XmlPullParser