package com.hiringtask.client.ui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.hiringtask.client.TableOfDudesService;

public class TableOfDudes implements EntryPoint {
    private DudeDataGrid dudeDataGrid = new DudeDataGrid();

    public void onModuleLoad() {
        final int genNum = 1000000;
        final Button genButton = new Button("Generate");
        final Label genLabel = new Label();

        DataGrid dataGrid = dudeDataGrid.init(genNum);

        RangeLabelPager pager = new RangeLabelPager();
        pager.setDisplay(dataGrid);

        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setSize("100%", "100%");
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        FlowPanel genPanel = new FlowPanel();
        genPanel.add(genButton);
        genPanel.add(genLabel);
        verticalPanel.add(genPanel);

        FlowPanel tablePanel = new FlowPanel();
        tablePanel.add(dataGrid);
        tablePanel.add(pager.getLabel());

        TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(32, Unit.PX);
        tabLayoutPanel.setSize("600px", "400px");
        RootPanel.get("panel").add(tabLayoutPanel);
        tabLayoutPanel.add(verticalPanel, "Generator");
        tabLayoutPanel.add(tablePanel, "Table");

        tabLayoutPanel.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                if (event.getSelectedItem() == 1) {
                    dudeDataGrid.clearState();
                    dudeDataGrid.refreshTable();
                }
            }
        });

        genButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                TableOfDudesService.App.getInstance().generate(genNum, new GenAsyncCallback(genLabel, genButton));
                genLabel.setText("Generating...");
                genButton.setEnabled(false);
                dudeDataGrid.turnOffSorting();
            }
        });
    }

    private class GenAsyncCallback implements AsyncCallback<String> {
        private Label label;
        private Button button;

        public GenAsyncCallback(Label label, Button button) {
            this.label = label;
            this.button = button;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
            button.setEnabled(true);
            dudeDataGrid.refreshTable();
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
            button.setEnabled(true);
        }
    }
}
