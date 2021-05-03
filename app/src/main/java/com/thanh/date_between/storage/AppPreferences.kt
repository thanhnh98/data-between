package com.thanh.date_between.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thanh.date_between.model.DateModel
import kotlin.collections.ArrayList
import kotlin.collections.List

class AppPreferences(context: Context?) {

    private var mPreferences: SharedPreferences
    private var PREFERENCES_NAME: String = "APP_Preferences"
    private var LIST_HOLIDAY: String = "LIST_HOLIDAY"

    init {
        mPreferences = context?.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)!!
    }

    companion object{
        private lateinit var mInstance: AppPreferences

        @Synchronized
        fun getInstance(): AppPreferences {
            if (mInstance == null)
                throw NullPointerException("LoshipPreferences is null!")
            return mInstance
        }

        @Synchronized
        fun init(context: Context?) {
            mInstance = AppPreferences(context)
        }
    }

    fun setListHoliday(listData: List<DateModel>?){
        if (listData == null)
            return
        mPreferences.edit().apply {
            putString(LIST_HOLIDAY, Gson().toJson(listData))
            commit()
        }
    }

    fun addHoliday(date: DateModel){
        val listData = getListHoliday().toMutableList()

        listData.forEachIndexed { index, dateModel ->
            if (dateModel.equalHoliday(date)) {
                listData[index] = date
                setListHoliday(listData)
                return
            }
        }
        listData.add(0, date)
        setListHoliday(listData)
    }

    fun deleteHoliday(date: DateModel){
        val listData = getListHoliday().toMutableList()

        for (i in listData){
            if (i.equalHoliday(date)){
                listData.remove(i)
                break
            }
        }
        setListHoliday(listData)
    }

    fun getListHoliday(): List<DateModel>{
        val listDataStr = mPreferences.getString(LIST_HOLIDAY, "")

        if (listDataStr.isNullOrEmpty())
            return ArrayList()
        val type = object : TypeToken<List<DateModel>>() {}.type

        return Gson().fromJson(listDataStr, type)
    }
}