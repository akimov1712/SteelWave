package ru.steelwave.steelwave.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.steelwave.steelwave.presentation.MainActivity
import ru.steelwave.steelwave.presentation.main.ads.AdsFragment
import ru.steelwave.steelwave.presentation.main.ads.modals.AddPartnerModal
import ru.steelwave.steelwave.presentation.main.employees.EmployeesFragment
import ru.steelwave.steelwave.presentation.main.employees.modals.AddEmployeeModal
import ru.steelwave.steelwave.presentation.modals.ChoiceProjectModal
import ru.steelwave.steelwave.presentation.main.finance.FinanceFragment
import ru.steelwave.steelwave.presentation.main.finance.modals.AddLossModal
import ru.steelwave.steelwave.presentation.main.finance.modals.AddTargetModal
import ru.steelwave.steelwave.presentation.main.finance.modals.DeleteTargetModal
import ru.steelwave.steelwave.presentation.main.finance.modals.RefillTargetModal
import ru.steelwave.steelwave.presentation.main.project.modals.AddProjectModal
import ru.steelwave.steelwave.presentation.main.project.modals.EditProjectModal
import ru.steelwave.steelwave.presentation.main.project.ProjectFragment
import ru.steelwave.steelwave.presentation.main.project.modals.DeleteProjectModal
import ru.steelwave.steelwave.presentation.main.traffic.TrafficFragment

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
    fun inject(fragment: TrafficFragment)
    fun inject(fragment: EmployeesFragment)
    fun inject(fragment: AdsFragment)

    fun inject(dialogFragment: AddProjectModal)
    fun inject(dialogFragment: EditProjectModal)
    fun inject(dialogFragment: ChoiceProjectModal)
    fun inject(dialogFragment: AddLossModal)
    fun inject(dialogFragment: AddTargetModal)
    fun inject(dialogFragment: RefillTargetModal)
    fun inject(dialogFragment: DeleteTargetModal)
    fun inject(dialogFragment: DeleteProjectModal)
    fun inject(dialogFragment: AddEmployeeModal)
    fun inject(dialogFragment: AddPartnerModal)

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}