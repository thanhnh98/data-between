package com.thanh.date_between.common.adapter.item.loading;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.thanh.date_between.R;
import com.thanh.date_between.common.NormalizeHelper;
import com.thanh.date_between.common.adapter.item.RecycleViewItem;

public class LoadingRecyclerItem extends RecycleViewItem<LoadingViewHolder>
{
    int height;


    public LoadingRecyclerItem()
    {
        // default
        height = NormalizeHelper.convertDpToPixel(56);
    }

    public LoadingRecyclerItem(int height)
    {
        this.height = height;
    }


    @Override
    public void bind(LoadingViewHolder viewHolder)
    {
        viewHolder.vwContainer.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, height));
    }

    @Override
    public RecyclerView.ViewHolder createViewHolder(Context context)
    {
        return new LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout
                .item_loading_layout, null));
    }
}
