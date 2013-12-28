package com.hiringtask.server.model;

import com.hiringtask.server.DudeDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DudeGenerator {
    final String firstLetter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final String numsAndLetters = "0123456789" + firstLetter.toLowerCase();

    final Random rnd = new Random();

    private DudeDao dd = new DudeDao();

    public void dlgenerate(int num) {
        List<Dude> list = new ArrayList<>();
        //((ArrayList) list).ensureCapacity(num);
        for (int i = 0; i < num; i++) {
            Dude d = new Dude();
            d.setId(i);
            d.setFirstName(random5to10Name());
            d.setLastName(random5to10Name());
            list.add(d);
            if (i % 10_000 == 0) {
                System.out.println(list.size());
                dd.saveList(list);
                list.clear();
            }
        }
        dd.saveList(list);
    }

    public static void main(String[] args) {
        DudeGenerator DG = new DudeGenerator();
        DG.start(100_000);
        //DG.dlgenerate(1_00_000);
    }

    public void start(int num) {
        long start = System.currentTimeMillis();
        DudeGenerator DG = new DudeGenerator();
        //dd.clear();
        //dd.prepare();
        DG.dlgenerate(num);
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
