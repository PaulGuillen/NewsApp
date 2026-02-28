plugins {
    id("plugin.feature")
}

android {
    namespace = "com.devpaul.mylist"
}

dependencies {
    implementation(project(":feature:shared"))
}