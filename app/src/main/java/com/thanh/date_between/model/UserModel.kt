package com.thanh.date_between.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "USER")
data class UserModel(
    @ColumnInfo(name = "name")
    var name: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}