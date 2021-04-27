package com.thanh.date_between.di

import androidx.lifecycle.ViewModelProvider
import bindViewModel
import com.thanh.date_between.common.base.BaseViewModel
import com.thanh.date_between.usecase.UrlUseCase
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val viewModelModule = Kodein.Module("VIEWMODEL_MODULE", false) {

    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }

    bindViewModel<BaseViewModel>() with provider { createHomeViewModel() }
}

fun createHomeViewModel(): BaseViewModel{
    return BaseViewModel()
}