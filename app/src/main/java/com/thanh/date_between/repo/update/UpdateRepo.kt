package com.thanh.date_between.repo.update

import com.thanh.date_between.model.UpdateModel

interface UpdateRepo {
    suspend fun getUpdateInfo(): UpdateModel
}