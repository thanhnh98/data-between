package com.thanh.date_between.model.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.thanh.date_between.model.PinModel
import com.thanh.date_between.model.UserModel


class Converter {
    @TypeConverter
    fun toUser(js: String): UserModel{
        return Gson().fromJson(js, UserModel::class.java)
    }

    @TypeConverter
    fun toString(user: UserModel): String{
        return Gson().toJson(user)
    }

    @TypeConverter
    fun toPin(js: String): PinModel {
        return Gson().fromJson(js, PinModel::class.java)
    }

    @TypeConverter
    fun toString(pin: PinModel): String{
        return Gson().toJson(pin)
    }
}