package com.hiringtask.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.hiringtask.client.ui.DudeCellTable;


public class MardybmGmailCom implements EntryPoint {
    final private Button starter = new Button("Spawn!");
    final private Label errorLabel = new Label();
    final private Label fname = new Label();
    DudeCellTable dct = new DudeCellTable();

    public void onModuleLoad() {
        VerticalPanel vp = new VerticalPanel();
        final Button button = new Button("Dont Click me");
        final Label label = new Label();
        vp.add(button);
        vp.add(label);
        vp.add(starter);
        vp.add(fname);

        final TabLayoutPanel p = new TabLayoutPanel(32, Unit.PX);
        p.setSize("600px", "300px");
        RootPanel.get("panel").add(p);
        RootPanel.get("debug").add(errorLabel);

        p.add(new HTML("lol"), "Generator");
        p.add(dct.create(), "Table");
        p.add(vp, "Buttons");
        p.selectTab(0);

        starter.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                //spawnDude();
            }
        });

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (label.getText().equals("")) {
                    MardybmGmailComService.App.getInstance().getMessage("Goodbye, Gorld!", new MyAsyncCallback(label));
                    MardybmGmailComService.App.getInstance().starter("OMG", new HisAsyncCallback(label, button));
                    button.setEnabled(false);
                } else {
                    label.setText("");
                }
            }
        });
    }

    private class HisAsyncCallback implements AsyncCallback<String> {
        private Label label;
        private Button button;

        public HisAsyncCallback(Label label, Button button) {
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

    private class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
