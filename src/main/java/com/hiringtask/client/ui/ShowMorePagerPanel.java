package com.hiringtask.client.ui;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasRows;


public class ShowMorePagerPanel extends AbstractPager {
    private static final int DEFAULT_INCREMENT = 100;

    private int incrementSize = DEFAULT_INCREMENT;

    private int lastScrollPos = 0;

    private final ScrollPanel scrollable = new ScrollPanel();

    public ShowMorePagerPanel() {
        initWidget(scrollable);

        // Do not let the scrollable take tab focus.
        scrollable.getElement().setTabIndex(-1);

        scrollable.addScrollHandler(new ScrollHandler() {
            public void onScroll(ScrollEvent event) {
                // If scrolling up, ignore the event.
                int oldScrollPos = lastScrollPos;
                lastScrollPos = scrollable.getVerticalScrollPosition();
                if (oldScrollPos >= lastScrollPos) {
                    return;
                }
                HasRows display = getDisplay();
                if (display == null) {
                    return;
                }
                int maxScrollTop = scrollable.getWidget().getOffsetHeight() - scrollable.getOffsetHeight();
                if (lastScrollPos >= maxScrollTop) {
                    // We are near the end, so increase the page size.
                    int newPageSize = Math.min(
                            display.getVisibleRange().getLength() + incrementSize,
                            display.getRowCount());
                    display.setVisibleRange(0, newPageSize);
                }
            }
        });
    }

    public int getIncrementSize() {
        return incrementSize;
    }

    @Override
    public void setDisplay(HasRows display) {
        assert display instanceof Widget : "display must extend Widget";
        scrollable.setWidget((Widget) display);
        super.setDisplay(display);
    }

    /**
     * Set the number of rows by which the range is increased when the scrollbar
     * reaches the bottom.
     *
     * @param incrementSize the incremental number of rows
     */
    public void setIncrementSize(int incrementSize) {
        this.incrementSize = incrementSize;
    }

    @Override
    protected void onRangeOrRowCountChanged() {
    }
}