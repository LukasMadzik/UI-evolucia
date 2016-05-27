package Models;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Lukas on 16-Apr-16.
 */
public class Jedinec {
    private Gen[] geny;
    private int fitness = 0;
    private Zahrada zahrada;

    public Zahrada getZahrada() {
        return zahrada;
    }

    public void setZahrada(Zahrada zahrada) {
        this.zahrada = zahrada;
    }

    public Jedinec(int pocet, Zahrada zahrada) {
        int[] temp = new int[pocet];
        this.zahrada = zahrada;
        for (int i = 0; i < pocet; i++) {
            temp[i] = i;
        }
        shuffleArray(temp);
        geny = new Gen[pocet / 2];
        for (int i = 0; i < pocet / 2; i++) {
            geny[i] = new Gen(temp[i], zahrada.getSirka(), zahrada.getVyska());
        }
    }

    public Jedinec(Zahrada zahrada, Jedinec jedinec1, Jedinec jedinec2) {
        geny = new Gen[jedinec1.getGeny().length];
        this.zahrada = zahrada;
        int a = jedinec1.getGeny().length;
        for(int i = 0;i<a/2;i++){
            geny[i] = jedinec1.getGeny()[i];
        }
            for (int i = a / 2; i < a; i++) {
                geny[i] = jedinec2.getGeny()[i];
            }
    }

    static void shuffleArray(int[] ar) {
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    private static boolean outOfMap(int sirka, int vyska, Zahrada zahrada, int smer) {
        if (sirka < 0 || vyska < 0)
            return true;
        switch (smer) {
            case 1: {
                if (sirka >= zahrada.getSirka()) {
                    return true;
                } else {
                    return false;
                }
            }
            case 2: {
                if (vyska >= zahrada.getVyska()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public void otestuj(Zahrada zahrada) {
        Zahrada temp_zahrada = null;
        temp_zahrada = (Zahrada) zahrada.clone();
        List<Integer> smer = null;       // 0=^  1=>  2=v  3=<
        Random random = new Random();
        int f = 1;
        for (Gen gen : geny) {
            int posunutie = 1;
            int stucked = 0;
            int vyska = -1;
            int sirka = -1;
            smer = gen.getSmer();
            if (isBetween(gen.getCislo(), 0, zahrada.getSirka() - 1) && temp_zahrada.getMapa()[gen.getCislo()][0] == 0) {
                vyska = 0;
                sirka = gen.getCislo();
            }
            if (isBetween(gen.getCislo(), zahrada.getSirka(), zahrada.getSirka() + zahrada.getVyska() - 1)
                    && temp_zahrada.getMapa()[zahrada.getSirka() - 1][gen.getCislo() - zahrada.getSirka()] == 0) {
                vyska = gen.getCislo() - zahrada.getSirka();
                sirka = zahrada.getSirka() - 1;
            }
            if (isBetween(gen.getCislo(), zahrada.getSirka() + zahrada.getVyska(), 2 * zahrada.getSirka() + zahrada.getVyska() - 1)
                    && temp_zahrada.getMapa()[gen.getCislo() - 2 * zahrada.getSirka()][zahrada.getVyska() - 1] == 0) {
                vyska = zahrada.getVyska() - 1;
                sirka = gen.getCislo() - 2 * zahrada.getSirka();
            }
            if (isBetween(gen.getCislo(), 2 * zahrada.getSirka() + zahrada.getVyska(), 2 * zahrada.getSirka() + 2 * zahrada.getVyska() - 1)
                    && temp_zahrada.getMapa()[0][gen.getCislo() - 2 * zahrada.getSirka() - zahrada.getVyska()] == 0) {
                vyska = gen.getCislo() - 2 * zahrada.getSirka() - zahrada.getVyska();
                sirka = 0;
            }
            while (!outOfMap(sirka, vyska, temp_zahrada, smer.get(smer.size() - 1))) {
                if (temp_zahrada.getMapa()[sirka][vyska] == 0) {
                    temp_zahrada.getMapa()[sirka][vyska] = f;
                    posunutie++;
                    switch (smer.get(smer.size() - 1)) {
                        case 0: {
                            vyska--;
                            break;
                        }
                        case 1: {
                            sirka++;
                            break;
                        }
                        case 2: {
                            vyska++;
                            break;
                        }
                        case 3: {
                            sirka--;
                            break;
                        }
                    }
                } else {
                    switch (smer.get(smer.size() - 1)) {
                        case 0: {
                            vyska++;
                            break;
                        }
                        case 1: {
                            sirka--;
                            break;
                        }
                        case 2: {
                            vyska--;
                            break;
                        }
                        case 3: {
                            sirka++;
                            break;
                        }
                    }
                    temp_zahrada.getMapa()[sirka][vyska] = 0;
                    int randnum = random.nextInt(2);
                    if (posunutie < 2) {
                        stucked++;
                        if (stucked == 2) {
//                            gen.setCislo(-1);
                            int[][] temp_mapa = temp_zahrada.getMapa();
                            for (int i = 0; i < temp_zahrada.getVyska(); i++) {
                                for (int j = 0; j < temp_zahrada.getSirka(); j++) {
                                    if (temp_mapa[j][i] == f) {
                                        temp_mapa[j][i] = 0;
                                    }
                                }
                            }
                            f--;
                            break;
                        }
                        if (smer.get(smer.size() - 1) == 2) {
                            smer.remove(smer.get(smer.size() - 1));
                            smer.add(0);
                        } else {
                            if (smer.get(smer.size() - 1) == 3) {
                                smer.remove(smer.get(smer.size() - 1));
                                smer.add(1);
                            } else {
                                smer.add(smer.get(smer.size() - 1) + 2);
                                smer.remove(smer.get(smer.size() - 2));
                            }
                        }
                    } else {
                        if (randnum == 0) {
                            if (smer.get(smer.size() - 1) == 0)
                                smer.add(3);
                            else
                                smer.add(smer.get(smer.size() - 1) - 1);
                        } else {
                            if (smer.get(smer.size() - 1) == 3)
                                smer.add(0);
                            else
                                smer.add(smer.get(smer.size() - 1) + 1);
                        }
                        stucked = 0;
                    }
                    posunutie = 0;
                }
            }
            f++;
        }
        setFitness(temp_zahrada);
        setZahrada(temp_zahrada);
//        if(fitness == zahrada.getSirka()*zahrada.getVyska()){
//            temp_zahrada.print();
//        }
    }

    private void setFitness(Zahrada temp_zahrada) {
        int[][] temp_mapa = temp_zahrada.getMapa();
        for (int i = 0; i < temp_zahrada.getVyska(); i++) {
            for (int j = 0; j < temp_zahrada.getSirka(); j++) {
                if (temp_mapa[j][i] != 0) {
                    fitness++;
                }
            }
        }
    }

    public void mutacia(){
        int pozicia;
        for(int i = 0;i<geny.length*zahrada.getMutaciaFaktor()/100;i++){
            Random random = new Random();
            pozicia = random.nextInt(geny.length);
            geny[pozicia] = new Gen(random.nextInt(zahrada.getObvod()),zahrada.getSirka(),zahrada.getVyska());
        }
    }

    public Gen[] getGeny() {
        return geny;
    }

    public void setGeny(Gen[] geny) {
        this.geny = geny;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
}
