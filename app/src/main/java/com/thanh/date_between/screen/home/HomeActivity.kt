package com.thanh.date_between.screen.home

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import com.thanh.date_between.R
import com.thanh.date_between.common.adapter.RecyclerManager
import com.thanh.date_between.common.base.BaseActivity
import com.thanh.date_between.databinding.ActivityHomeBinding
import com.thanh.date_between.extension.onClick
import com.thanh.date_between.model.DateModel
import com.thanh.date_between.screen.home.viewmodel.HomeViewModel
import kodeinViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import kotlin.reflect.KClass

class HomeActivity: BaseActivity<ActivityHomeBinding, HomeViewModel>(), KodeinAware{

    override val kodein by kodein()

    private lateinit var mRecyclerManager: RecyclerManager<KClass<*>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCluster()
        initListener()
        initObservers()
        initUI()
    }

    private fun initUI() {

    }



    private fun getDatePickerDialog(dateDefault: DateModel, listener: OnDateSetListener): DatePickerDialog{
        return DatePickerDialog(
            this,
            listener,
            dateDefault.year, dateDefault.month - 1, dateDefault.day)
    }

    private fun initObservers() {
        viewModel.onBeginDateObserve().observe(this, Observer {
            dataBinding.tvStartDay.text = it.toString()
        })

        viewModel.onEndDateObserve().observe(this, Observer {
            dataBinding.tvEndDay.text = it.toString()
        })

        viewModel.onValidDatesObserve().observe(this, Observer {

        })

        viewModel.onRequestChangeBeginDateObserve().observe(this, Observer {
            getDatePickerDialog(dateDefault = it,
                listener = OnDateSetListener { _, year, month, dayOfMonth ->
                    DateModel(dayOfMonth, month + 1, year).apply {
                        viewModel.updateBeginDate(this)
                    }
                }).show()
        })

        viewModel.onRequestChangeEndDateObserve().observe(this, Observer {
            getDatePickerDialog(dateDefault = it,
                listener = OnDateSetListener { _, year, month, dayOfMonth ->
                    DateModel(dayOfMonth, month + 1, year).apply {
                        viewModel.updateEndDate(this)
                    }
                }).show()
        })
    }

    private fun initListener() {
        dataBinding.tvStartDay.onClick {
            viewModel.requestChangeBeginDate()
        }

        dataBinding.tvEndDay.onClick {
            viewModel.requestChangeEndDate()
        }

        dataBinding.tvDateSwap.onClick {
            viewModel.swap()
        }

        dataBinding.tvDateBetween.onClick {
            viewModel.printBetweenDates()
        }
    }

    private fun initCluster() {
        mRecyclerManager = RecyclerManager()
        addCluster()
    }

    private fun addCluster() {

    }

    override val layoutResId: Int = R.layout.activity_home

    override val initViewModel: HomeViewModel by kodeinViewModel()
}