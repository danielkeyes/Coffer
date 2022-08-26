package dev.danielkeyes.coffer

import android.content.Context
import android.util.Log
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
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

// start Pin Preferences Datastore
private const val PASSWORD_PREFERENCES_NAME = "password_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PASSWORD_PREFERENCES_NAME
)

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
                migrations = listOf(SharedPreferencesMigration(appContext, PASSWORD_PREFERENCES_NAME)),
                scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
                produceFile = { appContext.preferencesDataStoreFile(PASSWORD_PREFERENCES_NAME) }
            )
        }
    }
}
// end

interface IPasswordRepository {
    val salt: Flow<String?>
    val hash: Flow<String?>

    suspend fun storeHashedPassword(hash: String)

//    suspend fun retrieveHashedPassword(): String?

    suspend fun storePasswordSalt(salt: String)

//    suspend fun retrievePasswordSalt(): String?
}

@Singleton
class PasswordRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
): IPasswordRepository {

    override val hash = dataStore.data.map { preferences ->
        preferences[HASHED_PASSWORD_PREFERENCE_KEY] ?: null
    }

    override val salt = dataStore.data.map { preferences ->
        preferences[SALT_PREFERENCE_KEY] ?: null
    }

    override suspend fun storeHashedPassword(hash: String) {
        dataStore.edit { preferences ->
            preferences[HASHED_PASSWORD_PREFERENCE_KEY] = hash
        }
    }

//    override suspend fun retrieveHashedPassword(): String {
//        return retrieveFlowValue(hash)
//    }

    override suspend fun storePasswordSalt(salt: String) {
        dataStore.edit { preferences ->
            preferences[SALT_PREFERENCE_KEY] = salt
        }
    }

//    override suspend fun retrievePasswordSalt(): String {
//        return retrieveFlowValue(salt)
//    }

//    private suspend fun retrieveFlowValue( flow: Flow<String?>): String {
//        Log.e("dkeyes", "aaaa")
//        var returnValue = ""
//        flow.collectLatest{ hash ->
//            if( hash != null) {
//                Log.e("dkeyes", "bbbb")
//
//                returnValue = hash
//            }
//        }
//        Log.e("dkeyes", "cccc")
//
//        return returnValue
//    }

    companion object{
        val SALT_PREFERENCE_KEY = stringPreferencesKey("salt_preference")
        val HASHED_PASSWORD_PREFERENCE_KEY = stringPreferencesKey("hased_password_preference")
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class PasswordRepositoryModule {
    @Binds
    abstract fun bindPasswordRepository(
        passwordRepository: PasswordRepository
    ): IPasswordRepository
}