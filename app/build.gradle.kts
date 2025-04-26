plugins {
    id("plugin.application")
    alias(libs.plugins.dagger.hilt.android)
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

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.converter.simplexml)

    // Lifecycle
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Import the Firebase BoM
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.storage)

    //Coil
    implementation(libs.coil.compose)

    //Shimmer
    implementation(libs.shimmer.compose)

    //DataStore
    implementation(libs.datastore.preferences)
    implementation(libs.datastore.core)
}
