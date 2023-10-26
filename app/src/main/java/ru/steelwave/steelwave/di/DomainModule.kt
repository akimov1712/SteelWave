package ru.steelwave.steelwave.di

import dagger.Binds
import dagger.Module
import ru.steelwave.steelwave.data.repository.ads.AdsRepositoryImpl
import ru.steelwave.steelwave.data.repository.finance.TargetRepositoryImpl
import ru.steelwave.steelwave.data.repository.finance.TransactionRepositoryImpl
import ru.steelwave.steelwave.data.repository.finance.YearIncomeRepositoryImpl
import ru.steelwave.steelwave.data.repository.project.ProjectRepositoryImpl
import ru.steelwave.steelwave.data.repository.traffic.TransferRepositoryImpl
import ru.steelwave.steelwave.data.repository.traffic.VisitionRepositoryImpl
import ru.steelwave.steelwave.data.repository.user.TaskRepositoryImpl
import ru.steelwave.steelwave.data.repository.user.UserRepositoryImpl
import ru.steelwave.steelwave.domain.repository.ads.AdsRepository
import ru.steelwave.steelwave.domain.repository.finance.TargetRepository
import ru.steelwave.steelwave.domain.repository.finance.TransactionRepository
import ru.steelwave.steelwave.domain.repository.finance.YearIncomeRepository
import ru.steelwave.steelwave.domain.repository.project.ProjectRepository
import ru.steelwave.steelwave.domain.repository.traffic.TransferRepository
import ru.steelwave.steelwave.domain.repository.traffic.VisitionRepository
import ru.steelwave.steelwave.domain.repository.user.TaskRepository
import ru.steelwave.steelwave.domain.repository.user.UserRepository

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

    @Binds
    fun bindTransferRepository(impl: TransferRepositoryImpl): TransferRepository

    @Binds
    fun bindVisitionRepository(impl: VisitionRepositoryImpl): VisitionRepository

    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository

    @Binds
    fun bindAdsRepository(impl: AdsRepositoryImpl): AdsRepository

}