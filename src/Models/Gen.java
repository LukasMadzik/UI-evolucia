package Models;

import java.util.*;

/**
 * Created by Lukas on 16-Apr-16.
 */
public class Gen {
    private int cislo;
    private List<Integer> smer = new ArrayList<Integer>(); // 0=^  1=>  2=v  3=<

    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public Gen(int cislo, int sirka, int vyska) {
        this.cislo = cislo;
        if(isBetween(cislo,0,sirka-1)){
            smer.add(2);
        }
        if(isBetween(cislo,sirka,sirka+vyska-1)){
            smer.add(3);
        }
        if(isBetween(cislo,sirka+vyska,sirka+vyska+sirka-1)){
            smer.add(0);
        }
        if(isBetween(cislo,sirka+vyska+sirka,sirka+vyska+sirka+vyska-1)){
            smer.add(1);
        }
    }

    public List<Integer> getSmer() {
        return smer;
    }

    public void setSmer(List<Integer> smer) {
        this.smer = smer;
    }

    public int getCislo() {
        return cislo;
    }

    public void setCislo(int cislo) {
        this.cislo = cislo;
    }
}
