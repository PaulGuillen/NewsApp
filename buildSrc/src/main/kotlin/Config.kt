import org.gradle.api.JavaVersion

object Config {
    const val COMPILE_SDK: Int = 35
    const val TARGET_SDK: Int = 35
    const val MIN_SDK: Int = 28
    val javaVersion: JavaVersion = JavaVersion.VERSION_21
}