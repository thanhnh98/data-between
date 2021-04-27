package com.thanh.date_between.di

import com.thanh.date_between.repo.update.UpdateRepo
import com.thanh.date_between.storage.AppPreferences
import com.thanh.date_between.storage.local_db.database.AppDatabase
import com.thanh.date_between.usecase.UrlUseCase
import com.thanh.date_between.usecase.UrlUseCaseImpl
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val useCaseModule = Kodein.Module("USECASE_MODULE", false) {
    bind<UrlUseCase>() with provider {
        createUrlUseCase(instance(), instance(), instance())
    }
}

fun createUrlUseCase(preferences: AppPreferences, database: AppDatabase, updateRepo: UpdateRepo): UrlUseCase = UrlUseCaseImpl(preferences, database, updateRepo)