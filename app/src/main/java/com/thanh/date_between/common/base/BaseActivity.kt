package com.thanh.date_between.common.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

abstract class BaseActivity<VB: ViewDataBinding, VM: BaseViewModel>: AppCompatActivity(), KodeinAware {

    lateinit var viewModel: VM
    lateinit var dataBinding: VB
    override val kodein by kodein()

    abstract val layoutResId: Int
    abstract val initViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layoutResId)
        viewModel = initViewModel
        viewModel.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::viewModel.isInitialized)
            viewModel.onDestroy()
    }
}