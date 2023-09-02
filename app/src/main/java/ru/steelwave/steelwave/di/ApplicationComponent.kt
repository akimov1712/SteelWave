package ru.steelwave.steelwave.di

import android.app.Application
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [

    ]
)
interface ApplicationComponent {

    fun inject(fragment: Fragment)

    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}