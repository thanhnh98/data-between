package com.thanh.date_between.storage.local_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.thanh.date_between.model.UrlModel

@Dao
interface UrlDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertUrl(url: UrlModel)

    @Query("select * from URL order by URL.id DESC")
    suspend fun getListUrl(): List<UrlModel>

    @Query("delete from URL where id = :id")
    suspend fun removeUrl(id: Int): Int

    @Query("select * from URL where id = :id")
    suspend fun getUrlById(id: Int): UrlModel

    @Query("delete from URL")
    suspend fun removeAllLocalData(): Int
}