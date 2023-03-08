package com.thanh.date_between.screen.edit_holiday

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.gson.Gson
import com.thanh.date_between.R
import com.thanh.date_between.common.NormalizeHelper
import com.thanh.date_between.common.adapter.RecyclerManager
import com.thanh.date_between.common.adapter.item.RecycleViewItem
import com.thanh.date_between.common.adapter.item.spacing.SpacingRecyclerItem
import com.thanh.date_between.common.base.BaseActivity
import com.thanh.date_between.databinding.ActivityEditListHolidayBinding
import com.thanh.date_between.dialog.AddDialogListener
import com.thanh.date_between.dialog.AddHolidayDialog
import com.thanh.date_between.extension.onClick
import com.thanh.date_between.model.DateModel
import com.thanh.date_between.screen.edit_holiday.item.HolidayItem
import com.thanh.date_between.screen.home.viewmodel.HomeViewModel
import kodeinViewModel
import kotlin.reflect.KClass

class EditListHolidayActivity: BaseActivity<ActivityEditListHolidayBinding, HomeViewModel>(), AddDialogListener {
    private lateinit var mRecyclerManager: RecyclerManager<KClass<*>>

    override val layoutResId: Int = R.layout.activity_edit_list_holiday
    override val viewModel: HomeViewModel by kodeinViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        initCluster()
        initListener()
        initObservers()
        setupRecyclerView()
        prepareAds()
    }



    private fun setupRecyclerView() {
        dataBinding.recyclerView.adapter = mRecyclerManager.adapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        showListItem()
    }

    private fun showListItem(){
        mRecyclerManager.replace(HolidayItem::class, buildListItem())
    }

    private fun buildListItem(): List<RecycleViewItem<*>> {
        val listItem = ArrayList<RecycleViewItem<*>>()
        val listDates = viewModel.getListHoliday()
        listDates.forEach {
            listItem.add(HolidayItem(it){ position, date ->
                onHolidayDeleted(position, date)
            })
        }
        listItem.add(SpacingRecyclerItem(NormalizeHelper.convertDpToPixel(80), 0))
        return listItem
    }

    private fun onHolidayDeleted(position: Int, dateModel: DateModel){
        mRecyclerManager.remove(HolidayItem::class, position)
        viewModel.deleteHoliday(dateModel)
    }

    private fun initListener() {

    }

    private fun initObservers() {
        viewModel.onTotalDatesCalculated().observe(this) {

        }

        viewModel.onListHolidayChanged().observe(this) {
            showListItem()
        }
    }

    private fun initUI() {
        dataBinding.icBack.onClick {
            finish()
        }
        dataBinding.btnAddHoliday.onClick {
            AddHolidayDialog.getInstance(this).show(supportFragmentManager, "edit holiday")
        }
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

    private fun initCluster() {
        mRecyclerManager = RecyclerManager()
        addCluster()
    }

    private fun addCluster() {
        mRecyclerManager.addCluster(HolidayItem::class)
    }

    override fun onSelectedDate(date: DateModel) {
        viewModel.addHoliday(date)
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeAds?.destroy()
    }

}