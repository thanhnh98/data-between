package com.thanh.date_between.repo.update

import com.thanh.date_between.model.UpdateModel
import com.thanh.date_between.repo.Service

class UpdateRepoImpl(private val service: Service): UpdateRepo {
    override suspend fun getUpdateInfo(): UpdateModel {
        return service.getUpdateInfoCoroutine()
    }
}