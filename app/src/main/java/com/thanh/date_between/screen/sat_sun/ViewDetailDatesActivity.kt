package com.thanh.date_between.screen.sat_sun

import android.os.Bundle
import com.thanh.date_between.R
import com.thanh.date_between.common.base.BaseActivity
import com.thanh.date_between.databinding.ActivityViewDetailBinding
import kodeinViewModel

class ViewDetailDatesActivity: BaseActivity<ActivityViewDetailBinding, ViewDetailDatesViewModel>() {
    override val layoutResId: Int = R.layout.activity_view_detail
    override val initViewModel: ViewDetailDatesViewModel by kodeinViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        initCluster()
        initListener()
        initObservers()
    }

    private fun initObservers() {

    }

    private fun initListener() {

    }

    private fun initCluster() {

    }

    private fun initUI() {
        TODO("Not yet implemented")
    }
}