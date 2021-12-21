package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 1000;
    public static int bossDamage = 100;
    public static String bossDefence = "";

    public static int[] heroesHealth = {270, 260, 250, 250, 300, 200, 150, 150};
    public static int[] heroesDamage = {15, 20, 25, 0, 10, 20, 15, 20};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Hill", "Break", "Lucky", "Berserk", "Thor"};
    public static int round_number = 0;

    public static void main(String[] args) {
        printStatistics(); // До начала игры вывод статистики
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        round_number++;
        changeBossDefence();
        bossHits();
        heroesHit();
        doctor();
        golem();
        lucky();
        berserk();
        thor();
        printStatistics();
    }

    public static void changeBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); //0,1,2
        bossDefence = heroesAttackType[randomIndex];
        System.out.println("Boss chose " + bossDefence);
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; //2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void doctor() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] != heroesHealth[3] && heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[3] > 0) {
                heroesHealth[i] = heroesHealth[i] + 50;
                System.out.println("Doctor helps");
                break;
            }
        }
    }

    public static void golem() {
        int bossDamageBeta1 = bossDamage;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (bossDamage == bossDamageBeta1) {
                continue;
            }
            if (heroesHealth[4] > 0) {
                if (heroesHealth[i] != heroesHealth[4]) {
                    heroesHealth[i] = heroesHealth[i] + bossDamage / 5;
                    System.out.println("Golem did it");
                } else {
                    heroesHealth[4] = heroesHealth[4] - bossDamage / 5 * (heroesHealth.length - 1);
                }
            } else {
                heroesHealth[4] = 0;
            }

        }
    }

    public static void lucky() {
        if (heroesHealth[5] > 0) {
            Random random = new Random();
            boolean isHeLucky = random.nextBoolean();
            if (isHeLucky == true) {
                heroesHealth[5] += bossDamage;
                System.out.println("Is he lucky " + isHeLucky);
            } else {
                System.out.println("Is he lucky " + isHeLucky);
            }
        }
    }
    public static void berserk() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] == heroesHealth[6]) {
                heroesHealth[6] += bossDamage / 2;
                heroesDamage[6] += bossDamage / 2;
            }
        }
    }

    public static void thor() {
        if (heroesHealth[7] > 0) {
            int bosDamageBeta2 = bossDamage;
            Random random2 = new Random();
            boolean doTheyGetHit = random2.nextBoolean();
            if (doTheyGetHit == true) {
                System.out.println("do they get hit " + doTheyGetHit);
                bossDamage = bosDamageBeta2;
            } else {
                System.out.println("do they get hit " + doTheyGetHit);
                bossDamage = 0;
            }
        }
    }

    public static void printStatistics() {
        System.out.println(round_number + " ROUND _______________");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
        System.out.println("_______________");
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }
}