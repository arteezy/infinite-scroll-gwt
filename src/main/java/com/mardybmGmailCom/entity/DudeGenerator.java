package com.mardybmGmailCom.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DudeGenerator {
    //final String firstLetter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //final String numsAndLetters = "0123456789" + firstLetter.toLowerCase();

    final char[] firstLetter = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    final char[] numsAndLetters = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};


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

    public static void main (String[] args) {
        long start = System.currentTimeMillis();
        DudeGenerator DG = new DudeGenerator();
        DG.generate(1_000_000);
        long end = System.currentTimeMillis();
        System.out.println("\nTime of running: " + (end - start) + " ms");
    }

    public String random5to10Name() {
        Random rnd = new Random();
        int len = 5 + rnd.nextInt(6);
        StringBuilder sb = new StringBuilder(len);
        sb.append(firstLetter[rnd.nextInt(firstLetter.length)]);
        for(int i = 0; i < len - 1; i++)
            sb.append(numsAndLetters[rnd.nextInt(numsAndLetters.length)]);
        return sb.toString();
    }
}
