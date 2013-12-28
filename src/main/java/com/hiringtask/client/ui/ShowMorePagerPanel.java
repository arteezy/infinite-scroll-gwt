package com.hiringtask.client.ui;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.AbstractPager;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.HasRows;


public class ShowMorePagerPanel extends AbstractPager {
    private final ScrollPanel scrollable = new ScrollPanel();

    private int incrementSize = 250;
    private int lastScrollPos = 0;
    //private boolean next = true;

    public ShowMorePagerPanel() {
        initWidget(scrollable);

        // Do not let the scrollable take tab focus.
        scrollable.getElement().setTabIndex(-1);

        scrollable.addScrollHandler(new ScrollHandler() {
            public void onScroll(ScrollEvent event) {
                // If scrolling up, ignore the event.
                int oldScrollPos = lastScrollPos;
                lastScrollPos = scrollable.getVerticalScrollPosition();
                //consoleLog("lastScrollPos", String.valueOf(lastScrollPos));
                if (oldScrollPos >= lastScrollPos) {
                    return;
                }
                HasRows display = getDisplay();
                if (display == null) {
                    return;
                }
                int maxScrollTop = scrollable.getWidget().getOffsetHeight() - scrollable.getOffsetHeight();
                //consoleLog("maxScrollTop", String.valueOf(maxScrollTop));
                if (lastScrollPos >= maxScrollTop) {
                    int newPageSize = Math.min(
                            display.getVisibleRange().getLength() + incrementSize,
                            display.getRowCount());
                    display.setVisibleRange(0, newPageSize);
                    //next = false;
                }
                //if (lastScrollPos >= maxScrollTop) {
                //    next = true;
                //}
            }
        });
    }

    native void consoleLog(String what, String message) /*-{
        console.log( what + ": " + message );
    }-*/;

    public int getIncrementSize() {
        return incrementSize;
    }

    @Override
    public void setDisplay(HasRows display) {
        assert display instanceof Widget : "display must extend Widget";
        scrollable.setWidget((Widget) display);
        super.setDisplay(display);
    }

    public void setIncrementSize(int incrementSize) {
        this.incrementSize = incrementSize;
    }

    @Override
    protected void onRangeOrRowCountChanged() {
    }
}