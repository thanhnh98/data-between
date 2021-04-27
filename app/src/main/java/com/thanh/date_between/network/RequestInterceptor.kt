package com.thanh.date_between.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {

    private val mHeaders: HashMap<String, String> by lazy {
        HashMap<String, String>()
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        if (mHeaders.size > 0) {
            val keys: MutableSet<String> = mHeaders.keys

            for (key in keys)
                mHeaders[key]?.apply {
                    builder.header(key, this)
                }
        }

        return chain.proceed(builder.build())
    }

    fun addHeader(key: String, value: String) {
        mHeaders[key] = value
    }

    fun removeHeader(key: String?) {
        mHeaders.remove(key)
    }
}