package com.thanh.date_between.repo

import com.thanh.date_between.model.UpdateModel
import io.reactivex.Observable
import retrofit2.http.GET

interface Service {
    @GET("update_checking")
    suspend fun getUpdateInfoCoroutine(): UpdateModel
}