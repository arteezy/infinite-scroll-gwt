package com.hiringtask.server.model;

import com.hiringtask.server.DudeDao;
import com.hiringtask.server.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DudeGenerator {
    final String firstLetter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final String numsAndLetters = "0123456789" + firstLetter.toLowerCase();

    private DudeDao dd = new DudeDao();

    public void dlgenerate(int genNum) {
        List<Dude> list = new ArrayList<>();
        for (int i = 0; i < genNum; i++) {
            Dude d = new Dude();
            d.setId(i);
            d.setFirstName(random5to10Name());
            d.setLastName(random5to10Name());
            list.add(d);
            if (i % 10_000 == 0) {
                dd.saveList(list);
                list.clear();
            }
        }
        dd.saveList(list);
    }

    public static void main(String[] args) {
        DudeGenerator DG = new DudeGenerator();
        System.out.println(DG.start(1_000_000));
        HibernateUtil.getSessionFactory().close();
    }

    public String start(int genNum) {
        long start = System.currentTimeMillis();
        dd.clearTable();
        dd.prepare();
        dd.createScheme();
        dlgenerate(genNum);
        long end = System.currentTimeMillis();
        return ("Generation time: " + ((double) (end - start)/1000) + " s");
    }

    public String random5to10Name() {
        int len = 5 + ThreadLocalRandom.current().nextInt(6);
        StringBuilder sb = new StringBuilder(len);
        sb.append(firstLetter.charAt(ThreadLocalRandom.current().nextInt(firstLetter.length())));
        for(int i = 0; i < len - 1; i++)
            sb.append(numsAndLetters.charAt(ThreadLocalRandom.current().nextInt(numsAndLetters.length())));
        return sb.toString();
    }
}
