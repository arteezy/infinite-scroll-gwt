package com.mardybmGmailCom.client;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.CellList;
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

import java.util.ArrayList;
import java.util.List;

public class MardybmGmailCom implements EntryPoint {
    final private Button starter = new Button("Spawn!");
    final private Label errorLabel = new Label();
    final private Label fname = new Label();

    public void onModuleLoad() {
        CellList<String> cellList = new CellList<String>(new TextCell());
        DudeDataProvider dataProvider = new DudeDataProvider();
        dataProvider.addDataDisplay(cellList);

        SimplePager pager = new SimplePager();
        pager.setDisplay(cellList);

        VerticalPanel vPanel = new VerticalPanel();
        vPanel.add(pager);
        vPanel.add(cellList);

        final Button button = new Button("Dont Click me");
        final Label label = new Label();
        final FlowPanel fl = new FlowPanel();
        fl.add(button);
        fl.add(label);
        fl.add(starter);
        fl.add(fname);
        fl.add(errorLabel);
        final TabLayoutPanel p = new TabLayoutPanel(32, Style.Unit.PX);
        p.setSize("700px", "400px");
        RootPanel.get("panel").add(p);

        p.add(vPanel, "[foo]");
        p.add(new HTML("ipsum"), "[bar]");
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

    private List<DudeProxy> getList(int start, int end) {
        DudeRequestFactory.DudeRequestContext context = createFactory().context();
        final List<DudeProxy> namesList = new ArrayList<DudeProxy>();
        context.getListByRange(start, end).fire(new Receiver<List<DudeProxy>>() {
            @Override
            public void onSuccess(List<DudeProxy> dudeProxyList) {
                namesList.addAll(dudeProxyList);
            }
            @Override
            public void onFailure(ServerFailure error) {
                errorLabel.setText(error.getMessage());
            }
        });
        return namesList;
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

    private class DudeDataProvider extends AsyncDataProvider<String> {
        @Override
        protected void onRangeChanged(HasData<String> display) {
            final Range range = display.getVisibleRange();

            int start = range.getStart();
            int length = range.getLength();

            List<DudeProxy> dudes = getList(start, start + length);
            List<String> newData = new ArrayList<String>();
            for (int i = 0; i < dudes.size(); i++) {
                DudeProxy dude = dudes.get(i);
                newData.add(dude.getFirstName());
            }

            updateRowCount(100, false);
            updateRowData(start, newData);
        }
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
