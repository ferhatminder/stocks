package com.ferhatminder.stocks

import android.app.Application
import com.ferhatminder.stocks.di.ApplicationComponent
import com.ferhatminder.stocks.di.DaggerApplicationComponent

open class StocksApplication(
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
) : Application()