package com.thanh.date_between.screen.edit_holiday.item

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_holiday.view.*

class HolidayVH(view: View): RecyclerView.ViewHolder(view) {
    var tvDate: TextView = view.tv_date
    var tvDescription: TextView = view.tv_description
    var imgDelete: ImageView = view.imgDelete
}