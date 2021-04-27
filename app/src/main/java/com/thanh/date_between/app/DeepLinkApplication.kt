package com.thanh.date_between.app

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleObserver
import com.thanh.date_between.common.resources.Resources
import com.thanh.date_between.di.*
import com.thanh.date_between.storage.AppPreferences
import com.thanh.date_between.storage.local_db.database.AppDatabase
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class DeepLinkApplication: Application(), LifecycleObserver, KodeinAware {
    companion object{
        lateinit var appContext: Context
    }
    override fun onCreate() {
        Resources.init(this)
        AppPreferences.init(this)
        AppDatabase.init(this)
        super.onCreate()
    }

    override val kodein by Kodein.lazy {
        import(androidXModule((this@DeepLinkApplication)))
        import(appModule)
        import(serviceModule)
        import(useCaseModule)
        import(viewModelModule)
    }
}