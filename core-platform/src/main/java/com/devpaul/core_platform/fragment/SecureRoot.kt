package com.devpaul.core_platform.fragment

import android.os.Build
import com.devpaul.core_platform.BuildConfig
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.Locale

fun isRootedSecondValidation(): Boolean {
    return checkRootGroup1() || checkRootGroup2()
}

fun checkRootGroup1() =  checkRootMethod0 () || checkRootMethod1() || checkRootMethod2()

fun checkRootGroup2() = checkRootMethod3() || checkRootMethod4()

fun checkRootMethod0(): Boolean {
    val isEmulator = false //isEmulator()
    val buildTags: String? = Build.TAGS

    if (buildTags != null && (buildTags.contains("test-keys") || buildTags.contains("dev-keys"))) {
        return true
    }
    var file = File("/system/app/Superuser.apk")
    if (file.exists()) {
        return true
    }
    if (isEmulator && BuildConfig.FLAVOR.equals("prod", true)) {
        return true
    }
    file = File("/system/xbin/su")
    return file.exists()
}


private fun checkRootMethod1(): Boolean {
    val buildTags = Build.TAGS
    return buildTags != null && buildTags.contains("test-keys")
}

private fun checkRootMethod2(): Boolean {
    val paths = arrayOf("/system/app/Superuser.apk",
        "/sbin/su",
        "/system/bin/su",
        "/system/xbin/su",
        "/data/local/xbin/su",
        "/data/local/bin/su",
        "/system/sd/xbin/su",
        "/system/bin/failsafe/su",
        "/data/local/su",
        "/su/bin/su",
        "/system/etc/init.d/99SuperSUDaemon",
        "/dev/com.koushikdutta.superuser.daemon/",
        "/system/xbin/daemonsu",
        "/sbin/su",
        "/system/bin/su",
        "/system/bin/failsafe/su",
        "/system/xbin/busybox",
        "/system/bin/.ext/su",
        "/system/usr/we-need-root/su",
        "/system/bin/magiskinit",
        "/system/bin/magisk",
        "/system/bin/magiskpolicy",
        "/system/bin/magiskhide"
    )
    for (path in paths) {
        if (File(path).exists()) return true
    }
    return false
}

private fun checkRootMethod3(): Boolean {
    var process: Process? = null
    return try {
        process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        reader.readLine() != null
    } catch (t: Throwable) {
        false
    } finally {
        process?.destroy()
    }
}

fun checkRootMethod4(): Boolean {
    if (isRootAvailable()) {
        var process: Process? = null
        try {
            process = Runtime.getRuntime().exec(arrayOf("su", "-c", "id"))
            val `in` = BufferedReader(InputStreamReader(process.inputStream))
            val output = `in`.readLine()
            if (output != null && output.lowercase(Locale.getDefault()).contains("uid=0")) return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            process?.destroy()
        }
    }
    return false
}

fun isRootAvailable(): Boolean {
    System.getenv("PATH")?.split(":")?.toTypedArray()?.let {
        for (pathDir in it) {
            if (File(pathDir, "su").exists()) {
                return true
            }
        }
    }
    return false
}