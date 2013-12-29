package com.hiringtask.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TableOfDudesServiceAsync {
    void generate(int genNum, AsyncCallback<String> async);
}
