import dependence.*

plugins {
    id("plugin.android.lib")
    id("androidx.navigation.safeargs.kotlin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.devpaul.navigation"
}

dependencies {
    implementation(project(":session"))
    implementation(project(":core-data"))
    implementation(project(":core-domain"))
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.activity)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.koin.navigation)
    implementation(libs.kotlinx.serialization.json)
    koinImplementation()
    uiCoreLibsImplementation()
    retrofitImplementation()
}