package com.thanh.date_between.screen.home.viewmodel

import android.util.Log
import com.google.gson.Gson
import com.thanh.date_between.common.SingleLiveEvent
import com.thanh.date_between.common.base.BaseViewModel
import com.thanh.date_between.model.DateModel
import com.thanh.date_between.storage.AppPreferences
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.stream.Collectors
import java.util.stream.LongStream
import kotlin.collections.ArrayList

class HomeViewModel: BaseViewModel() {
    private var beginDateEvent = SingleLiveEvent<DateModel>()
    private var endDateEvent = SingleLiveEvent<DateModel>()
    private var validDatesEvent = SingleLiveEvent<Boolean>()
    private var onRequestChangeBeginDate = SingleLiveEvent<DateModel>()
    private var onRequestChangeEndDate = SingleLiveEvent<DateModel>()
    private var totalDatesAcceptedCalculatedEvent = SingleLiveEvent<Triple<List<DateModel>, List<DateModel>, List<DateModel>>>()
    private var onListHolidayChangedEvent = SingleLiveEvent<List<DateModel>>()
    //<remain, weekend, holiday>
    var filterWeekendT7 = true
    var filterWeekendCN = true
    var filterHoliday = true

    private var listHoliday: List<DateModel> = ArrayList()

    private var listWeekendFiltered: MutableList<DateModel> = ArrayList()
    private var listHolidayFiltered: MutableList<DateModel> = ArrayList()
    private var listDayRemainAccepted: MutableList<DateModel> = ArrayList()

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

    fun onTotalDatesCalculated(): SingleLiveEvent<Triple<List<DateModel>, List<DateModel>, List<DateModel>>>{
        return totalDatesAcceptedCalculatedEvent
    }

    fun onListHolidayChanged(): SingleLiveEvent<List<DateModel>>{
        return onListHolidayChangedEvent
    }

    init {
        initValue()
    }

    fun initValue(){
        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        var initDate = DateModel(mDay, mMonth + 1, mYear)
        beginDateEvent.value = initDate
        endDateEvent.value = initDate
        listHoliday = getListHoliday()
        listWeekendFiltered.clear()
        listDayRemainAccepted.clear()
        listHolidayFiltered.clear()
        totalDatesAcceptedCalculatedEvent.postValue(Triple(ArrayList(), ArrayList(), ArrayList()))
        calculate()
    }

    fun updateBeginDate(date: DateModel){
        beginDateEvent.value = date
        if (validateBefore())
            calculate()
    }

    fun updateEndDate(date: DateModel){
        endDateEvent.value = date
        if(validateBefore())
            calculate()
    }

    fun swap(){
        val dateBegin = beginDateEvent.value
        val dateEnd = endDateEvent.value
        beginDateEvent.value = dateEnd
        endDateEvent.value = dateBegin
        if (validateBefore())
            calculate()
    }

    private fun excuteCalcuation(){
        listWeekendFiltered.clear()
        listHolidayFiltered.clear()
        listDayRemainAccepted.clear()
        listHoliday = getListHoliday()

        val begin = beginDateEvent.value
        val end = endDateEvent.value
        val startDate: LocalDate = LocalDate.parse(begin?.getDateLocalFormat())
        val endDate: LocalDate = LocalDate.parse(end?.getDateLocalFormat())
        val daysBetween: Long = ChronoUnit.DAYS.between(startDate, endDate) + 1
        val totalDates: List<LocalDate> = LongStream.iterate(0) { i -> i + 1 }
            .limit(daysBetween).mapToObj { i -> startDate.plusDays(i) }
            .collect(Collectors.toList())
        totalDates.filter {

            if (filterWeekendT7) {
                if (it.dayOfWeek == DayOfWeek.SATURDAY) {
                    listWeekendFiltered.add(
                        DateModel(
                            it.dayOfMonth,
                            it.monthValue,
                            year = it.year,
                            dayOfWeek = it.dayOfWeek
                        )
                    )
                    return@filter false
                }
            }

            if (filterWeekendCN) {
                if (it.dayOfWeek == DayOfWeek.SUNDAY) {
                    listWeekendFiltered.add(
                        DateModel(
                            it.dayOfMonth,
                            it.monthValue,
                            year = it.year,
                            dayOfWeek = it.dayOfWeek
                        )
                    )
                    return@filter false
                }
            }

            if (filterHoliday) {
                listHoliday.forEach { date ->
                    val dateModel = DateModel(
                        it.dayOfMonth,
                        it.monthValue,
                        year = it.year,
                        dayOfWeek = it.dayOfWeek
                    )
//                    Log.e(date.getDateHolidayFormat(), dateModel.getDateHolidayFormat())
                    if (date.getDateHolidayFormat() == dateModel.getDateHolidayFormat()) {
                        listHolidayFiltered.add(dateModel)
                        return@filter false
                    }
                }
            }

            true
        }.forEachIndexed{ _, locale ->
            listDayRemainAccepted.add(DateModel(locale.dayOfMonth, locale.monthValue, year = locale.year, dayOfWeek = locale.dayOfWeek))
        }
    }

    fun calculate(){
        if (!validateBefore())
            return

        excuteCalcuation()
        totalDatesAcceptedCalculatedEvent.postValue(Triple(listDayRemainAccepted, listWeekendFiltered, listHolidayFiltered))
    }


    fun requestChangeBeginDate(){
        onRequestChangeBeginDate.postValue(beginDateEvent.value)
    }

    fun requestChangeEndDate(){
        onRequestChangeEndDate.postValue(endDateEvent.value)
    }

    private fun validateBefore(): Boolean{
        val begin = beginDateEvent.value
        val end = endDateEvent.value
        val startDate: LocalDate = LocalDate.parse(begin?.getDateLocalFormat())
        val endDate: LocalDate = LocalDate.parse(end?.getDateLocalFormat())
        return if (startDate.isAfter(endDate)) {
            validDatesEvent.postValue(false)
            false
        } else {
            validDatesEvent.postValue(true)
            true
        }
    }

    fun resetState(){
        initValue()
    }

    fun onFilterWeekendStatusChangedT7(isEnable: Boolean){
        this.filterWeekendT7 = isEnable
        calculate()
    }

    fun onFilterWeekendStatusChangedCN(isEnable: Boolean){
        this.filterWeekendCN = isEnable
        calculate()
    }

    fun onFilterHolidayStatusChanged(isEnable: Boolean){
        this.filterHoliday = isEnable
        calculate()
    }

    fun getListHoliday(): List<DateModel>{
        return AppPreferences.getInstance().getListHoliday()
    }

    fun addHoliday(date: DateModel){
        AppPreferences.getInstance().addHoliday(date)
        onListHolidayChangedEvent.postValue(getListHoliday())
        calculate()
    }
    fun deleteHoliday(date: DateModel){
        AppPreferences.getInstance().deleteHoliday(date)
        calculate()
    }
}