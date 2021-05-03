package com.thanh.date_between.model

import com.thanh.date_between.extension.twoNumberOf
import java.time.DayOfWeek

data class DateModel(
    val day: Int,
    val month: Int,
    val year: Int,
    val dayOfWeek: DayOfWeek? = null,
    val dayName: DateType? = DateType.OTHER,
    var dayInfo: String? = ""
){
    override fun toString(): String {
        return "${day.twoNumberOf()}-${month.twoNumberOf()}-${year.twoNumberOf()}"
    }

    fun getDateLocalFormat(): String{
        return "${year.twoNumberOf()}-${month.twoNumberOf()}-${day.twoNumberOf()}"
    }

    fun getDateHolidayFormat(): String{
        return "${day.twoNumberOf()}-${month.twoNumberOf()}"
    }

    fun equalHoliday(other: DateModel): Boolean {
        return (other.day == this.day && other.month == this.month)
    }
}