plugins {
    id("plugin.feature")
}

android {
    namespace = "com.devpaul.profile"
}

dependencies {
    implementation(project(":feature:shared"))
}