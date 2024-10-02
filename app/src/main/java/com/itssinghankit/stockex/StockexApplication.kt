package com.itssinghankit.stockex

import android.app.Application
import timber.log.Timber

class StockexApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}