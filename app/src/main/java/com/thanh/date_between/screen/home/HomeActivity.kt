package com.thanh.date_between.screen.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.thanh.date_between.R
import com.thanh.date_between.common.AdsManager
import com.thanh.date_between.common.base.BaseActivity
import com.thanh.date_between.convertDpToPixel
import com.thanh.date_between.databinding.ActivityHomeBinding
import com.thanh.date_between.extension.onClick
import com.thanh.date_between.extension.twoNumberOf
import com.thanh.date_between.getScreenHeight
import com.thanh.date_between.model.DateModel
import com.thanh.date_between.screen.edit_holiday.EditListHolidayActivity
import com.thanh.date_between.screen.home.viewmodel.HomeViewModel
import com.thanh.date_between.screen.sat_sun.ViewDetailDatesActivity
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
        prepareAds()
    }

    private var nativeAds: NativeAd? = null
    @SuppressLint("MissingPermission")
    private fun prepareAds() {
        val adLoader: AdLoader = AdLoader.Builder(this, getString(R.string.key_ads_native_1))
            .forNativeAd { nativeAd ->
                this.nativeAds = nativeAd
                val styles = NativeTemplateStyle.Builder().withMainBackgroundColor(
                    ColorDrawable(
                        ResourcesCompat.getColor(resources, R.color.white, null)
                    )
                )
                    .build()
                val template: TemplateView = dataBinding.adsTemplate
                template.visibility = View.VISIBLE
                template.setStyles(styles)
                template.setNativeAd(nativeAd)
            }
            .build()

        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun initUI() {
        viewModel.initValue()
        dataBinding.cbWeekendT7.isChecked = true
        dataBinding.cbWeekendCn.isChecked = true
        dataBinding.cbHoliday.isChecked = true
        dataBinding.imgMenu.onClick {
            showPopup(it)
        }
    }

    private fun showPopup(viewCall: View) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupLayout = inflater.inflate(R.layout.popup_menu, null)
        val popupWidth = resources.getDimensionPixelSize(R.dimen.popup_width)
        val popupHeight = resources.getDimensionPixelSize(R.dimen.popup_height)
        val popupWindow = PopupWindow(
            popupLayout,
            popupWidth,
            popupHeight,
            true
        )

        popupLayout.findViewById<TextView>(R.id.tv_other_apps).onClick {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5540559479839330036")))
            popupWindow.dismiss()
        }

        popupLayout.findViewById<TextView>(R.id.tv_rating).onClick {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
            popupWindow.dismiss()
        }

        //show popup menu
        val values = IntArray(2)
        viewCall.getLocationInWindow(values)
        val positionOfIcon = values[1]
        val height = getScreenHeight() * 2 / 3
        if (positionOfIcon > height) {
            // when parent view in the bottom of the screen show popup up
            val offsetY = resources.getDimensionPixelSize(R.dimen._200dp)
            popupWindow.showAsDropDown(viewCall, 0, -offsetY, Gravity.END)
        } else {
            // when parent view in the bottom of the screen show popup down
            popupWindow.showAsDropDown(
                viewCall,
                0,
                convertDpToPixel(8F, this).toInt(),
                Gravity.END
            )
        }
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

        viewModel.onTotalDatesCalculated().observe(this) {
            dataBinding.tvTotalDay.text = it.first.size.twoNumberOf()
            dataBinding.tvTotalWeekendT7.text =
                it.second.filter { it.dayOfWeek == DayOfWeek.SATURDAY }.size.twoNumberOf() + " ngày"
            dataBinding.tvTotalWeekendCn.text =
                it.second.filter { it.dayOfWeek == DayOfWeek.SUNDAY }.size.twoNumberOf() + " ngày"
            dataBinding.tvTotalHoliday.text = it.third.size.twoNumberOf() + " ngày"
            dataBinding.tvTotalDayNotFilterd.text =
                (it.first.size + it.second.size + it.third.size).twoNumberOf() + " ngày"
        }

        viewModel.onListHolidayChanged().observe(this) {
            viewModel.calculate()
        }
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

        dataBinding.tvViewDetailSaturday.onClick {
            startActivity(
                ViewDetailDatesActivity(
                    this,
                    DayOfWeek.SATURDAY
                )
            )
        }

        dataBinding.tvViewDetailSunday.onClick {
            startActivity(
                ViewDetailDatesActivity(
                    this,
                    DayOfWeek.SUNDAY
                )
            )
        }

    }

    private fun initCluster() {
    }

    private fun addCluster() {

    }

    override val layoutResId: Int = R.layout.activity_home

    override val viewModel: HomeViewModel by kodeinViewModel()

    override fun onResume() {
        super.onResume()
        viewModel.calculate()
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeAds?.destroy()
    }
}