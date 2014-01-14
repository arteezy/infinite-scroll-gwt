package com.hiringtask.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class DudeGenerator {
    final String firstLetter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final String numsAndLetters = "0123456789" + firstLetter.toLowerCase();

    final int numThreads = 4;
    final CountDownLatch latch = new CountDownLatch(numThreads);

    private DudeDao dudeDao = new DudeDao();

    public void generateAndSave(int genNum) {
        List<Dude> list = new ArrayList<>();
        for (int i = 0; i < genNum; i++) {
            Dude d = new Dude();
            d.setId(i);
            d.setFirstName(random5to10Name());
            d.setLastName(random5to10Name());
            list.add(d);
            if (i % 25_000 == 0) {
                dudeDao.saveList(list);
                list.clear();
            }
        }
        dudeDao.saveList(list);
    }

    public void parGenerateAndSave(final int genNum) {
        for (int i = 0; i < numThreads; i++) {
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    int number = Integer.parseInt(Thread.currentThread().getName());
                    List<Dude> list = new ArrayList<>();
                    System.out.println("Thread: " + number + " (" + (number * genNum/4) + " - " + ((number + 1) * genNum/4) + ")");
                    for (int j = (number * genNum/4); j < ((number + 1) * genNum/4); j++) {
                        Dude dude = new Dude();
                        dude.setId(j);
                        dude.setFirstName(random5to10Name());
                        dude.setLastName(random5to10Name());
                        list.add(dude);
                    }
                    dudeDao.saveList(list);
                    latch.countDown();
                }
            };

            Thread t = new Thread(run);
            t.setName(String.valueOf(i));
            t.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String start(int genNum) {
        long start = System.currentTimeMillis();
        dudeDao.clearTable();
        dudeDao.prepare();
        dudeDao.createSchema();
        parGenerateAndSave(genNum);
        long end = System.currentTimeMillis();
        return ("Generation time: " + ((double) (end - start)/1000) + " s");
    }

    public static void main(String[] args) {
        DudeDao dudeDao = new DudeDao();
        DudeGenerator dg = new DudeGenerator();
        long start = System.currentTimeMillis();
        dudeDao.clearTable();
        dudeDao.prepare();
        dudeDao.createSchema();
        dg.parGenerateAndSave(1000000);
        long end = System.currentTimeMillis();
        System.out.println("Generation time: " + ((double) (end - start)/1000) + " s");
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
