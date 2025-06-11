plugins {
    id("plugin.feature")
}

android {
    namespace = "com.devpaul.districts"
}

dependencies {
    implementation(project(":feature:shared"))
}