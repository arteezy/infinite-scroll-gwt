package com.mardybmGmailCom.client;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import com.mardybmGmailCom.server.DaoLocator;
import com.mardybmGmailCom.server.DudeDao;

import java.util.List;

public interface DudeRequestFactory extends RequestFactory {

    @Service(value = DudeDao.class, locator = DaoLocator.class)
    public interface DudeRequestContext extends RequestContext {
        Request<DudeProxy> findById(int id);
        Request<List<DudeProxy>> getListByRange(int start, int end);
        Request<Void> save(DudeProxy dude);
    }

    DudeRequestContext context();
}
