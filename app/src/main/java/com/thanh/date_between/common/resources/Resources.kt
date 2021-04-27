package com.thanh.date_between.common.resources

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.res.Resources


class Resources {
    companion object{
        private lateinit var context: Context
        fun init(context: Context){
                this.context = context.applicationContext
        }

        fun getContext(): Context{
            return this.context
        }

        fun getResources(): Resources{
            return context.resources
        }

        fun getString(id: Int): String{
            return getResources().getString(id)
        }

        fun getClipboard(): String{
            val clipboard = getContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            return clipboard.primaryClip?.getItemAt(0)?.text.toString()
        }

        fun copyToClipboard(url: String){
            val clipboard =
                getContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", url)
            clipboard.setPrimaryClip(clip)
        }
    }
}