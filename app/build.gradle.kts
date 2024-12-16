plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.devpaul.infoxperu"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.devpaul.infoxperu"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions += "environment"

    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"

            buildConfigField("String", "BASE_URL_NEWS", "\"https://newsapi.org/\"")
            buildConfigField("String", "BASE_URL_PERU", "\"https://deperu.com/\"")
            buildConfigField("String", "BASE_URL_GOOGLE_NEWS", "\"https://news.google.com/\"")
            buildConfigField(
                "String",
                "BASE_URL_GDELT_PROJECT",
                "\"https://api.gdeltproject.org/\""
            )
            buildConfigField("String", "BASE_URL_REDDIT", "\"https://www.reddit.com/\"")

        }

        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL_NEWS", "\"https://newsapi.org/\"")
            buildConfigField("String", "BASE_URL_PERU", "\"https://deperu.com/\"")
            buildConfigField("String", "BASE_URL_GOOGLE_NEWS", "\"https://news.google.com/\"")
            buildConfigField(
                "String",
                "BASE_URL_GDELT_PROJECT",
                "\"https://api.gdeltproject.org/\""
            )
            buildConfigField("String", "BASE_URL_REDDIT", "\"https://www.reddit.com/\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=1.9.21"
        )
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
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
    testImplementation(libs.junit.junit)
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
