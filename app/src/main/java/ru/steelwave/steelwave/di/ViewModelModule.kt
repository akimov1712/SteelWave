package ru.steelwave.steelwave.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.steelwave.steelwave.presentation.auth.AuthViewModel
import ru.steelwave.steelwave.presentation.main.ads.AdsViewModel
import ru.steelwave.steelwave.presentation.main.employees.EmployeesViewModel
import ru.steelwave.steelwave.presentation.main.finance.FinanceViewModel
import ru.steelwave.steelwave.presentation.main.project.ProjectViewModel
import ru.steelwave.steelwave.presentation.main.trafic.TraficViewModel


@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(AdsViewModel::class)
    @Binds
    fun bindAdsViewModel(impl: AdsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(EmployeesViewModel::class)
    @Binds
    fun bindEmployeesViewModel(impl: EmployeesViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FinanceViewModel::class)
    @Binds
    fun bindFinanceViewModel(impl: FinanceViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ProjectViewModel::class)
    @Binds
    fun bindProjectViewModel(impl: ProjectViewModel): ViewModel

    @IntoMap
    @ViewModelKey(TraficViewModel::class)
    @Binds
    fun bindTraficViewModel(impl: TraficViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindAuthViewModel(impl: AuthViewModel): ViewModel

}