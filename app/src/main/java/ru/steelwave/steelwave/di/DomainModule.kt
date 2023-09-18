package ru.steelwave.steelwave.di

import dagger.Binds
import dagger.Module
import ru.steelwave.steelwave.data.repository.finance.TargetRepositoryImpl
import ru.steelwave.steelwave.data.repository.finance.TransactionRepositoryImpl
import ru.steelwave.steelwave.data.repository.finance.YearIncomeRepositoryImpl
import ru.steelwave.steelwave.data.repository.project.ProjectRepositoryImpl
import ru.steelwave.steelwave.domain.repository.finance.TargetRepository
import ru.steelwave.steelwave.domain.repository.finance.TransactionRepository
import ru.steelwave.steelwave.domain.repository.finance.YearIncomeRepository
import ru.steelwave.steelwave.domain.repository.project.ProjectRepository

@Module
interface DomainModule {

    @Binds
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @Binds
    fun bindIncomeYearRepository(impl: YearIncomeRepositoryImpl): YearIncomeRepository

    @Binds
    fun bindTargetRepository(impl: TargetRepositoryImpl): TargetRepository

    @Binds
    fun bindProjectRepository(impl: ProjectRepositoryImpl): ProjectRepository


//    @Binds
//    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

}