package com.infinitescrolling.client.ui;

import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.view.client.HasRows;
import com.google.gwt.view.client.Range;

public class RangeLabelPager extends AbstractPager {

    private final HTML label = new HTML();

    public HTML getLabel() {
        return label;
    }

    @Override
    protected void onRangeOrRowCountChanged() {
        HasRows display = getDisplay();
        Range range = display.getVisibleRange();
        int start = range.getStart();
        int end = start + range.getLength();
        label.setText(start + 1 + " - " + end + " : " + display.getRowCount());
    }
}