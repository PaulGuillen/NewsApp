plugins {
    id("plugin.feature")
}

android {
    namespace = "com.devpaul.home"
}

dependencies {
    implementation(project(":feature:news"))
    implementation(project(":feature:districts"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:shared"))
}