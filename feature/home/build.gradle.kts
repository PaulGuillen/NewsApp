plugins {
    id("plugin.feature")
}

android {
    namespace = "com.devpaul.home"
}

dependencies {
    implementation(project(":feature:shared"))
}