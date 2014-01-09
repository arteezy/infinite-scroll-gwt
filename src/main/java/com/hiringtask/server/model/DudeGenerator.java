package com.hiringtask.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DudeGenerator {
    final String firstLetter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final String numsAndLetters = "0123456789" + firstLetter.toLowerCase();

    private DudeDao dudeDao = new DudeDao();

    public void generateAndSave(int genNum) {
        List<Dude> list = new ArrayList<>();
        for (int i = 0; i < genNum; i++) {
            Dude d = new Dude();
            d.setId(i);
            d.setFirstName(random5to10Name());
            d.setLastName(random5to10Name());
            list.add(d);
            if (i % 10_000 == 0) {
                dudeDao.saveList(list);
                list.clear();
            }
        }
        dudeDao.saveList(list);
    }

    public String start(int genNum) {
        long start = System.currentTimeMillis();
        dudeDao.clearTable();
        dudeDao.prepare();
        dudeDao.createSchema();
        generateAndSave(genNum);
        long end = System.currentTimeMillis();
        return ("Generation time: " + ((double) (end - start)/1000) + " s");
    }

    public String random5to10Name() {
        int randLength = 5 + ThreadLocalRandom.current().nextInt(6);
        StringBuilder sb = new StringBuilder(randLength);
        sb.append(firstLetter.charAt(ThreadLocalRandom.current().nextInt(firstLetter.length())));
        for(int i = 0; i < randLength - 1; i++)
            sb.append(numsAndLetters.charAt(ThreadLocalRandom.current().nextInt(numsAndLetters.length())));
        return sb.toString();
    }
}
