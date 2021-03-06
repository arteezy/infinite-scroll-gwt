package com.infinitescrolling.client;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import com.infinitescrolling.server.DaoLocator;
import com.infinitescrolling.server.model.DudeDao;

import java.util.List;

public interface DudeRequestFactory extends RequestFactory {

    @Service(value = DudeDao.class, locator = DaoLocator.class)
    public interface DudeRequestContext extends RequestContext {
        Request<DudeProxy> findById(int id);
        Request<List<DudeProxy>> getListByRange(int start, int end);
        Request<List<DudeProxy>> getSortedListByRange(int start, int end, String column, boolean asc);
        Request<Void> saveDude(DudeProxy dude);
    }

    DudeRequestContext context();
}
