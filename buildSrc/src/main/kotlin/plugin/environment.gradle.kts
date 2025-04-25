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

            create("cert") {
                dimension = dimensionName
                versionNameSuffix = "-cert"
                versionName = "1.0.0"
                versionCode = 1
                buildConfigField("String", "VERSION_NAME", "\"$versionName\"")
                buildConfigField("String", "ENVIRONMENT", "\"cert\"")
                buildConfigField("int", "VERSION_CODE", "$versionCode")
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

            create("dev") {
                dimension = dimensionName
                versionNameSuffix = "-dev"
                versionName = "1.0.0"
                versionCode = 1
                buildConfigField("String", "VERSION_NAME", "\"$versionName\"")
                buildConfigField("String", "ENVIRONMENT", "\"dev\"")
                buildConfigField("int", "VERSION_CODE", "$versionCode")
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

    }
}
