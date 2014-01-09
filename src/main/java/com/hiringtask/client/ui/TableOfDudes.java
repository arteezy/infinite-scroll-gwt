package com.hiringtask.client.ui;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.hiringtask.client.TableOfDudesService;

public class TableOfDudes implements EntryPoint {
    private DudeCellTable dudeCellTable = new DudeCellTable();

    public void onModuleLoad() {
        final int genNum = 1000000;
        final Button genButton = new Button("Generate");
        final Label genLabel = new Label();

        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.setSize("100%", "100%");
        verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        verticalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        FlowPanel flowPanel = new FlowPanel();
        flowPanel.add(genButton);
        flowPanel.add(genLabel);
        verticalPanel.add(flowPanel);

        TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(32, Unit.PX);
        tabLayoutPanel.setSize("600px", "400px");
        RootPanel.get("panel").add(tabLayoutPanel);

        tabLayoutPanel.add(verticalPanel, "Generator");
        tabLayoutPanel.add(dudeCellTable.createWidget(genNum), "Table");

        tabLayoutPanel.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                if (event.getSelectedItem() == 1) {
                    dudeCellTable.refreshTable();
                }
            }
        });

        genButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                TableOfDudesService.App.getInstance().generate(genNum, new GenAsyncCallback(genLabel, genButton));
                genLabel.setText("Generating...");
                genButton.setEnabled(false);
                dudeCellTable.turnOffSorting();
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
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
            button.setEnabled(true);
        }
    }
}
