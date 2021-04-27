package com.thanh.date_between.common.adapter;

import android.os.Parcelable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thanh.date_between.common.adapter.item.RecycleViewChildHolder;
import com.thanh.date_between.common.adapter.item.RecycleViewItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private List<RecycleViewItem> mItems;
    private Map<Integer, RecycleViewItem> mPrototypeItem;
    private Map<Integer, Parcelable> mStateSaved;

    public RecycleViewAdapter()
    {
        mItems = new ArrayList<>();
        mPrototypeItem = new HashMap<>();
        mStateSaved = new HashMap<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return mPrototypeItem.get(viewType).createViewHolder(parent.getContext());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        mItems.get(position).bind(holder);

        //Restore state for nested recycleview
        if(holder instanceof RecycleViewChildHolder
                && mStateSaved.containsKey(holder.getLayoutPosition())
                && mStateSaved.get(holder.getLayoutPosition()) != null){
            ((RecycleViewChildHolder) holder).childRecycleView.getLayoutManager().onRestoreInstanceState(mStateSaved.get(holder.getLayoutPosition()));
        }
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        //Save state for nested recycleview
        if(holder instanceof RecycleViewChildHolder){
            mStateSaved.put(holder.getLayoutPosition(), ((RecycleViewChildHolder) holder).childRecycleView.getLayoutManager().onSaveInstanceState());
        }
    }

    public RecyclerView.Adapter getAdapter(){
        return this;
    }
    @Override
    public int getItemViewType(int position)
    {
        return mItems.get(position).getType();
    }

    @Override
    public int getItemCount()
    {
        return mItems.size();
    }

    public void append(RecycleViewItem item)
    {
        append(mItems.size(), item);
    }

    public void append(int position, RecycleViewItem item)
    {
        mItems.add(position, item);
        addPrototypeItem(item);
        notifyItemInserted(position);
    }

    public void append(List<RecycleViewItem> items)
    {
        append(mItems.size(), items);
    }

    public void append(int position, List<RecycleViewItem> items)
    {
        mItems.addAll(position, items);
        addPrototypeItem(items);
        notifyItemRangeInserted(position, items.size());
    }

    public void remove(int position)
    {
        validPosition(position);

        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void remove(int position, int length)
    {
        if (length == 1)
        {
            remove(position);
        }
        else
        {
            List<RecycleViewItem> items = new ArrayList<>();
            for (int i = 0; i < mItems.size(); i++)
            {
                if (i < position || i >= position + length)
                    items.add(mItems.get(i));
            }
            mItems = items;
            notifyItemRangeRemoved(position, length);
        }
    }

    public void replace(int position, RecycleViewItem item)
    {
        validPosition(position);

        mItems.remove(position);
        mItems.add(position, item);
        addPrototypeItem(item);

        notifyItemChanged(position);
    }

    public void refresh(List<RecycleViewItem> items)
    {
        mItems = items;
        mPrototypeItem.clear();
        addPrototypeItem(items);
        notifyDataSetChanged();
    }

    public int getLength()
    {
        return mItems.size();
    }

    private void addPrototypeItem(RecycleViewItem item)
    {
        if (!mPrototypeItem.containsKey(item))
        {
            mPrototypeItem.put(item.getType(), item);
        }
    }

    private void addPrototypeItem(List<RecycleViewItem> items)
    {
        for (RecycleViewItem item : items)
        {
            if (!mPrototypeItem.containsKey(item))
            {
                mPrototypeItem.put(item.getType(), item);
            }
        }
    }


    public void validPosition(int position)
    {
        if (position < 0 || position > mItems.size())
        {
            throw new IllegalStateException(String.format("Recycler-Adapter: Out off range: " +
                    "pos %s length ", position, mItems.size()));
        }
    }


}
