pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven("https://jitpack.io")
        maven { setUrl("https://pkgs.dev.azure.com/MicrosoftDeviceSDK/DuoSDK-Public/_packaging/Duo-SDK-Feed/maven/v1") }
    }
}

gradle.startParameter.excludedTaskNames.addAll(listOf(":buildSrc:testClasses"))

rootProject.name = "InfoXPeru"
include(":app")
include(":core-data")
include(":core-domain")
include(":core-platform")
include(":session")
include(":navigation")
include(":feature:auth")
include(":feature:home")
include(":feature:news")
include(":feature:shared")
include(":feature:districts")
include(":feature:profile")
