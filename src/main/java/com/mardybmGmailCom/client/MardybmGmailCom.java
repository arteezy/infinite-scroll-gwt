package com.mardybmGmailCom.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MardybmGmailCom implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button button = new Button("Dont Click me");
        final Label label = new Label();

        final TabLayoutPanel p = new TabLayoutPanel(32, Style.Unit.PX);
        p.setSize("700px", "400px");
        RootPanel.get("panel").add(p);

        p.add(new HTML("lorem"), "[foo]");
        p.add(new HTML("ipsum"), "[bar]");
        p.add(new HTML("this"), "[this]");
        p.add(button, "[what]");
        p.add(label, "[omg]");

        p.selectTab(2);

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (label.getText().equals("")) {
                    MardybmGmailComService.App.getInstance().getMessage("Goodbye, Gorld!", new MyAsyncCallback(label));
                } else {
                    label.setText("");
                }
            }
        });
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
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
