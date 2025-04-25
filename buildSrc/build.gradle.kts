plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven { setUrl("https://pkgs.dev.azure.com/MicrosoftDeviceSDK/DuoSDK-Public/_packaging/Duo-SDK-Feed/maven/v1") }
    maven { setUrl("https://plugins.gradle.org/m2/") }
}

dependencies {
    implementation(libs.android.application.gradle)
    implementation(libs.jetbrains.kotlin.android.gradle)
    implementation(libs.google.ksp.gradle)
    implementation(libs.jetbrains.kotlin.gradle)
    implementation(libs.navigation.safe.args.gradle.plugin)
    implementation(libs.kotlinx.serialization.gradle.plugin)
}