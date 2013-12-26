package com.mardybmGmailCom.server.model;

import com.mardybmGmailCom.server.DudeDao;
import com.mardybmGmailCom.server.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DudeGenerator {
    final String firstLetter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final String numsAndLetters = "0123456789" + firstLetter.toLowerCase();

    final Random rnd = new Random();

    private DudeDao dd = new DudeDao();

    public List<String[]> generate(int num) {
        List<String[]> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            String[] tuple = new String[2];
            tuple[0] = random5to10Name();
            tuple[1] = random5to10Name();
            list.add(tuple);
        }
        return list;
    }

    public List<String> lgenerate(int num) {
        List<String> list = new ArrayList<>();
        //((ArrayList) list).ensureCapacity(num);
        for (int i = 0; i < num; i++) {
            list.add(random5to10Name());
        }
        return list;
    }

    public void dlgenerate(int num) {
        List<Dude> list = new ArrayList<>();
        ((ArrayList) list).ensureCapacity(num);
        for (int i = 0; i < num; i++) {
            Dude d = new Dude();
            d.setFirstName(random5to10Name());
            d.setLastName(random5to10Name());
            list.add(d);
        }
        dd.saveList(list);
    }

    public void hgenerate(int num) {
        for (int i = 0; i < num; i++) {
            Dude d = new Dude();
            d.setFirstName(random5to10Name());
            d.setLastName(random5to10Name());
            dd.save(d);
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        DudeGenerator DG = new DudeGenerator();
        DG.dlgenerate(1_0_000);
        long end = System.currentTimeMillis();
        System.out.println("\nTime of running: " + (end - start) + " ms");
    }

    public void start() {
        long start = System.currentTimeMillis();
        DudeGenerator DG = new DudeGenerator();
        DG.lgenerate(1_000_000);
        long end = System.currentTimeMillis();
        System.out.println("\nTime of running: " + (end - start) + " ms");
    }

    public String random5to10Name() {
        int len = 5 + rnd.nextInt(6);
        StringBuilder sb = new StringBuilder(len);
        sb.append(firstLetter.charAt(rnd.nextInt(firstLetter.length())));
        for(int i = 0; i < len - 1; i++)
            sb.append(numsAndLetters.charAt(rnd.nextInt(numsAndLetters.length())));
        return sb.toString();
    }
}
