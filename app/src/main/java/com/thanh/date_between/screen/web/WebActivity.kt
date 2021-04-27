package com.thanh.date_between.screen.web

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thanh.date_between.R
import kotlinx.android.synthetic.main.activity_webview.*

class WebActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        var url: String = intent?.extras?.getString("URL")?:""

        if (!url.contains("http")){
            url = "https://$url"
        }
        wv?.loadUrl(url)
    }
}