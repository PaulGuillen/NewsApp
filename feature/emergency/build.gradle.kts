plugins {
    id("plugin.feature")
}

android {
    namespace = "com.devpaul.emergency"
}

dependencies {
    implementation(project(":feature:shared"))
}