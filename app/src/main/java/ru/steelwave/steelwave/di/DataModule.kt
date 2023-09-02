package ru.steelwave.steelwave.di

import android.app.Application
import dagger.Module
import dagger.Provides
import ru.steelwave.steelwave.data.database.AppDatabase
import ru.steelwave.steelwave.data.database.dao.ProjectDao

@Module
interface DataModule {

    companion object{

        @Provides
        fun provideProjectDao(application: Application): ProjectDao {
            return AppDatabase.getInstance(application).projectDao()
        }

    }

}