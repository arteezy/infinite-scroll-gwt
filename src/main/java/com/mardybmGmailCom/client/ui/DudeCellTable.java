package com.mardybmGmailCom.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.mardybmGmailCom.client.DudeProxy;


public class DudeCellTable {

    interface Binder extends UiBinder<Widget, DudeCellTable> {
    }

    @UiField
    ShowMorePagerPanel pagerPanel;

    @UiField
    RangeLabelPager rangeLabelPager;

    public Widget create(AsyncDataProvider dataProvider) {
        CellTable<DudeProxy> cellTable = new CellTable<DudeProxy>();

        TextColumn<DudeProxy> fnameColumn = new TextColumn<DudeProxy>() {
            @Override
            public String getValue(DudeProxy object) {
                return object.getFirstName();
            }
        };
        cellTable.addColumn(fnameColumn, "First Name");

        TextColumn<DudeProxy> lnameColumn = new TextColumn<DudeProxy>() {
            @Override
            public String getValue(DudeProxy object) {
                return object.getLastName();
            }
        };
        cellTable.addColumn(lnameColumn, "Last Name");

        cellTable.setPageSize(50);
        cellTable.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
        cellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

        final SingleSelectionModel<DudeProxy> selectionModel = new SingleSelectionModel<DudeProxy>();
        cellTable.setSelectionModel(selectionModel);

        Binder uiBinder = GWT.create(Binder.class);
        Widget widget = uiBinder.createAndBindUi(this);

        dataProvider.addDataDisplay(cellTable);

        // Set the cellList as the display of the pagers. This example has two
        // pagers. pagerPanel is a scrollable pager that extends the range when the
        // user scrolls to the bottom. rangeLabelPager is a pager that displays the
        // current range, but does not have any controls to change the range.
        pagerPanel.setDisplay(cellTable);
        rangeLabelPager.setDisplay(cellTable);

        return widget;
    }
}