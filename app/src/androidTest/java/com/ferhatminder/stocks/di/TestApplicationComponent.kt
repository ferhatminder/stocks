package com.ferhatminder.stocks.di

import androidx.compose.material.ExperimentalMaterialApi
import com.ferhatminder.stocks.MainActivity
import com.ferhatminder.stocks.utils.DispatcherProvider
import com.ferhatminder.stocks.utils.StandardDispatcherProvider
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@ExperimentalMaterialApi
@Singleton
@Component(modules = [TestAppModule::class, TestRepositoryModule::class, UseCaseModule::class])
interface TestApplicationComponent : ApplicationComponent {
    override fun inject(activity: MainActivity)
}

@Module
class TestAppModule {

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider =
        StandardDispatcherProvider()
}