package com.thanh.date_between.screen.sat_sun

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.thanh.date_between.R
import com.thanh.date_between.common.AdsManager
import com.thanh.date_between.common.NormalizeHelper
import com.thanh.date_between.common.adapter.RecyclerManager
import com.thanh.date_between.common.adapter.item.RecycleViewItem
import com.thanh.date_between.common.adapter.item.spacing.SpacingRecyclerItem
import com.thanh.date_between.common.base.BaseActivity
import com.thanh.date_between.databinding.ActivityViewDetailBinding
import com.thanh.date_between.screen.home.viewmodel.HomeViewModel
import com.thanh.date_between.screen.sat_sun.item.DetailItem
import kodeinViewModel
import org.kodein.di.generic.instance
import java.time.DayOfWeek
import kotlin.reflect.KClass


class ViewDetailDatesActivity: BaseActivity<ActivityViewDetailBinding, HomeViewModel>() {
    companion object {
        private const val KEY_DAY_OF_WEEK = "KEY_DAY_OF_WEEK"
        operator fun invoke(
            from: Context,
            dayOfWeek: DayOfWeek
        ): Intent {
            return Intent(from, ViewDetailDatesActivity::class.java).apply {
                putExtras(
                    bundleOf(
                        KEY_DAY_OF_WEEK to dayOfWeek
                    )
                )
            }
        }
    }
    private val adsManager: AdsManager by instance()

    private val mRecyclerManager: RecyclerManager<KClass<*>> = RecyclerManager()

    private val dayOfWeek: DayOfWeek by lazy {
        intent?.getSerializableExtra(KEY_DAY_OF_WEEK) as DayOfWeek
    }

    override val layoutResId: Int = R.layout.activity_view_detail
    override val viewModel: HomeViewModel by kodeinViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCluster()
        initUI()
        setupRecyclerView()
    }

    private fun setupAds() {
        val adRequest = AdRequest.Builder().build()
        dataBinding.adView.loadAd(adRequest)
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
    }

    private fun initCluster() {
        mRecyclerManager.addCluster(DetailItem::class)
    }

    private fun setupRecyclerView() {
        dataBinding.recyclerView.adapter = mRecyclerManager.adapter
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        showListItem()
    }

    private fun initUI() {
        when (dayOfWeek){
            DayOfWeek.SATURDAY -> {
                dataBinding.tvTitle.text = "Danh sách ngày Thứ 7"
            }
            DayOfWeek.SUNDAY -> {
                dataBinding.tvTitle.text = "Danh sách ngày Chủ Nhật"
            }
            else -> {

            }
        }
        setupAds()
    }

    private fun showListItem() {
        mRecyclerManager.replace(DetailItem::class, buildListItem())
    }

    private fun buildListItem(): List<RecycleViewItem<*>> {
        val listItem = ArrayList<RecycleViewItem<*>>()
        val listDates = viewModel.getListDate(dayOfWeek)
        listDates?.forEach {
            listItem.add(DetailItem(it))
        }
        listItem.add(SpacingRecyclerItem(NormalizeHelper.convertDpToPixel(80), 0))
        return listItem
    }
}