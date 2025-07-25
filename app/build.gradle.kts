plugins {
    id("plugin.application")
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.devpaul.infoxperu"

    defaultConfig {
        applicationId = "com.devpaul.infoxperu"
    }

}

dependencies {
    implementation(project(":navigation"))
    implementation(project(":core-platform"))
    implementation(project(":core-data"))
    implementation(project(":core-domain"))
    implementation(project(":feature:shared"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:news"))
    implementation(project(":feature:districts"))
    implementation(project(":feature:profile"))
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.activity)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.koin.navigation)
}