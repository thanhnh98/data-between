package com.thanh.date_between.common.adapter.item.spacing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.thanh.date_between.R;
import com.thanh.date_between.common.adapter.item.RecycleViewItem;

public class SpacingRecyclerItem extends RecycleViewItem<SpacingViewHolder>
{
    int height;
    int width;
    int backgroundColor;
    boolean isWidthSpacing;


    public SpacingRecyclerItem(int height, int backgroundColor)
    {
        this.height = height;
        this.isWidthSpacing = false;
        this.backgroundColor = backgroundColor;
    }

    public SpacingRecyclerItem(int width, int backgroundColor, boolean isWidthSpacing)
    {
        this.width = width;
        this.isWidthSpacing = true;
        this.backgroundColor = backgroundColor;
    }


    @Override
    public void bind(SpacingViewHolder viewHolder)
    {
        if(!isWidthSpacing){
            viewHolder.vwSpacing.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, height));
        }else{
            viewHolder.vwSpacing.setLayoutParams(new LinearLayout.LayoutParams(
                    width, ViewGroup.LayoutParams.MATCH_PARENT));
        }
        viewHolder.vwSpacing.setBackgroundColor(backgroundColor);
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(Context context)
    {
        return new SpacingViewHolder(LayoutInflater.from(context).inflate(R.layout
                .item_spacing_layout, null));
    }
}
