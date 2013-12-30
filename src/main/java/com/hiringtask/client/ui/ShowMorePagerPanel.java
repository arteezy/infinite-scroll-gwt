package com.hiringtask.client.ui;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasRows;

public class ShowMorePagerPanel extends AbstractPager {
    private final ScrollPanel scrollable = new ScrollPanel();

    private boolean updateFlag = true;
    private int incrementSize = 250;
    private int lastMaxHeight = 0;
    private int lastScrollPos = 0;

    public ShowMorePagerPanel() {
        initWidget(scrollable);

        scrollable.getElement().setTabIndex(-1);

        scrollable.addScrollHandler(new ScrollHandler() {
            public void onScroll(ScrollEvent event) {
                int oldScrollPos = lastScrollPos;
                lastScrollPos = scrollable.getVerticalScrollPosition();
                if (oldScrollPos >= lastScrollPos) return;

                HasRows display = getDisplay();
                if (display == null) return;

                int maxScrollTop = scrollable.getWidget().getOffsetHeight() - scrollable.getOffsetHeight();
                int halfIncrementScrollSize = (maxScrollTop - lastMaxHeight) / 2;
                if (lastScrollPos >= (maxScrollTop - halfIncrementScrollSize) && updateFlag) {
                    int newPageSize = Math.min(
                        display.getVisibleRange().getLength() + incrementSize,
                        display.getRowCount());
                    lastMaxHeight = maxScrollTop;
                    display.setVisibleRange(0, newPageSize);
                    updateFlag = false;
                }
                if (maxScrollTop > lastMaxHeight) updateFlag = true;
            }
        });
    }

    @Override
    public void setDisplay(HasRows display) {
        assert display instanceof Widget : "display must extend Widget";
        scrollable.setWidget((Widget) display);
        super.setDisplay(display);
    }

    public void clearState() {
        lastMaxHeight = 0;
        lastScrollPos = 0;
    }

    public int getIncrementSize() {
        return incrementSize;
    }

    public void setIncrementSize(int incrementSize) {
        this.incrementSize = incrementSize;
    }

    @Override
    protected void onRangeOrRowCountChanged() {
    }
}