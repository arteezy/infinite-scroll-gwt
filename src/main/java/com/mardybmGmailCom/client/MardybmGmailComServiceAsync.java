package com.mardybmGmailCom.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MardybmGmailComServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);

    void starter(String msg, AsyncCallback<String> async);
}
