package com.devpaul.infoxperu.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object UpdateChecker {

	private const val FORCE_UPDATE_URL = ""

	@SuppressLint("ObsoleteSdkInt")
    suspend fun isForceUpdateRequired(context: Context): Boolean = withContext(Dispatchers.IO) {
		if (FORCE_UPDATE_URL.isBlank()) return@withContext false
		try {
			val url = URL(FORCE_UPDATE_URL)
			val conn = url.openConnection() as HttpURLConnection
			conn.requestMethod = "GET"
			conn.connectTimeout = 3000
			conn.readTimeout = 3000
			conn.doInput = true
			conn.connect()
			if (conn.responseCode != HttpURLConnection.HTTP_OK) return@withContext false
			val text = conn.inputStream.bufferedReader().use { it.readText() }
			val json = JSONObject(text)
			val minVersion = json.optInt("minVersionCode", -1)
			if (minVersion <= 0) return@withContext false
			val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
			val currentVersion = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) pInfo.longVersionCode.toInt() else pInfo.versionCode
			return@withContext currentVersion < minVersion
		} catch (e: Exception) {
			return@withContext false
		}
	}
}


