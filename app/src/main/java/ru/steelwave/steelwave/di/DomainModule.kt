package ru.steelwave.steelwave.di

import dagger.Binds
import dagger.Module
import ru.steelwave.steelwave.data.repository.project.ProjectRepositoryImpl
import ru.steelwave.steelwave.domain.repository.project.ProjectRepository

@Module
interface DomainModule {

//    @Binds
//    fun bindIncomeRepository(impl: IncomeRepositoryImpl): IncomeRepository
//
//    @Binds
//    fun bindIncomeYearRepository(impl: IncomeYearRepositoryImpl): IncomeYearRepository
//
//    @Binds
//    fun bindLossRepository(impl: LossRepositoryImpl): LossRepository
//
//    @Binds
//    fun bindTargetRepository(impl: TargetRepositoryImpl): TargetRepository

    @Binds
    fun bindProjectRepository(impl: ProjectRepositoryImpl): ProjectRepository

//    @Binds
//    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

}