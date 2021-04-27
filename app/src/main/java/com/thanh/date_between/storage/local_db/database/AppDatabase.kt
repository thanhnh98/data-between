package com.thanh.date_between.storage.local_db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thanh.date_between.storage.local_db.dao.UrlDao
import com.thanh.date_between.model.PinModel
import com.thanh.date_between.model.UrlModel
import com.thanh.date_between.model.UserModel
import com.thanh.date_between.model.converter.Converter
import java.lang.IllegalStateException
import java.lang.NullPointerException

@Database(version = 1, entities = [PinModel::class, UrlModel::class, UserModel::class])
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase() {
    companion object{
        private var appDatabase: AppDatabase? = null
        private val DB_NAME = "DEEPLINK"

        fun init(context: Context){
            appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
            appDatabase?.context = context
        }

        fun getInstance(): AppDatabase{
            if (appDatabase == null)
                throw IllegalStateException("Must call init before")

            val appDatabaseInstance = appDatabase

            return appDatabaseInstance?: throw NullPointerException("appDatabaseInstance is NULL")
        }
    }

    private lateinit var context: Context

    abstract fun getUrlDao(): UrlDao

}