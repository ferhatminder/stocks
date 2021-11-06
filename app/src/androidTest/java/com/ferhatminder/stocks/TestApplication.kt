package com.ferhatminder.stocks

import com.ferhatminder.stocks.di.DaggerTestApplicationComponent


class TestApplication : StocksApplication(
    appComponent = DaggerTestApplicationComponent.create()
)