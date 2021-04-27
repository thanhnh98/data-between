package com.thanh.date_between.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

class BaseViewModel: ViewModel() {
    private lateinit var job: Job
    lateinit var uiScope: CoroutineScope
    lateinit var ioContext: CoroutineContext

    open fun init() {
        onCreate()
    }

    protected open fun onCreate() {
        job = Job()
        uiScope = CoroutineScope(Dispatchers.Main + job)
        ioContext = Dispatchers.IO + job
    }

    fun onDestroy() {
        uiScope.cancel()
        ioContext.cancel()
    }
}