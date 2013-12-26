package com.mardybmGmailCom.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mardybmGmailCom.client.MardybmGmailComService;
import com.mardybmGmailCom.server.model.DudeGenerator;

public class MardybmGmailComServiceImpl extends RemoteServiceServlet implements MardybmGmailComService {
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Serve888r answered: \"Hi!\"";
    }

    public String starter(String msg) {
        DudeGenerator dudeGenerator = new DudeGenerator();
        dudeGenerator.start();
        return "OMFG";
    }
}