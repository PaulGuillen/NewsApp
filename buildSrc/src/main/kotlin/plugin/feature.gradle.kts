package plugin

import dependence.*

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("plugin.environment")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        minSdk = Config.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        dataBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }

    kotlinOptions {
        jvmTarget = Config.javaVersion.toString()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    lint {
        disable += "NullSafeMutableLiveData"
    }

}

ksp {
    arg("KOIN_DEFAULT_MODULE", "false")
}

dependencies {
    implementation(project(":core-platform"))
    implementation(project(":core-domain"))
    implementation(project(":core-data"))
    implementation(project(":navigation"))
    implementation(project(":session"))
    androidCoreImplementation()
    androidTestingImplementation()
    uiCoreLibsImplementation()
    navigationFragmentImplementation()
    retrofitImplementation()
    koinImplementation()
    composeImplementation()
    datastoreImplementation()
    firebaseImplementation()
}