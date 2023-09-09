package ru.steelwave.steelwave.di

import android.app.Application
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Component
import ru.steelwave.steelwave.presentation.MainActivity
import ru.steelwave.steelwave.presentation.main.finance.ChoiceProjectModal
import ru.steelwave.steelwave.presentation.main.finance.FinanceFragment
import ru.steelwave.steelwave.presentation.main.project.AddProjectModal
import ru.steelwave.steelwave.presentation.main.project.EditProjectModal
import ru.steelwave.steelwave.presentation.main.project.ProjectFragment

@ApplicationScope
@Component(
    modules = [
        DomainModule::class,
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ProjectFragment)
    fun inject(fragment: FinanceFragment)

    fun inject(dialogFragment: AddProjectModal)
    fun inject(dialogFragment: EditProjectModal)
    fun inject(dialogFragment: ChoiceProjectModal)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}