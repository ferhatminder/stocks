package com.ferhatminder.stocks.di

import com.ferhatminder.stocks.MainActivity
import com.ferhatminder.stocks.core.DispatcherProvider
import com.ferhatminder.stocks.core.StandardDispatcherProvider
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class, UseCaseModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

}

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = StandardDispatcherProvider()
}