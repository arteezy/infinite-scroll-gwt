package com.hiringtask.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hiringtask.client.MardybmGmailComService;
import com.hiringtask.server.model.DudeGenerator;

public class MardybmGmailComServiceImpl extends RemoteServiceServlet implements MardybmGmailComService {
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Serve888r answered: \"Hi!\"";
    }

    public String starter(String msg) {
        DudeGenerator dudeGenerator = new DudeGenerator();
        dudeGenerator.start(1_000_000);
        return "OMFG";
    }
}