package dev.danielkeyes.coffer

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

// start Pin Preferences Datastore
private const val PIN_PREFERENCES_NAME = "pin_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PIN_PREFERENCES_NAME
)

data class PinPreferences(val pin: String)

@Module
@InstallIn(SingletonComponent::class)
abstract class PinPreferencesModule {

    companion object {
        @Singleton
        @Provides
        fun providePinPreferencesDataStore(
            @ApplicationContext appContext: Context
        ): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }),
                migrations = listOf(SharedPreferencesMigration(appContext, PIN_PREFERENCES_NAME)),
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                produceFile = { appContext.preferencesDataStoreFile(PIN_PREFERENCES_NAME) }
            )
        }
    }
}
// end

interface IPinRepository {
    val pin: Flow<String?>

    suspend fun updatePin(pin: String)
}

class PinRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
): IPinRepository {

    override val pin = dataStore.data.map { preferences ->
        preferences[PIN_PREFERENCE_KEY] ?: null
    }

    override suspend fun updatePin(pin: String) {
        dataStore.edit { preferences ->
            preferences[PIN_PREFERENCE_KEY] = pin
        }
    }

    companion object{
        val PIN_PREFERENCE_KEY = stringPreferencesKey("pin_preference")
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class AnalyticsModule {
    @Binds
    abstract fun bindPinRepository(
        pinRepository: PinRepository
    ): IPinRepository
}