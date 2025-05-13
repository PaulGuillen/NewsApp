import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

object DataStoreSingleton {

    private var dataStoreInstance: DataStore<Preferences>? = null
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    fun getInstance(context: Context): DataStore<Preferences> {
        if (dataStoreInstance == null) {
            synchronized(this) {
                if (dataStoreInstance == null) {
                    dataStoreInstance = context.dataStore
                }
            }
        }
        return dataStoreInstance!!
    }
}