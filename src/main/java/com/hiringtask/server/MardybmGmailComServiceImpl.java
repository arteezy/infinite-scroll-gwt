package com.hiringtask.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hiringtask.client.MardybmGmailComService;
import com.hiringtask.server.model.DudeGenerator;

public class MardybmGmailComServiceImpl extends RemoteServiceServlet implements MardybmGmailComService {

    public String generate(int genNum) {
        DudeGenerator dudeGenerator = new DudeGenerator();
        return dudeGenerator.start(genNum);
    }
}