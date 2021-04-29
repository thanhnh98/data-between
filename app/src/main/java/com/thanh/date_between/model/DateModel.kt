package com.thanh.date_between.model

import com.thanh.date_between.extension.twoNumberOf
import java.time.Month

data class DateModel(val day: Int, val month: Int, val year: Int){
    override fun toString(): String {
        return "${day.twoNumberOf()}-${month.twoNumberOf()}-${year.twoNumberOf()}"
    }

    fun getDateLocalFormat(): String{
        return "${year.twoNumberOf()}-${month.twoNumberOf()}-${day.twoNumberOf()}"
    }
}