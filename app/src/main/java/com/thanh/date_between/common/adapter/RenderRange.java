package com.thanh.date_between.common.adapter;


public class RenderRange
{
    private int position;
    private int length;

    public RenderRange(int position, int length)
    {
        this.setPosition(position);
        this.setLength(length);
    }

    public int getPosition()
    {
        return position;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }
}
