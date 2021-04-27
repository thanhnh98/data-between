package com.thanh.date_between.common.adapter.item.loading;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.thanh.date_between.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder
{
    View vwContainer;


    public LoadingViewHolder(View itemView)
    {
        super(itemView);
        vwContainer = itemView.findViewById(R.id.vw_container);
    }
}
