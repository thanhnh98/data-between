package com.thanh.date_between.storage

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thanh.date_between.model.UrlModel
import java.lang.Exception
import kotlin.collections.ArrayList
import kotlin.collections.List

class AppPreferences(context: Context?) {

    private var mPreferences: SharedPreferences
    private var PREFERENCES_NAME:String = "APP_Preferences"

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
}