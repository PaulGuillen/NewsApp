// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("com.google.devtools.ksp") apply false
    id("org.jetbrains.kotlin.jvm") apply false
    id("androidx.navigation.safeargs.kotlin") apply false
    id("org.jetbrains.kotlin.plugin.serialization") apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.google.services) apply false
}