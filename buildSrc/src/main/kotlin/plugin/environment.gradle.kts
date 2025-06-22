package plugin

import com.android.build.gradle.BaseExtension

val dimensionName = "environment"

project.extensions.findByType(BaseExtension::class.java)?.let { android ->
    android.apply {
        flavorDimensions(dimensionName)

        productFlavors {
            create("prod") {
                dimension = dimensionName
                versionNameSuffix = "-prod"
                versionName = "1.0.0"
                versionCode = 2
                buildConfigField("String", "VERSION_NAME", "\"$versionName\"")
                buildConfigField("String", "ENVIRONMENT", "\"prod\"")
                buildConfigField("int", "VERSION_CODE", "$versionCode")
                buildConfigField("String", "BASE_URL", "\"https://infoperu-be.onrender.com/\"")
            }

            create("dev") {
                dimension = dimensionName
                versionNameSuffix = "-dev"
                versionName = "1.0.0"
                versionCode = 1
                buildConfigField("String", "VERSION_NAME", "\"$versionName\"")
                buildConfigField("String", "ENVIRONMENT", "\"dev\"")
                buildConfigField("int", "VERSION_CODE", "$versionCode")
                buildConfigField("String", "BASE_URL", "\"http://192.168.100.137:3000/\"")
            }
        }

    }
}