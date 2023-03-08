package com.thanh.date_between.app

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.thanh.date_between.common.resources.Resources
import com.thanh.date_between.di.*
import com.thanh.date_between.storage.AppPreferences
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import java.util.*

class DateBetweenApplication: Application(), LifecycleObserver, KodeinAware {
    companion object{
        lateinit var appContext: Context
    }
    override fun onCreate() {
        Resources.init(this)
        AppPreferences.init(this)
        MobileAds.initialize(this){
            Log.e("Inited","init")
        }
        val testDeviceIds = listOf(
            "2D754340943AB2A524632B55EEC48816",
            "692A3CF32D14F5E7FEA5855C9E8DE0D7",
            "B3EEABB8EE11C2BE770B684D95219ECB",
        )
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)
        super.onCreate()
    }

    override val kodein by Kodein.lazy {
        import(androidXModule((this@DateBetweenApplication)))
        import(appModule)
        import(serviceModule)
        import(viewModelModule)
    }
}