package com.thanh.date_between.screen.home.viewmodel

import android.util.Log
import com.thanh.date_between.common.SingleLiveEvent
import com.thanh.date_between.common.base.BaseViewModel
import com.thanh.date_between.model.DateModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.Collectors
import java.util.stream.LongStream

class HomeViewModel: BaseViewModel() {
    private var beginDateEvent = SingleLiveEvent<DateModel>()
    private var endDateEvent = SingleLiveEvent<DateModel>()
    private var validDatesEvent = SingleLiveEvent<Boolean>()
    private var onRequestChangeBeginDate = SingleLiveEvent<DateModel>()
    private var onRequestChangeEndDate = SingleLiveEvent<DateModel>()

    fun onBeginDateObserve(): SingleLiveEvent<DateModel>{
        return beginDateEvent
    }

    fun onEndDateObserve(): SingleLiveEvent<DateModel>{
        return endDateEvent
    }

    fun onValidDatesObserve(): SingleLiveEvent<Boolean>{
        return validDatesEvent
    }

    fun onRequestChangeEndDateObserve(): SingleLiveEvent<DateModel>{
        return onRequestChangeEndDate
    }

    fun onRequestChangeBeginDateObserve(): SingleLiveEvent<DateModel>{
        return onRequestChangeBeginDate
    }

    init {
        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        var initDate = DateModel(mDay, mMonth + 1, mYear)
        beginDateEvent.value = initDate
        endDateEvent.value = initDate

    }

    fun updateBeginDate(date: DateModel){
        beginDateEvent.postValue(date)
    }

    fun updateEndDate(date: DateModel){
        endDateEvent.postValue(date)
    }

    fun swap(){
        val dateBegin = beginDateEvent.value
        val dateEnd = endDateEvent.value
        beginDateEvent.value = dateEnd
        endDateEvent.value = dateBegin
    }

    fun printBetweenDates(){
        val begin = beginDateEvent.value
        val end = endDateEvent.value
        val startDate: LocalDate = LocalDate.parse(begin?.getDateLocalFormat())
        val endDate: LocalDate = LocalDate.parse(end?.getDateLocalFormat())
        val daysBetween: Long = ChronoUnit.DAYS.between(startDate, endDate) + 1
        val totalDates: List<LocalDate> = LongStream.iterate(0) { i -> i + 1 }
            .limit(daysBetween).mapToObj { i -> startDate.plusDays(i) }
            .collect(Collectors.toList())
        totalDates.filter {
            if(it.dayOfWeek == DayOfWeek.SUNDAY || it.dayOfWeek == DayOfWeek.SATURDAY){
                Log.e("ignored ${it.dayOfWeek}", DateModel(it.dayOfMonth, it.monthValue, year = it.year).toString())
                return@filter false
            }
            true
        }.forEachIndexed{ _, locale ->
            Log.e("${locale.dayOfWeek}", DateModel(locale.dayOfMonth, locale.monthValue, year = locale.year).toString())
        }
    }

    fun requestChangeBeginDate(){
        onRequestChangeBeginDate.postValue(beginDateEvent.value)
    }

    fun requestChangeEndDate(){
        onRequestChangeEndDate.postValue(endDateEvent.value)
    }
}