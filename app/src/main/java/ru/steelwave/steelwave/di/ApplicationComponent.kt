package ru.steelwave.steelwave.di

import android.app.Application
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DomainModule::class,
        DataModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: Fragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}