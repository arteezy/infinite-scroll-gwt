package com.hiringtask.server;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;
import com.hiringtask.server.model.DudeDao;

public class DaoLocator implements ServiceLocator {
    @Override
    public Object getInstance(Class<?> clazz) {
        return new DudeDao();
    }
}