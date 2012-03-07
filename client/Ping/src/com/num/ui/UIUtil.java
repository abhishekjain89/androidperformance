package com.num.ui;

import com.num.ui.adapter.ItemAdapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class UIUtil {

    public static void setListViewHeightBasedOnChildren(ListView listView, ItemAdapter listAdapter) {
        
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            int height = listItem.getMeasuredHeight();
            totalHeight += height;
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        totalHeight+=10;
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
  


}
