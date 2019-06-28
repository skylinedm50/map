package com.map_movil.map_movil.decorator;

import android.view.View;

public interface HeaderInterface
{
    int getHeaderPositionForItem(int itemPosition);
    int getHeaderLayout(int headerPosition);
    void bindHeaderData(View header, int headerPosition);
    boolean isHeader(int itemPosition);

}
