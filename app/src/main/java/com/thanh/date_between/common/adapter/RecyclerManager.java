package com.thanh.date_between.common.adapter;

import com.thanh.date_between.common.adapter.item.RecycleViewItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerManager<T>
{
    // Manage group type
    private List<T> mClusters;

    // Manage map group range
    private Map<T, RenderRange> mMapRenderRange;

    private RecycleViewAdapter mAdapter;


    public RecyclerManager()
    {
        mClusters = new ArrayList<>();
        mAdapter = new RecycleViewAdapter();
        mMapRenderRange = new HashMap<>();
    }

    /*
    *  Public method
    * */
    public void recycle()
    {
        mClusters.clear();
        mMapRenderRange.clear();
    }

    public RecycleViewAdapter getAdapter()
    {
        return mAdapter;
    }

    public void addCluster(T t)
    {
        mClusters.add(t);
        mMapRenderRange.put(t, new RenderRange(0, 0));
        calculateMapRenderRange();
    }

    /*
    *  Method notify change recycle
    *  Position: position item in cluster
    * */
    public void append(T t, RecycleViewItem item)
    {
        RenderRange renderRange = mMapRenderRange.get(t);
        append(t, renderRange.getLength(), item);
    }

    public void append(T t, int position, RecycleViewItem item)
    {
        RenderRange renderRange = mMapRenderRange.get(t);

        validPosition(renderRange, position);

        // find position append
        position = position + renderRange.getPosition();

        // update render range
        int length = renderRange.getLength() + 1;
        mMapRenderRange.put(t, new RenderRange(renderRange.getPosition(), length));

        // notify
        mAdapter.append(position, item);

        calculateMapRenderRange();
    }

    public void append(T t, List<RecycleViewItem> items)
    {
        RenderRange renderRange = mMapRenderRange.get(t);
        append(t, renderRange.getLength(), items);
    }

    public void append(T t, int position, List<RecycleViewItem> items)
    {
        RenderRange renderRange = mMapRenderRange.get(t);

        validPosition(renderRange, position);

        // find position append
        position = position + renderRange.getPosition();

        // update render range
        int length = renderRange.getLength() + items.size();
        mMapRenderRange.put(t, new RenderRange(renderRange.getPosition(), length));

        // notify
        mAdapter.append(position, items);

        calculateMapRenderRange();
    }

    public void update(T t, int position)
    {
        RenderRange renderRange = mMapRenderRange.get(t);
        position = position + renderRange.getPosition();
        mAdapter.notifyItemChanged(position);
    }

    public void update(T t, int position, RecycleViewItem item)
    {
        replace(t, position, item);
    }

    public void remove(T t, int position)
    {
        if (!mClusters.contains(t) || mMapRenderRange.get(t).getLength()==0)
        {
            return;
        }

        RenderRange renderRange = mMapRenderRange.get(t);

        validPosition(renderRange, position);

        // this delete last item -> just remove cluster viewChangeistener
        int length = renderRange.getLength() - 1;
        if (length < 0)
            length = 0;
        mMapRenderRange.put(t, new RenderRange(renderRange.getPosition(), length));

        position = position + renderRange.getPosition();
        mAdapter.remove(position);

        calculateMapRenderRange();
    }

    public void replace(T t, int position, RecycleViewItem item)
    {
        // first remove old
        RenderRange renderRange = mMapRenderRange.get(t);
        validPosition(renderRange, position);

        position = position + renderRange.getPosition();
        mAdapter.replace(position, item);
    }

    public void replace(T t, RecycleViewItem item)
    {
        List<RecycleViewItem> items = new ArrayList<>();
        items.add(item);
        replace(t, items);
    }

    public void replaceAndUpdateIfExisted(T t, RecycleViewItem item)
    {
        if (getLenghtOfCluster( t) > 0)
            update(t, 0, item);
        else
            replace(t, item);
    }

    public void replace(T t, List<RecycleViewItem> items)
    {
        // first remove old
        RenderRange renderRange = mMapRenderRange.get(t);
        if (renderRange.getLength() > 0)
            mAdapter.remove(renderRange.getPosition(), renderRange.getLength());

        mMapRenderRange.put(t, new RenderRange(renderRange.getPosition(), items.size()));
        mAdapter.append(renderRange.getPosition(), items);

        calculateMapRenderRange();
    }

    /**/
    private void calculateMapRenderRange()
    {
        int position = 0;
        for (T t : mClusters)
        {
            int length = mMapRenderRange.get(t).getLength();
            mMapRenderRange.put(t, new RenderRange(position, length));

            position += length;
        }
    }

    public void validPosition(RenderRange renderRange, int position)
    {
        if (position < 0 || position > renderRange.getLength())
        {
            throw new IllegalStateException(String.format("Recycler-DataManager: Out off range: " +
                    "pos %s length %d", position, renderRange.getLength()));
        }
    }

    public int getStartPosition(T t)
    {
        int pos = -1;

        RenderRange renderRange = mMapRenderRange.get(t);
        if (renderRange != null)
            pos = renderRange.getPosition();

        return pos;
    }
    public boolean existPosition(String cluster, int position){
        return (mMapRenderRange.get(cluster).getLength() > position);
    }

    public boolean existPosition(Object cluster, int position){
        return (mMapRenderRange.get(cluster).getLength() > position);
    }
    public int getLenghtOfCluster(String cluster){
        return mMapRenderRange.get(cluster).getLength();
    }

    public int getLenghtOfCluster(T cluster){
        return mMapRenderRange.get(cluster).getLength();
    }
}
