package Models;

import java.util.Comparator;

/**
 * Created by Lukas on 17-Apr-16.
 */
public class JedinecComparator implements Comparator<Jedinec> {
    @Override
    public int compare(Jedinec o1, Jedinec o2) {
        return o1.getFitness()<o2.getFitness() ? 1 : o1.getFitness() == o2.getFitness() ? 0 : -1;
    }
}