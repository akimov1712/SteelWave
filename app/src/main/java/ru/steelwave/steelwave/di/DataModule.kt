package ru.steelwave.steelwave.di

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.steelwave.steelwave.data.database.AppDatabase
import ru.steelwave.steelwave.data.database.dao.finance.TargetDao
import ru.steelwave.steelwave.data.database.dao.finance.TransactionDao
import ru.steelwave.steelwave.data.database.dao.finance.YearIncomeDao
import ru.steelwave.steelwave.data.database.dao.project.ProjectDao
import ru.steelwave.steelwave.data.database.dao.traffic.TransferDao
import ru.steelwave.steelwave.data.database.dao.traffic.VisitionDao
import ru.steelwave.steelwave.data.database.dao.user.UserDao

@Module
interface DataModule {

    companion object{

        @Provides
        fun provideProjectDao(application: Application): ProjectDao {
            return AppDatabase.getInstance(application).projectDao()
        }

        @Provides
        fun provideIncomeDao(application: Application): TransactionDao {
            return AppDatabase.getInstance(application).transactionDao()
        }

        @Provides
        fun provideTargetDao(application: Application): TargetDao {
            return AppDatabase.getInstance(application).targetDao()
        }

        @Provides
        fun provideYearIncomeDao(application: Application): YearIncomeDao {
            return AppDatabase.getInstance(application).yearIncomeDao()
        }

        @Provides
        fun provideTransferDao(application: Application): TransferDao {
            return AppDatabase.getInstance(application).transferDao()
        }

        @Provides
        fun provideVisitionDao(application: Application): VisitionDao {
            return AppDatabase.getInstance(application).visitionDao()
        }

        @Provides
        fun provideUserDao(application: Application): UserDao {
            return AppDatabase.getInstance(application).userDao()
        }

    }

}