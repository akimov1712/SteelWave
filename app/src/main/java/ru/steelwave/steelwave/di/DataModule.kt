package ru.steelwave.steelwave.di

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.steelwave.steelwave.data.database.AppDatabase
import ru.steelwave.steelwave.data.database.dao.finance.IncomeDao
import ru.steelwave.steelwave.data.database.dao.finance.LossDao
import ru.steelwave.steelwave.data.database.dao.finance.TargetDao
import ru.steelwave.steelwave.data.database.dao.finance.YearIncomeDao
import ru.steelwave.steelwave.data.database.dao.project.ProjectDao

@Module
interface DataModule {

    companion object{

        @Provides
        fun provideProjectDao(application: Application): ProjectDao {
            return AppDatabase.getInstance(application).projectDao()
        }

        @Provides
        fun provideIncomeDao(application: Application): IncomeDao {
            return AppDatabase.getInstance(application).incomeDao()
        }
        @Provides
        fun provideLossDao(application: Application): LossDao {
            return AppDatabase.getInstance(application).lossDao()
        }

        @Provides
        fun provideTargetDao(application: Application): TargetDao {
            return AppDatabase.getInstance(application).targetDao()
        }

        @Provides
        fun provideYearIncomeDao(application: Application): YearIncomeDao {
            return AppDatabase.getInstance(application).yearIncomeDao()
        }

    }

}