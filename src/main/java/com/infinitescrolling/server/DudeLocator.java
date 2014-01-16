package com.infinitescrolling.server;

import com.google.web.bindery.requestfactory.shared.Locator;
import com.infinitescrolling.server.model.Dude;
import com.infinitescrolling.server.model.DudeDao;

public class DudeLocator extends Locator<Dude, Integer>{
    @Override
    public Dude create(Class<? extends Dude> clazz) {
        return new Dude();
    }

    @Override
    public Dude find(Class<? extends Dude> clazz, Integer id) {
        return getDudeDao().findById(id);
    }

    private DudeDao getDudeDao() {
        return new DudeDao();
    }

    @Override
    public Class<Dude> getDomainType() {
        return Dude.class;
    }

    @Override
    public Integer getId(Dude domainObject) {
        return domainObject.getId();
    }

    @Override
    public Class<Integer> getIdType() {
        return Integer.class;
    }

    @Override
    public Object getVersion(Dude domainObject) {
        // I don't need versions
        return 0;
    }
}