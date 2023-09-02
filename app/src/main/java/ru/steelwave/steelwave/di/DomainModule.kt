package ru.steelwave.steelwave.di

import dagger.Binds
import dagger.Module
import ru.steelwave.steelwave.data.repository.finance.IncomeRepositoryImpl
import ru.steelwave.steelwave.data.repository.finance.IncomeYearRepositoryImpl
import ru.steelwave.steelwave.data.repository.finance.LossRepositoryImpl
import ru.steelwave.steelwave.data.repository.finance.TargetRepositoryImpl
import ru.steelwave.steelwave.data.repository.project.ProjectRepositoryImpl
import ru.steelwave.steelwave.data.repository.user.UserRepositoryImpl
import ru.steelwave.steelwave.domain.repository.finance.IncomeRepository
import ru.steelwave.steelwave.domain.repository.finance.IncomeYearRepository
import ru.steelwave.steelwave.domain.repository.finance.LossRepository
import ru.steelwave.steelwave.domain.repository.finance.TargetRepository
import ru.steelwave.steelwave.domain.repository.project.ProjectRepository
import ru.steelwave.steelwave.domain.repository.user.UserRepository

@Module
interface DomainModule {

    @Binds
    fun bindIncomeRepository(impl: IncomeRepositoryImpl): IncomeRepository

    @Binds
    fun bindIncomeYearRepository(impl: IncomeYearRepositoryImpl): IncomeYearRepository

    @Binds
    fun bindLossRepository(impl: LossRepositoryImpl): LossRepository

    @Binds
    fun bindTargetRepository(impl: TargetRepositoryImpl): TargetRepository

    @Binds
    fun bindProjectRepository(impl: ProjectRepositoryImpl): ProjectRepository

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

}