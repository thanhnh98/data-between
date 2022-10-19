package com.thanh.date_between.screen.edit_holiday.item

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.thanh.date_between.R
import com.thanh.date_between.common.adapter.item.RecycleViewItem
import com.thanh.date_between.extension.onClick
import com.thanh.date_between.model.DateModel

class HolidayItem(
    private val date: DateModel,
    private val onDeleteItem: (position: Int, date: DateModel) -> Unit
) : RecycleViewItem<HolidayVH>(){
    override fun bind(viewHolder: HolidayVH) {
        viewHolder.tvDate.text = date.getDateHolidayFormat()
        viewHolder.tvDescription.text = date.dayInfo
        viewHolder.imgDelete.onClick {
            onDeleteItem.invoke(viewHolder.adapterPosition, date)
        }
    }

    override fun createViewHolder(context: Context?): RecyclerView.ViewHolder {
       return HolidayVH(LayoutInflater.from(context).inflate(R.layout.item_holiday, null))
    }
}