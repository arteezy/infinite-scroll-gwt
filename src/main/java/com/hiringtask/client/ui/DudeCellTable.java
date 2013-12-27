package com.hiringtask.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
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

    interface Binder extends UiBinder<Widget, DudeCellTable> {
    }
    final private Label errorLabel = new Label();

    private int cursor = 0;
    private int scursor = 0;
    private String sortedColumn = null;
    private boolean isAscending;

    @UiField
    ShowMorePagerPanel pagerPanel;

    @UiField
    RangeLabelPager rangeLabelPager;

    public Widget create() {
        final CellTable<DudeProxy> cellTable = new CellTable<DudeProxy>();

        final DudeDataProvider dataProvider = new DudeDataProvider();

        TextColumn<DudeProxy> fnameColumn = new TextColumn<DudeProxy>() {
            @Override
            public String getValue(DudeProxy object) {
                return object.getFirstName();
            }
        };
        fnameColumn.setDataStoreName("first_name");
        cellTable.addColumn(fnameColumn, "First Name");
        fnameColumn.setSortable(true);

        TextColumn<DudeProxy> lnameColumn = new TextColumn<DudeProxy>() {
            @Override
            public String getValue(DudeProxy object) {
                return object.getLastName();
            }
        };
        lnameColumn.setDataStoreName("last_name");
        cellTable.addColumn(lnameColumn, "Last Name");
        lnameColumn.setSortable(true);

        cellTable.setPageSize(1000);
        cellTable.setRowCount(100000);
        cellTable.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
        cellTable.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.BOUND_TO_SELECTION);

        final SingleSelectionModel<DudeProxy> selectionModel = new SingleSelectionModel<DudeProxy>();
        cellTable.setSelectionModel(selectionModel);

        Binder uiBinder = GWT.create(Binder.class);
        Widget widget = uiBinder.createAndBindUi(this);

        dataProvider.addDataDisplay(cellTable);


        AsyncHandler columnSortHandler = new AsyncHandler(cellTable) {
            @Override
            public void onColumnSort(ColumnSortEvent event) {
                sortedColumn = cellTable.getColumnSortList().get(0).getColumn().getDataStoreName();
                isAscending = event.isSortAscending();
                cursor = 0;
                RangeChangeEvent.fire(cellTable, new Range(0, 1000));
            }
        };
        cellTable.addColumnSortHandler(columnSortHandler);

        pagerPanel.setDisplay(cellTable);
        rangeLabelPager.setDisplay(cellTable);

        return widget;
    }

    private static DudeRequestFactory createFactory() {
        DudeRequestFactory factory = GWT.create(DudeRequestFactory.class);
        factory.initialize(new SimpleEventBus());
        return factory;
    }

    private class DudeDataProvider extends AsyncDataProvider<DudeProxy> {
        @Override
        protected void onRangeChanged(HasData<DudeProxy> display) {
            final int length;
            if (cursor == 0) length = 1000;
            else length = 500;

            errorLabel.setText(String.valueOf(cursor) + " - " + String.valueOf(cursor + length));
            
            DudeRequestFactory.DudeRequestContext context = createFactory().context();
            context.getSortedListByRange(cursor, cursor + length, sortedColumn, isAscending)
                .fire(new Receiver<List<DudeProxy>>() {
                    @Override
                    public void onSuccess(List<DudeProxy> dudeProxyList) {
                        updateRowData(cursor, dudeProxyList);
                        cursor = cursor + length;
                    }
                    @Override
                    public void onFailure(ServerFailure error) {
                        errorLabel.setText(error.getMessage());
                    }
                });
        }
    }
}