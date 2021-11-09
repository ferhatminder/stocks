package com.ferhatminder.stocks.di

import com.ferhatminder.stocks.MainActivity
import com.ferhatminder.stocks.core.ViewModelFactory
import com.ferhatminder.stocks.utils.DispatcherProvider
import com.ferhatminder.stocks.utils.StandardDispatcherProvider
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class, UseCaseModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun viewModelsFactory(): ViewModelFactory
}

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = StandardDispatcherProvider()

}