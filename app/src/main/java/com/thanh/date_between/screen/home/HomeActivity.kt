package com.thanh.date_between.screen.home

import android.os.Bundle
import com.thanh.date_between.R
import com.thanh.date_between.common.adapter.RecyclerManager
import com.thanh.date_between.common.base.BaseActivity
import com.thanh.date_between.common.base.BaseViewModel
import com.thanh.date_between.databinding.ActivityHomeBinding
import kodeinViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import kotlin.reflect.KClass

class HomeActivity: BaseActivity<ActivityHomeBinding, BaseViewModel>(), KodeinAware{

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

    private fun initObservers() {
    }

    private fun initListener() {
    }

    private fun initCluster() {
        mRecyclerManager = RecyclerManager()
        addCluster()
    }

    private fun addCluster() {

    }

    override val layoutResId: Int = R.layout.activity_home

    override val initViewModel: BaseViewModel by kodeinViewModel()
}