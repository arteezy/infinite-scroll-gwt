package com.hiringtask.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.*;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.hiringtask.client.DudeProxy;
import com.hiringtask.client.DudeRequestFactory;

import java.util.List;

public class DudeCellTable {

    interface Binder extends UiBinder<Widget, DudeCellTable> {}

    private CellTable<DudeProxy> cellTable = new CellTable<DudeProxy>();

    private final Label debugLabel = new Label();
    private final int listRange = 500;
    private String sortColName = null;
    private boolean isAscending;
    private int cursor = 0;

    @UiField
    ShowMorePagerPanel pagerPanel;

    @UiField
    RangeLabelPager rangeLabelPager;

    public Widget createWidget(int genNum) {
        DudeDataProvider dataProvider = new DudeDataProvider();

        TextColumn<DudeProxy> fnameColumn = new TextColumn<DudeProxy>() {
            @Override
            public String getValue(DudeProxy object) {
                return object.getFirstName();
            }
        };
        fnameColumn.setDataStoreName("fName");
        cellTable.setColumnWidth(fnameColumn, "200px");
        cellTable.addColumn(fnameColumn, "First Name");
        fnameColumn.setSortable(true);

        TextColumn<DudeProxy> lnameColumn = new TextColumn<DudeProxy>() {
            @Override
            public String getValue(DudeProxy object) {
                return object.getLastName();
            }
        };
        lnameColumn.setDataStoreName("lName");
        cellTable.setColumnWidth(lnameColumn, "200px");
        cellTable.addColumn(lnameColumn, "Last Name");
        lnameColumn.setSortable(true);

        cellTable.setPageSize(listRange);
        cellTable.setRowCount(genNum);
        cellTable.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);

        Binder uiBinder = GWT.create(Binder.class);
        Widget widget = uiBinder.createAndBindUi(this);

        AsyncHandler columnSortHandler = new AsyncHandler(cellTable) {
            @Override
            public void onColumnSort(ColumnSortEvent event) {
                sortColName = cellTable.getColumnSortList().get(0).getColumn().getDataStoreName();
                isAscending = event.isSortAscending();
                pagerPanel.clearState();
                refreshTable();
            }
        };
        cellTable.addColumnSortHandler(columnSortHandler);

        dataProvider.addDataDisplay(cellTable);

        pagerPanel.setDisplay(cellTable);
        rangeLabelPager.setDisplay(cellTable);

        return widget;
    }

    private class DudeDataProvider extends AsyncDataProvider<DudeProxy> {
        @Override
        protected void onRangeChanged(HasData<DudeProxy> display) {
            final int length;
            if (cursor == 0) length = listRange;
            else length = pagerPanel.getIncrementSize();

            DudeRequestFactory.DudeRequestContext context = createFactory().context();
            context.getSortedListByRange(cursor, cursor + length, sortColName, isAscending)
                .fire(new Receiver<List<DudeProxy>>() {
                    @Override
                    public void onSuccess(List<DudeProxy> dudeProxyList) {
                        updateRowData(cursor, dudeProxyList);
                        cursor = cursor + length;
                    }
                    @Override
                    public void onFailure(ServerFailure error) {
                        debugLabel.setText(error.getMessage());
                    }
                });
        }
    }

    public void refreshTable() {
        cellTable.setVisibleRangeAndClearData(new Range(0, listRange), true);
        cursor = 0;
    }

    public void turnOffSorting() {
        cellTable.getColumnSortList().clear();
        pagerPanel.clearState();
        sortColName = null;
    }

    private static DudeRequestFactory createFactory() {
        DudeRequestFactory factory = GWT.create(DudeRequestFactory.class);
        factory.initialize(new SimpleEventBus());
        return factory;
    }
}