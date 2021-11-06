package com.ferhatminder.stocks.di

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.ferhatminder.stocks.TestApplication

class DaggerTestRunner : AndroidJUnitRunner() {
    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}


