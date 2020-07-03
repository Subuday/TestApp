package com.muzzlyworld.testapp

import android.app.Application
import com.muzzlyworld.testapp.di.AppContainer

class App : Application() {
    val appContainer = AppContainer()
}