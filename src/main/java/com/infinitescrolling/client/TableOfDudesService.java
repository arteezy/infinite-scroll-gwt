package com.infinitescrolling.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("TableOfDudesService")
public interface TableOfDudesService extends RemoteService {
    String generate(int num);

    public static class App {
        private static TableOfDudesServiceAsync ourInstance = GWT.create(TableOfDudesService.class);

        public static synchronized TableOfDudesServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
