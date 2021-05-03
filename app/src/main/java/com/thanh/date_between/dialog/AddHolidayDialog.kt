package com.thanh.date_between.dialog

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thanh.date_between.R
import com.thanh.date_between.common.base.BaseDialog
import com.thanh.date_between.extension.onClick
import com.thanh.date_between.model.DateModel
import kotlinx.android.synthetic.main.dialog_add_holiday.*
import java.util.*

class AddHolidayDialog: BaseDialog() {


    companion object{
        private var listener: AddDialogListener? = null
        fun getInstance(listener: AddDialogListener): AddHolidayDialog{
            this.listener = listener
            return AddHolidayDialog()
        }
    }
    private lateinit var currentDate: DateModel

    init {
        val c: Calendar = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)
        currentDate = DateModel(mDay, mMonth + 1, mYear)
    }

    override fun initView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater?.inflate(R.layout.dialog_add_holiday, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_holiday.onClick {
            getDatePickerDialog(currentDate) { _, year, month, dayOfMonth ->
                DateModel(dayOfMonth, month + 1, year).apply {
                    currentDate = this
                    it.text = this.getDateHolidayFormat()
                }
            }.show()
        }

        tv_holiday.text = currentDate.getDateHolidayFormat()

        tvPositive.onClick {
            currentDate.dayInfo = edt_description.text.toString()
            listener?.onSelectedDate(currentDate)
            dismiss()
        }
    }

    private fun getDatePickerDialog(dateDefault: DateModel, listener: DatePickerDialog.OnDateSetListener): DatePickerDialog {
        return DatePickerDialog(
            activity!!,
            listener,
            dateDefault.year, dateDefault.month - 1, dateDefault.day)
    }
}