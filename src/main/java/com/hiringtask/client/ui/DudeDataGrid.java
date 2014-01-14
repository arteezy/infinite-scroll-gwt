package com.hiringtask.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.HasKeyboardPagingPolicy.KeyboardPagingPolicy;
import com.google.gwt.user.client.ui.HeaderPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.hiringtask.client.DudeProxy;
import com.hiringtask.client.DudeRequestFactory;

import java.util.List;

public class DudeDataGrid {
    private final MyDataGrid<DudeProxy> dataGrid = new MyDataGrid<DudeProxy>();
    private final Label debugLabel = new Label();

    private final int listRange = 500;
    private final int incrementSize = 250;

    private String sortColName;
    private boolean isAscending;

    private boolean updateFlag = true;
    private int lastMaxHeight = 0;
    private int lastScrollPos = 0;
    private int cursor = 0;

    public DataGrid init(int genNum) {
        DudeDataProvider dataProvider = new DudeDataProvider();

        dataGrid.setSize("400px", "300px");
        dataGrid.setStyleName("datagrid");

        TextColumn<DudeProxy> fnameColumn = new TextColumn<DudeProxy>() {
            @Override
            public String getValue(DudeProxy object) {
                return object.getFirstName();
            }
        };
        fnameColumn.setDataStoreName("fName");
        dataGrid.addColumn(fnameColumn, "First Name");
        fnameColumn.setSortable(true);

        TextColumn<DudeProxy> lnameColumn = new TextColumn<DudeProxy>() {
            @Override
            public String getValue(DudeProxy object) {
                return object.getLastName();
            }
        };
        lnameColumn.setDataStoreName("lName");
        dataGrid.addColumn(lnameColumn, "Last Name");
        lnameColumn.setSortable(true);

        dataGrid.setPageSize(listRange);
        dataGrid.setRowCount(genNum);
        dataGrid.setKeyboardPagingPolicy(KeyboardPagingPolicy.INCREASE_RANGE);
        dataGrid.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.DISABLED);

        dataGrid.getScrollPanel().addScrollHandler(new ScrollHandler() {
            public void onScroll(ScrollEvent event) {
                int oldScrollPos = lastScrollPos;
                lastScrollPos = dataGrid.getScrollPanel().getVerticalScrollPosition();
                if (oldScrollPos >= lastScrollPos) return;
                if (dataGrid == null) return;

                int maxScrollTop = dataGrid.getScrollPanel().getWidget().getOffsetHeight() - dataGrid.getScrollPanel().getOffsetHeight();
                int halfIncrementScrollSize = (maxScrollTop - lastMaxHeight) / 2;
                if (lastScrollPos >= (maxScrollTop - halfIncrementScrollSize) && updateFlag) {
                    int newPageSize = Math.min(
                            dataGrid.getVisibleRange().getLength() + incrementSize,
                            dataGrid.getRowCount());
                    lastMaxHeight = maxScrollTop;
                    dataGrid.setVisibleRange(0, newPageSize);
                    updateFlag = false;
                }
                if (maxScrollTop > lastMaxHeight) updateFlag = true;
            }
        });

        AsyncHandler columnSortHandler = new AsyncHandler(dataGrid) {
            @Override
            public void onColumnSort(ColumnSortEvent event) {
                sortColName = dataGrid.getColumnSortList().get(0).getColumn().getDataStoreName();
                isAscending = event.isSortAscending();
                clearState();
                refreshTable();
            }
        };
        dataGrid.addColumnSortHandler(columnSortHandler);

        dataProvider.addDataDisplay(dataGrid);

        return dataGrid;
    }

    private class DudeDataProvider extends AsyncDataProvider<DudeProxy> {
        @Override
        protected void onRangeChanged(HasData<DudeProxy> display) {
            final int length;
            if (cursor == 0) length = listRange;
            else length = incrementSize;

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
        cursor = 0;
        dataGrid.setVisibleRangeAndClearData(new Range(0, listRange), true);
    }

    public void clearState() {
        lastMaxHeight = 0;
        lastScrollPos = 0;
    }

    public void turnOffSorting() {
        dataGrid.getColumnSortList().clear();
        clearState();
        sortColName = null;
    }

    private class MyDataGrid<T> extends DataGrid {
        public ScrollPanel getScrollPanel() {
            HeaderPanel header = (HeaderPanel) getWidget();
            return (ScrollPanel) header.getContentWidget();
        }
    }

    private static DudeRequestFactory createFactory() {
        DudeRequestFactory factory = GWT.create(DudeRequestFactory.class);
        factory.initialize(new SimpleEventBus());
        return factory;
    }
}