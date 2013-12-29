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
import com.hiringtask.client.ui.DudeCellTable;

public class TableOfDudes implements EntryPoint {
    private DudeCellTable dct = new DudeCellTable();

    public void onModuleLoad() {
        final int genNum = 100000;
        final Button genButton = new Button("Generate");
        final Label genLabel = new Label();

        VerticalPanel vp = new VerticalPanel();
        vp.setSize("100%", "100%");
        vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        vp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

        FlowPanel fp = new FlowPanel();
        fp.add(genButton);
        fp.add(genLabel);
        vp.add(fp);

        TabLayoutPanel p = new TabLayoutPanel(32, Unit.PX);
        p.setSize("600px", "400px");
        RootPanel.get("panel").add(p);

        p.add(vp, "Generator");
        p.add(dct.createWidget(genNum), "Table");

        p.addSelectionHandler(new SelectionHandler<Integer>() {
            @Override
            public void onSelection(SelectionEvent<Integer> event) {
                if (event.getSelectedItem() == 1) {
                    dct.refreshTable();
                }
            }
        });

        genButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                TableOfDudesService.App.getInstance().generate(genNum, new GenAsyncCallback(genLabel, genButton));
                genLabel.setText("Generating...");
                genButton.setEnabled(false);
                dct.turnOffSorting();
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
