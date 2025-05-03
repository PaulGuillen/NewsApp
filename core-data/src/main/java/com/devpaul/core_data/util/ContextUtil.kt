package com.devpaul.core_data.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

fun Context.connectivityManager(): Lazy<ConnectivityManager> {
    return lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}

@SuppressLint("MissingPermission", "ObsoleteSdkInt")
@Suppress("DEPRECATION")
fun ConnectivityManager.isNetworkAvailable(): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network: Network = activeNetwork ?: return false
        val activeNetwork: NetworkCapabilities = getNetworkCapabilities(network)
            ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    } else {
        val networkInfo: NetworkInfo = activeNetworkInfo ?: return false
        return networkInfo.isConnected
    }
}