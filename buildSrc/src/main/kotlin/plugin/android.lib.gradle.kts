package plugin

import Config
import dependence.androidCoreImplementation
import dependence.androidTestingImplementation
import dependence.composeImplementation

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("plugin.environment")
}

android {
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        minSdk = Config.MIN_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

ksp {
    arg("KOIN_DEFAULT_MODULE", "false")
}

dependencies {
    androidCoreImplementation()
    androidTestingImplementation()
    composeImplementation()
}