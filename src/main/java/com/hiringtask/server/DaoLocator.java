package com.hiringtask.server;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class DaoLocator implements ServiceLocator {
    @Override
    public Object getInstance(Class<?> clazz) {
        return new DudeDao();
    }
}