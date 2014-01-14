package com.hiringtask.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.hiringtask.client.TableOfDudesService;
import com.hiringtask.server.model.DudeGenerator;

public class TableOfDudesServiceImpl extends RemoteServiceServlet implements TableOfDudesService {

    public String generate(int genNum) {
        DudeGenerator dudeGenerator = new DudeGenerator();
        return dudeGenerator.start(genNum);
    }
}