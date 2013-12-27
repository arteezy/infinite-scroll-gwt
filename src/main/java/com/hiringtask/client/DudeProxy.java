package com.hiringtask.client;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.hiringtask.server.DudeLocator;
import com.hiringtask.server.model.Dude;

@ProxyFor(value = Dude.class, locator = DudeLocator.class)
public interface DudeProxy extends EntityProxy {
    public Integer getId();

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);
}
