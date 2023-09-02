package ru.steelwave.steelwave.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.steelwave.steelwave.data.database.converter.BitmapConverter
import ru.steelwave.steelwave.data.database.converter.ProjectConverter
import ru.steelwave.steelwave.data.database.converter.UserConverter
import ru.steelwave.steelwave.data.database.dao.ProjectDao
import ru.steelwave.steelwave.data.database.model.ProjectDbModel
import ru.steelwave.steelwave.data.database.model.UserDbModel


@Database(entities = [ProjectDbModel::class, UserDbModel::class], version = 1, exportSchema = false)
@TypeConverters(BitmapConverter::class, UserConverter::class, ProjectConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun projectDao(): ProjectDao

    companion object{
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "test.db"

        fun getInstance(application: Application) = INSTANCE ?: synchronized(this){
            INSTANCE ?: buildDatabase(application).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).build()

    }
}