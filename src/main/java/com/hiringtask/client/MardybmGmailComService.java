package com.hiringtask.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("MardybmGmailComService")
public interface MardybmGmailComService extends RemoteService {
    String generate(int num);

    public static class App {
        private static MardybmGmailComServiceAsync ourInstance = GWT.create(MardybmGmailComService.class);

        public static synchronized MardybmGmailComServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
