import dependence.*

plugins {
    id("plugin.android.lib")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.devpaul.core_data"
}

dependencies {
    implementation(project(":core-domain"))
    implementation(project(":session"))
    implementation(libs.kotlinx.serialization.json)
    uiCoreLibsImplementation()
    retrofitImplementation()
    koinImplementation()
    datastoreImplementation()
}