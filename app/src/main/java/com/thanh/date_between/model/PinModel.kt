package com.thanh.date_between.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PIN")
data class PinModel(
    @ColumnInfo(name = "createdAt")
    var createdAt: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}