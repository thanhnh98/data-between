package com.thanh.date_between.model

import androidx.room.*

@Entity(tableName = "URL")
class UrlModel(
    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "createdAt")
    var createdAt: String,

    @ColumnInfo(name = "createBy")
    var createdBy: UserModel,

    @ColumnInfo(name = "typeUrl", defaultValue = "1") // 0 = Deep link, 1 = Universal link, 2 = others
    var typeUrl: Int,

    @ColumnInfo(name = "isEnable", defaultValue = "true")
    var isEnable: Boolean,

    @ColumnInfo(name = "pin")
    var pin: PinModel
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "$id-$url-$createdAt"
    }
}