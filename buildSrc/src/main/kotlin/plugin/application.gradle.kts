package plugin

import Config
import dependence.androidCoreImplementation
import dependence.androidTestingImplementation
import dependence.composeImplementation
import dependence.koinImplementation
import dependence.uiCoreLibsImplementation

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("plugin.environment")
}

android {
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        minSdk = Config.MIN_SDK
        targetSdk = Config.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        dataBinding = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ksp {
    arg("KOIN_DEFAULT_MODULE", "false")
}

dependencies {
    androidCoreImplementation()
    uiCoreLibsImplementation()
    koinImplementation()
    androidTestingImplementation()
    composeImplementation()
}