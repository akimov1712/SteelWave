package ru.steelwave.steelwave.di

import android.app.Application
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component
import ru.steelwave.steelwave.presentation.MainActivity
import ru.steelwave.steelwave.presentation.main.project.AddProjectModal
import ru.steelwave.steelwave.presentation.main.project.ProjectFragment

@ApplicationScope
@Component(
    modules = [
        DomainModule::class,
        DataModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: ProjectFragment)
    fun inject(dialogFragment: DialogFragment)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}