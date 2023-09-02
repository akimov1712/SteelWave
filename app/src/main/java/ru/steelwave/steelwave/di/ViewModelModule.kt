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
    fun bindAdsViewModel(viewModel: AdsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(EmployeesViewModel::class)
    @Binds
    fun bindEmployeesViewModel(viewModel: EmployeesViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FinanceViewModel::class)
    @Binds
    fun bindFinanceViewModel(viewModel: FinanceViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ProjectViewModel::class)
    @Binds
    fun bindProjectViewModel(viewModel: ProjectViewModel): ViewModel

    @IntoMap
    @ViewModelKey(TraficViewModel::class)
    @Binds
    fun bindTraficViewModel(viewModel: TraficViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel

}