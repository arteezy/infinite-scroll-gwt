package com.hiringtask.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MardybmGmailComServiceAsync {
    void generate(int genNum, AsyncCallback<String> async);
}
