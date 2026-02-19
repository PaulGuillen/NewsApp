package dependence

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

fun Project.getLibs(): VersionCatalog =
    extensions.getByType<VersionCatalogsExtension>().named("libs")

operator fun VersionCatalog.get(alias: String): Provider<MinimalExternalModuleDependency> {
    return findLibrary(alias).get()
}

fun Project.composeImplementation() {
    val libs: VersionCatalog = getLibs()
    dependencies {
        "implementation"(libs["androidx-activity-compose"])
        "implementation"(platform(libs["androidx-compose-bom"]))
        "implementation"(libs["androidx-ui"])
        "implementation"(libs["androidx-ui-graphics"])
        "implementation"(libs["androidx-ui-tooling"])
        "implementation"(libs["androidx-ui-tooling-preview"])
        "implementation"(libs["androidx-material3"])
        "implementation"(libs["androidx-runtime-livedata"])
        "implementation"(libs["navigation-compose"])
        "implementation"(libs["koin-androidx-compose"])
        "implementation"(libs["androidx-material3"])
        "implementation"(libs["datetime"])
        "androidTestImplementation"(platform(libs["androidx-compose-bom"]))
        "androidTestImplementation"(libs["androidx-ui-test-junit4"])
        "debugImplementation"(libs["androidx-ui-tooling"])
        "debugImplementation"(libs["androidx-ui-test-manifest"])
    }
}

fun Project.datastoreImplementation() {
    val libs: VersionCatalog = getLibs()
    dependencies {
        "implementation"(libs["datastore-preferences"])
        "implementation"(libs["datastore-core"])
    }
}

fun Project.koinImplementation() {
    val libs: VersionCatalog = getLibs()
    dependencies {
        "implementation"(libs["koin"])
        "implementation"(libs["koin-annotations"])
        "ksp"(libs["koin-compiler"])
    }
}

fun Project.hiltImplementation() {
    val libs: VersionCatalog = getLibs()
    dependencies {
        "implementation"(libs["hilt-android"])
        "implementation"(libs["hilt-navigation-compose"])
        "ksp"(libs["hilt-android-compiler"])
    }
}

fun Project.uiCoreLibsImplementation() {
    val libs: VersionCatalog = getLibs()
    dependencies {
        "implementation"(libs["material"])
        "implementation"(libs["glide"])
        "implementation"(libs["androidx-appcompat"])
        "implementation"(libs["androidx-fragment"])
        "implementation"(libs["androidx-activity"])
    }
}

fun Project.androidCoreImplementation() {
    val libs: VersionCatalog = getLibs()
    dependencies {
        "implementation"(libs["jwtdecode"])
        "implementation"(libs["timber"])
        "implementation"(libs["androidx-core-ktx"])
        "implementation"(libs["androidx-lifecycle-runtime-ktx"])
        "implementation"(libs["coroutines-android"])
        "implementation"(libs["lifecycle-livedata-ktx"])
        "implementation"(libs["lifecycle-viewmodel-ktx"])
        "implementation"(libs["coil-compose"])
        "implementation"(libs["shimmer-compose"])
        "implementation"(libs["converter-simplexml"])
    }
}

fun Project.firebaseImplementation() {
    val libs: VersionCatalog = getLibs()
    dependencies {
        "implementation"(platform(libs["firebase-bom"]))
        "implementation"(libs["firebase-analytics"])
        "implementation"(libs["firebase-auth"])
        "implementation"(libs["firebase-firestore"])
        "implementation"(libs["firebase-messaging"])
        "implementation"(libs["firebase-storage"])
    }
}

fun Project.navigationFragmentImplementation() {
    val libs: VersionCatalog = getLibs()
    dependencies {
        "implementation"(libs["navigation-fragment"])
        "implementation"(libs["navigation-ui"])
        "implementation"(libs["kotlinx-serialization-json"])
    }
}

fun Project.androidTestingImplementation() {
    val libs: VersionCatalog = getLibs()
    dependencies {
        "testImplementation"(libs["junit"])
        "androidTestImplementation"(libs["androidx-junit"])
        "androidTestImplementation"(libs["androidx-espresso-core"])
        "testImplementation"(libs["mockito-core"])
        "testImplementation"(libs["mockito-inline"])
    }
}

fun Project.retrofitImplementation() {
    val libs: VersionCatalog = getLibs()
    dependencies {
        "implementation"(libs["retrofit"])
        "implementation"(libs["retrofit-gson-converter"])
        "implementation"(libs["okhttp3-loggin-interceptor"])
    }
}

fun Project.chuckerImplementation() {
    val libs: VersionCatalog = getLibs()

    dependencies {
        "debugImplementation"(dependency = libs["chucker"])
        "releaseImplementation"(dependency = libs["chucker-no-op"])
    }
}