package com.thanh.date_between.common.adapter.item.spacing;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.thanh.date_between.R;

public class SpacingViewHolder extends RecyclerView.ViewHolder
{
    View vwSpacing;


    public SpacingViewHolder(View itemView)
    {
        super(itemView);

        vwSpacing = itemView.findViewById(R.id.vw_spacing);
    }
}
