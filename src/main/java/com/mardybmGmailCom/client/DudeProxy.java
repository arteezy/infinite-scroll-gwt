package com.mardybmGmailCom.client;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.mardybmGmailCom.server.DudeLocator;
import com.mardybmGmailCom.server.model.Dude;

@ProxyFor(value = Dude.class, locator = DudeLocator.class)
public interface DudeProxy extends EntityProxy {
    public Integer getId();

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);
}
