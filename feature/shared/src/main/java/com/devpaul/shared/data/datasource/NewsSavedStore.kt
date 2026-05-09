package com.devpaul.shared.data.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private const val KEY_SAVED_NEWS = "saved_news_json_array"

private val Context.dataStore by preferencesDataStore(name = "mylist_datastore")

object NewsSavedStore {
    private val SAVED_KEY = stringPreferencesKey(KEY_SAVED_NEWS)

    fun getSavedNewsFlow(context: Context): Flow<List<SavedNews>> {
        return context.dataStore.data.map { prefs ->
            val raw = prefs[SAVED_KEY] ?: "[]"
            parseSavedNewsList(raw)
        }
    }

    suspend fun saveArticle(context: Context, article: SavedNews) {
        context.dataStore.edit { prefs ->
            val current = prefs[SAVED_KEY] ?: "[]"
            val arr = JSONArray(current)
            // Keep the most recently saved article at the top of the list.
            val reordered = JSONArray()
            reordered.put(articleToJson(article))
            for (i in 0 until arr.length()) {
                val obj = arr.optJSONObject(i) ?: continue
                if (obj.optString("id") != article.id) reordered.put(obj)
            }
            prefs[SAVED_KEY] = reordered.toString()
        }
    }

    suspend fun removeArticle(context: Context, id: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[SAVED_KEY] ?: "[]"
            val arr = JSONArray(current)
            val filtered = JSONArray()
            for (i in 0 until arr.length()) {
                val obj = arr.optJSONObject(i) ?: continue
                if (obj.optString("id") != id) filtered.put(obj)
            }
            prefs[SAVED_KEY] = filtered.toString()
        }
    }

    suspend fun toggleRead(context: Context, id: String, read: Boolean) {
        context.dataStore.edit { prefs ->
            val current = prefs[SAVED_KEY] ?: "[]"
            val arr = JSONArray(current)
            val result = JSONArray()
            for (i in 0 until arr.length()) {
                val obj = arr.optJSONObject(i) ?: continue
                if (obj.optString("id") == id) {
                    obj.put("isRead", read)
                }
                result.put(obj)
            }
            prefs[SAVED_KEY] = result.toString()
        }
    }

    suspend fun toggleSave(context: Context, article: SavedNews) {
        // if exists -> remove, else -> add
        context.dataStore.edit { prefs ->
            val current = prefs[SAVED_KEY] ?: "[]"
            val arr = JSONArray(current)
            var existsIndex = -1
            for (i in 0 until arr.length()) {
                val obj = arr.optJSONObject(i) ?: continue
                if (obj.optString("id") == article.id) {
                    existsIndex = i; break
                }
            }
            val result = JSONArray()
            for (i in 0 until arr.length()) {
                if (i == existsIndex) continue
                val obj = arr.optJSONObject(i) ?: continue
                result.put(obj)
            }
            if (existsIndex == -1) {
                val reordered = JSONArray()
                reordered.put(articleToJson(article))
                for (i in 0 until result.length()) {
                    reordered.put(result.opt(i))
                }
                prefs[SAVED_KEY] = reordered.toString()
            } else {
                prefs[SAVED_KEY] = result.toString()
            }
        }
    }

    // --- helpers ---
    private fun parseSavedNewsList(raw: String): List<SavedNews> {
        return try {
            val arr = JSONArray(raw)
            val list = mutableListOf<SavedNews>()
            for (i in 0 until arr.length()) {
                val obj = arr.optJSONObject(i) ?: continue
                list.add(jsonToArticle(obj))
            }
            list
        } catch (t: Throwable) {
            emptyList()
        }
    }

    private fun jsonToArticle(obj: JSONObject): SavedNews {
        return SavedNews(
            id = obj.optString("id"),
            title = obj.optString("title"),
            country = if (obj.has("country")) obj.optString("country") else null,
            source = if (obj.has("source")) obj.optString("source") else null,
            category = if (obj.has("category")) obj.optString("category") else null,
            time = if (obj.has("time")) obj.optString("time") else null,
            url = if (obj.has("url")) obj.optString("url") else null,
            isRead = obj.optBoolean("isRead", false),
            savedAtMillis = obj.optLong("savedAtMillis", 0L)
        )
    }

    private fun articleToJson(a: SavedNews): JSONObject {
        return JSONObject().apply {
            put("id", a.id)
            put("title", a.title)
            put("country", a.country ?: "")
            put("source", a.source ?: "")
            put("category", a.category ?: "")
            put("time", a.time ?: "")
            put("url", a.url ?: "")
            put("isRead", a.isRead)
            put("savedAtMillis", a.savedAtMillis)
        }
    }
}