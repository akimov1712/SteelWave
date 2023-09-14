package ru.steelwave.steelwave.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.steelwave.steelwave.data.database.converter.BitmapConverter
import ru.steelwave.steelwave.data.database.converter.finance.IncomeConverter
import ru.steelwave.steelwave.data.database.converter.finance.LossConverter
import ru.steelwave.steelwave.data.database.converter.finance.TargetConverter
import ru.steelwave.steelwave.data.database.converter.project.ProjectConverter
import ru.steelwave.steelwave.data.database.converter.finance.TransactionConverter
import ru.steelwave.steelwave.data.database.converter.finance.TransactionListConverter
import ru.steelwave.steelwave.data.database.converter.finance.YearIncomeConverter
import ru.steelwave.steelwave.data.database.converter.user.UserConverter
import ru.steelwave.steelwave.data.database.dao.finance.IncomeDao
import ru.steelwave.steelwave.data.database.dao.finance.LossDao
import ru.steelwave.steelwave.data.database.dao.finance.TargetDao
import ru.steelwave.steelwave.data.database.dao.project.ProjectDao
import ru.steelwave.steelwave.data.database.dao.finance.YearIncomeDao
import ru.steelwave.steelwave.data.database.model.finance.IncomeDbModel
import ru.steelwave.steelwave.data.database.model.finance.LossDbModel
import ru.steelwave.steelwave.data.database.model.finance.TargetDbModel
import ru.steelwave.steelwave.data.database.model.finance.YearIncomeDbModel
import ru.steelwave.steelwave.data.database.model.project.ProjectDbModel
import ru.steelwave.steelwave.data.database.model.user.UserDbModel


@Database(
    entities = [
        ProjectDbModel::class,
        UserDbModel::class,
        IncomeDbModel::class,
        LossDbModel::class,
        TargetDbModel::class,
        YearIncomeDbModel::class,
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    BitmapConverter::class,
    UserConverter::class,
    ProjectConverter::class,
    IncomeConverter::class,
    LossConverter::class,
    TargetConverter::class,
    YearIncomeConverter::class,
    TransactionConverter::class,
    TransactionListConverter::class,
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun projectDao(): ProjectDao
    abstract fun incomeDao(): IncomeDao
    abstract fun yearIncomeDao(): YearIncomeDao
    abstract fun lossDao(): LossDao
    abstract fun targetDao(): TargetDao

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
            ).fallbackToDestructiveMigration().build()

    }
}