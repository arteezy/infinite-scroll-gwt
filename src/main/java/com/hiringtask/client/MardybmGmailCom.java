package com.hiringtask.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.hiringtask.client.ui.DudeCellTable;

import java.util.List;

public class MardybmGmailCom implements EntryPoint {
    final private Button starter = new Button("Spawn!");
    final private Label errorLabel = new Label();
    final private Label fname = new Label();

    public void onModuleLoad() {
        DudeCellTable dct = new DudeCellTable();

        VerticalPanel vp = new VerticalPanel();
        final Button button = new Button("Dont Click me");
        final Label label = new Label();
        vp.add(button);
        vp.add(label);
        vp.add(starter);
        vp.add(fname);

        final TabLayoutPanel p = new TabLayoutPanel(32, Style.Unit.PX);
        p.setSize("600px", "275px");
        RootPanel.get("panel").add(p);
        RootPanel.get("debug").add(errorLabel);

        p.add(new HTML("lol"), "[foo]");
        p.add(dct.create(), "[bar]");
        p.add(new HTML("this"), "[this]");
        p.add(vp, "[what]");
        p.add(new HTML("still"), "[omg]");
        p.selectTab(2);

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

/*    private void spawnDude() {
        DudeRequestFactory.DudeRequestContext context = createFactory().context();
        DudeProxy dude = context.create(DudeProxy.class);
        dude.setFirstName("Ron");
        dude.setLastName("Burgundy");

        context.save(dude).fire(new Receiver<Void>() {
            @Override
            public void onSuccess(Void arg0) {
                fname.setText("Stay classy");
            }
            @Override
            public void onFailure(ServerFailure error) {
                errorLabel.setText(error.getMessage());
            }
        });
    }*/

    private static class HisAsyncCallback implements AsyncCallback<String> {
        private Label label;
        private Button button;

        public HisAsyncCallback(Label label, Button button) {
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
