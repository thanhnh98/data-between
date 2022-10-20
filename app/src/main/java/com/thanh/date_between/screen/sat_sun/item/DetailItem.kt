package com.thanh.date_between.screen.sat_sun.item

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.thanh.date_between.R
import com.thanh.date_between.common.adapter.item.RecycleViewItem
import com.thanh.date_between.model.DateModel

class DetailItem(
    private val date: DateModel
): RecycleViewItem<DetailItemVH>() {
    override fun bind(viewHolder: DetailItemVH?) {
        viewHolder?.tvDate?.text = date.toString()
    }

    override fun createViewHolder(context: Context?): RecyclerView.ViewHolder {
        return DetailItemVH(LayoutInflater.from(context).inflate(R.layout.item_weekend_date, null))
    }

}

class DetailItemVH(view: View): RecyclerView.ViewHolder(view) {
    val tvDate: TextView = view.findViewById(R.id.tv_date)
}