package com.hiringtask.client;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import com.hiringtask.server.DaoLocator;
import com.hiringtask.server.DudeDao;

import java.util.List;

public interface DudeRequestFactory extends RequestFactory {

    @Service(value = DudeDao.class, locator = DaoLocator.class)
    public interface DudeRequestContext extends RequestContext {
        Request<DudeProxy> findById(int id);
        Request<List<DudeProxy>> getListByRange(int start, int end);
        Request<List<DudeProxy>> getSortedListByRange(int start, int end, String column, boolean asc);
        Request<Void> save(DudeProxy dude);
    }

    DudeRequestContext context();
}
