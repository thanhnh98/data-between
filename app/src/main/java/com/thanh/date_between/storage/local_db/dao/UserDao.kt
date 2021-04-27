package com.thanh.date_between.storage.local_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thanh.date_between.model.UserModel
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUrl(user: UserModel)

    @Query("delete from USER where id = :id")
    fun removeUrl(id: Int): Single<Int>


}