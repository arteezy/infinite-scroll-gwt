package com.mardybmGmailCom.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.CellTable;
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
import com.mardybmGmailCom.client.ui.DudeCellTable;

import java.util.List;

public class MardybmGmailCom implements EntryPoint {
    final private Button starter = new Button("Spawn!");
    final private Label errorLabel = new Label();
    final private Label fname = new Label();

    public void onModuleLoad() {
        CellTable<DudeProxy> table = new CellTable<DudeProxy>();
        DudeDataProvider dataProvider = new DudeDataProvider();
        //dataProvider.addDataDisplay(table);

        DudeCellTable cwcl = new DudeCellTable();

        TextColumn<DudeProxy> fnameColumn = new TextColumn<DudeProxy>() {
            @Override
            public String getValue(DudeProxy object) {
                return object.getFirstName();
            }
        };
        table.addColumn(fnameColumn, "First Name");

        TextColumn<DudeProxy> lnameColumn = new TextColumn<DudeProxy>() {
            @Override
            public String getValue(DudeProxy object) {
                return object.getLastName();
            }
        };
        table.addColumn(lnameColumn, "Last Name");

        SimplePager pager = new SimplePager();
        pager.setDisplay(table);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(table);
        vPanel.add(pager);

        final Button button = new Button("Dont Click me");
        final Label label = new Label();
        final FlowPanel fl = new FlowPanel();
        fl.add(button);
        fl.add(label);
        fl.add(starter);
        fl.add(fname);
        fl.add(errorLabel);

        final TabLayoutPanel p = new TabLayoutPanel(32, Style.Unit.PX);
        p.setSize("600px", "400px");
        RootPanel.get("panel").add(p);

        p.add(vPanel, "[foo]");
        p.add(cwcl.create(dataProvider), "[bar]");
        p.add(new HTML("this"), "[this]");
        p.add(fl, "[what]");
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

    private class DudeDataProvider extends AsyncDataProvider<DudeProxy> {
        @Override
        protected void onRangeChanged(HasData<DudeProxy> display) {
            final Range range = display.getVisibleRange();
            int start = range.getStart();
            int length = range.getLength();


            final int st = start;

            DudeRequestFactory.DudeRequestContext context = createFactory().context();
            context.getListByRange(start, start + length).fire(new Receiver<List<DudeProxy>>() {
                @Override
                public void onSuccess(List<DudeProxy> dudeProxyList) {
                    updateRowData(st, dudeProxyList);
                }
                @Override
                public void onFailure(ServerFailure error) {
                    errorLabel.setText(error.getMessage());
                }
            });
        }
    }

    private void spawnDude() {
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
    }

    private static DudeRequestFactory createFactory() {
        DudeRequestFactory factory = GWT.create(DudeRequestFactory.class);
        factory.initialize(new SimpleEventBus());
        return factory;
    }

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
