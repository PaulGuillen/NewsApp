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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.runtime.livedata)

    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.coroutines.test) // Coroutines test library
    testImplementation(libs.mockito.core) // Mockito core library
    testImplementation(libs.kotlin.test) // Kotlin test library

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)
    implementation(libs.converter.simplexml)

    // Lifecycle
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Coroutines
    implementation(libs.coroutines.android)

    //Timber
    implementation(libs.timber)

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
