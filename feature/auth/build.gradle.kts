plugins {
    id("plugin.feature")
}

android {
    namespace = "com.devpaul.auth"
}

dependencies {
    implementation(project(":feature:shared"))
}