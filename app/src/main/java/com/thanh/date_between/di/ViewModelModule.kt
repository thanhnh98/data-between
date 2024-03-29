package com.thanh.date_between.di

import androidx.lifecycle.ViewModelProvider
import bindViewModel
import com.thanh.date_between.screen.home.viewmodel.HomeViewModel
import com.thanh.date_between.screen.sat_sun.ViewDetailDatesViewModel
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

val viewModelModule = Kodein.Module("VIEWMODEL_MODULE", false) {

    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }

    bindViewModel<HomeViewModel>() with singleton { createHomeViewModel() }
    bindViewModel<ViewDetailDatesViewModel>() with singleton { ViewDetailDatesViewModel() }
}

fun createHomeViewModel(): HomeViewModel{
    return HomeViewModel()
}