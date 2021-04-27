package com.thanh.date_between.model

data class UpdateModel(
    var content: String,
    var title: String,
    var isForceUpdate: Boolean,
    var url: String,
    var version: String
): BaseModel()