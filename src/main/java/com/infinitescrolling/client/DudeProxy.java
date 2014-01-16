package com.infinitescrolling.client;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.infinitescrolling.server.DudeLocator;
import com.infinitescrolling.server.model.Dude;

@ProxyFor(value = Dude.class, locator = DudeLocator.class)
public interface DudeProxy extends EntityProxy {
    public Integer getId();

    public String getFirstName();

    public void setFirstName(String firstName);

    public String getLastName();

    public void setLastName(String lastName);
}
