package ru.steelwave.steelwave.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.steelwave.steelwave.presentation.MainActivity
import ru.steelwave.steelwave.presentation.main.finance.modals.ChoiceProjectModal
import ru.steelwave.steelwave.presentation.main.finance.FinanceFragment
import ru.steelwave.steelwave.presentation.main.finance.modals.AddLossModal
import ru.steelwave.steelwave.presentation.main.project.modals.AddProjectModal
import ru.steelwave.steelwave.presentation.main.project.modals.EditProjectModal
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
    fun inject(dialogFragment: AddLossModal)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}