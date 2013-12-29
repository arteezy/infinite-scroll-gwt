package com.hiringtask.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.hiringtask.client.ui.DudeCellTable;

public class MardybmGmailCom implements EntryPoint {
    final private Label debugLabel = new Label();
    private DudeCellTable dct = new DudeCellTable();

    public void onModuleLoad() {
        final int genNum = 100000;

        VerticalPanel vp = new VerticalPanel();
        vp.setSize("100%", "100%");
        vp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        vp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        FlowPanel fp = new FlowPanel();

        final Button genButton = new Button("Generate");
        final Label genLabel = new Label();
        fp.add(genButton);
        fp.add(genLabel);
        vp.add(fp);

        final TabLayoutPanel p = new TabLayoutPanel(32, Unit.PX);
        p.setSize("600px", "400px");
        RootPanel.get("panel").add(p);
        RootPanel.get("debug").add(debugLabel);

        p.add(vp, "Generator");
        p.add(dct.createWidget(genNum), "Table");
        p.selectTab(0);

        genButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                MardybmGmailComService.App.getInstance().generate(genNum, new GenAsyncCallback(genLabel, genButton));
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
            dct.refreshTable();
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
            button.setEnabled(true);
        }
    }
}
