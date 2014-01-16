package com.infinitescrolling.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.infinitescrolling.client.TableOfDudesService;
import com.infinitescrolling.server.model.DudeGenerator;

public class TableOfDudesServiceImpl extends RemoteServiceServlet implements TableOfDudesService {

    public String generate(int genNum) {
        DudeGenerator dudeGenerator = new DudeGenerator();
        return dudeGenerator.start(genNum);
    }
}