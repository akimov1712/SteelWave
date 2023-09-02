package ru.steelwave.steelwave

import android.app.Application
import ru.steelwave.steelwave.di.DaggerApplicationComponent

class App: Application() {

    val component by lazy{
        DaggerApplicationComponent.factory().create(this)
    }

}