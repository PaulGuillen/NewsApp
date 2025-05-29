plugins {
    id("plugin.feature")
}

android {
    namespace = "com.devpaul.news"
}

dependencies {
    implementation(project(":feature:shared"))
}