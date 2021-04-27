package com.thanh.date_between.helper

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTimeHelper {
    companion object {
        fun getTime(dateString: String?): Long {
            val isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            isoFormat.timeZone = TimeZone.getTimeZone("UTC")
            try {
                val date = isoFormat.parse(dateString)
                return date.time
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return System.currentTimeMillis()
        }

        fun getTimeFormatted(time: String?): String? {
            var isoFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val date: Date
            try {
                date = isoFormat.parse(time)
                isoFormat = SimpleDateFormat("HH:mm, dd-MM-yyyy")
                return isoFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return time
        }

        fun getSimpleDate(time: Long): String{
            val isoFormat = SimpleDateFormat("dd/MM, HH:mm:ss")

            return isoFormat.format(time)
        }
    }
}