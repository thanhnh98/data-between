package com.thanh.date_between.usecase

import com.thanh.date_between.common.SingleLiveEvent
import com.thanh.date_between.model.*
import com.thanh.date_between.repo.update.UpdateRepo
import com.thanh.date_between.storage.AppPreferences
import com.thanh.date_between.storage.local_db.database.AppDatabase

class UrlUseCaseImpl(val mPreferences: AppPreferences, val mDatabase: AppDatabase, val updateRepo: UpdateRepo): UrlUseCase {

}