package dev.danielkeyes.coffer

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

interface IPinRepository {
    fun retrievePin(): Int

    fun setPin(): Boolean
}

class PinRepository @Inject constructor(): IPinRepository {
    override fun retrievePin(): Int {
        /* TODO using datastore*/
        return 1112
    }

    override fun setPin(): Boolean {
        /* store pin in datastore */
        return true
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