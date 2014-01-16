package com.infinitescrolling.server;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;
import com.infinitescrolling.server.model.DudeDao;

public class DaoLocator implements ServiceLocator {
    @Override
    public Object getInstance(Class<?> clazz) {
        return new DudeDao();
    }
}