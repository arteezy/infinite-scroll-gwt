package com.mardybmGmailCom.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("MardybmGmailComService")
public interface MardybmGmailComService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use MardybmGmailComService.App.getInstance() to access static instance of MardybmGmailComServiceAsync
     */
    public static class App {
        private static MardybmGmailComServiceAsync ourInstance = GWT.create(MardybmGmailComService.class);

        public static synchronized MardybmGmailComServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
