package com.thanh.date_between.screen.home

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.thanh.date_between.R
import com.thanh.date_between.common.AdsManager
import com.thanh.date_between.common.base.BaseActivity
import com.thanh.date_between.databinding.ActivityHomeBinding
import com.thanh.date_between.extension.onClick
import com.thanh.date_between.extension.twoNumberOf
import com.thanh.date_between.model.DateModel
import com.thanh.date_between.screen.edit_holiday.EditListHolidayActivity
import com.thanh.date_between.screen.home.viewmodel.HomeViewModel
import kodeinViewModel
import org.kodein.di.generic.instance
import java.time.DayOfWeek

class HomeActivity: BaseActivity<ActivityHomeBinding, HomeViewModel>(){

    private val adsManager: AdsManager by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        initCluster()
        initListener()
        initObservers()
        initAds()
        adsManager.prepareAds()
    }

    private fun initAds() {
        dataBinding.adView.adListener = object: AdListener() {
            override fun onAdLoaded() {
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
            }

            override fun onAdOpened() {
            }

            override fun onAdClicked() {
            }

            override fun onAdClosed() {
            }
        }

        val adRequest = AdRequest.Builder().build()
        dataBinding.adView.loadAd(adRequest)
    }

    private fun initUI() {
        viewModel.initValue()
        dataBinding.cbWeekendT7.isChecked = true
        dataBinding.cbWeekendCn.isChecked = true
        dataBinding.cbHoliday.isChecked = true
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
            if (it)
                dataBinding.tvError.visibility = View.GONE
            else {
                dataBinding.tvError.visibility = View.VISIBLE
            }
        })

        viewModel.onRequestChangeBeginDateObserve().observe(this, Observer {
            getDatePickerDialog(dateDefault = it,
                listener = { _, year, month, dayOfMonth ->
                    DateModel(dayOfMonth, month + 1, year).apply {
                        viewModel.updateBeginDate(this)
                    }
                }).show()
        })

        viewModel.onRequestChangeEndDateObserve().observe(this, Observer {
            getDatePickerDialog(dateDefault = it,
                listener = { _, year, month, dayOfMonth ->
                    DateModel(dayOfMonth, month + 1, year).apply {
                        viewModel.updateEndDate(this)
                    }
                }).show()
        })

        viewModel.onTotalDatesCalculated().observe(this, {
            dataBinding.tvTotalDay.text = it.first.size.twoNumberOf()
            dataBinding.tvTotalWeekendT7.text = it.second.filter { it.dayOfWeek == DayOfWeek.SATURDAY }.size.twoNumberOf() + " ngày"
            dataBinding.tvTotalWeekendCn.text = it.second.filter { it.dayOfWeek == DayOfWeek.SUNDAY }.size.twoNumberOf() + " ngày"
            dataBinding.tvTotalHoliday.text = it.third.size.twoNumberOf() + " ngày"
            dataBinding.tvTotalDayNotFilterd.text = (it.first.size + it.second.size + it.third.size).twoNumberOf() + " ngày"
        })

        viewModel.onListHolidayChanged().observe(this, {
            viewModel.calculate()
        })
    }

    private fun initListener() {
        dataBinding.tvStartDay.onClick {
            viewModel.requestChangeBeginDate()
        }

        dataBinding.tvEndDay.onClick {
            viewModel.requestChangeEndDate()
        }

        dataBinding.imgSwap.onClick {
            viewModel.swap()
        }

        dataBinding.tvReset.onClick {
            viewModel.resetState()
        }

        dataBinding.cbHoliday.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onFilterHolidayStatusChanged(isChecked)
        }

        dataBinding.cbWeekendT7.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onFilterWeekendStatusChangedT7(isChecked)
        }

        dataBinding.cbWeekendCn.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onFilterWeekendStatusChangedCN(isChecked)
        }

        dataBinding.imgEditListHoliday.onClick {
            adsManager.show(
                this,
                onFailedToShow = {
                    startActivity(Intent(this, EditListHolidayActivity::class.java))
                },
                onDismiss = {
                    startActivity(Intent(this, EditListHolidayActivity::class.java))
                },
                onOtherException = {
                    startActivity(Intent(this, EditListHolidayActivity::class.java))
                }
            )
        }
    }

    private fun initCluster() {
    }

    private fun addCluster() {

    }

    override val layoutResId: Int = R.layout.activity_home

    override val initViewModel: HomeViewModel by kodeinViewModel()

    override fun onResume() {
        super.onResume()
        viewModel.calculate()
    }
}