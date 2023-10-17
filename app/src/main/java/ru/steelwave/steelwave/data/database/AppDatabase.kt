package ru.steelwave.steelwave.data.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.steelwave.steelwave.data.database.converter.BitmapConverter
import ru.steelwave.steelwave.data.database.converter.finance.TargetConverter
import ru.steelwave.steelwave.data.database.converter.project.ProjectConverter
import ru.steelwave.steelwave.data.database.converter.finance.YearIncomeConverter
import ru.steelwave.steelwave.data.database.converter.traffic.TransferConverter
import ru.steelwave.steelwave.data.database.converter.traffic.VisitionConverter
import ru.steelwave.steelwave.data.database.converter.user.UserConverter
import ru.steelwave.steelwave.data.database.dao.ads.DetailsPartnerDao
import ru.steelwave.steelwave.data.database.dao.ads.PartnerDao
import ru.steelwave.steelwave.data.database.dao.finance.TargetDao
import ru.steelwave.steelwave.data.database.dao.finance.TransactionDao
import ru.steelwave.steelwave.data.database.dao.project.ProjectDao
import ru.steelwave.steelwave.data.database.dao.finance.YearIncomeDao
import ru.steelwave.steelwave.data.database.dao.traffic.TransferDao
import ru.steelwave.steelwave.data.database.dao.traffic.VisitionDao
import ru.steelwave.steelwave.data.database.dao.user.UserDao
import ru.steelwave.steelwave.data.database.model.ads.DetailsPartnerDbModel
import ru.steelwave.steelwave.data.database.model.finance.TargetDbModel
import ru.steelwave.steelwave.data.database.model.finance.TransactionDbModel
import ru.steelwave.steelwave.data.database.model.finance.YearIncomeDbModel
import ru.steelwave.steelwave.data.database.model.project.ProjectDbModel
import ru.steelwave.steelwave.data.database.model.traffic.TransferDbModel
import ru.steelwave.steelwave.data.database.model.traffic.VisitionDbModel
import ru.steelwave.steelwave.data.database.model.user.UserDbModel
import ru.steelwave.steelwave.data.database.model.ads.PartnerDbModel


@Database(
    entities = [
        ProjectDbModel::class,
        UserDbModel::class,
        TargetDbModel::class,
        YearIncomeDbModel::class,
        TransactionDbModel::class,
        TransferDbModel::class,
        VisitionDbModel::class,
        PartnerDbModel::class,
        DetailsPartnerDbModel::class
    ],
    version = 22,
    exportSchema = false
)
@TypeConverters(
    BitmapConverter::class,
    UserConverter::class,
    ProjectConverter::class,
    TargetConverter::class,
    YearIncomeConverter::class,
    TransferConverter::class,
    VisitionConverter::class
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun projectDao(): ProjectDao
    abstract fun yearIncomeDao(): YearIncomeDao
    abstract fun targetDao(): TargetDao
    abstract fun transactionDao(): TransactionDao
    abstract fun transferDao(): TransferDao
    abstract fun visitionDao(): VisitionDao
    abstract fun userDao(): UserDao
    abstract fun partnerDao(): PartnerDao
    abstract fun detailsPartnerDao(): DetailsPartnerDao

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