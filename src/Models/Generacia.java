package Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lukas on 16-Apr-16.
 */
public class Generacia {
    private int pocet;
    private List<Jedinec> jedince;

    public int getPocet() {
        return pocet;
    }

    public void setPocet(int pocet) {
        this.pocet = pocet;
    }

    public List<Jedinec> getJedince() {
        return jedince;
    }

    public void setJedince(List<Jedinec> jedince) {
        this.jedince = jedince;
    }

    public Generacia() {
        jedince = new ArrayList<Jedinec>();
    }



    public Generacia(Generacia generacia, Zahrada zahrada){
        int bestFitness=0;
        int avgFitness = 0;
        jedince = new ArrayList<Jedinec>();
        Random random = new Random();
        pocet = generacia.getPocet();
        generacia.getJedince().sort(new JedinecComparator());
        int elita = (int) (generacia.getJedince().size()*zahrada.getElitaFaktor()/100);
        int krizenie = (int) (generacia.getJedince().size()*zahrada.getKrizenieFaktor()/100);
        for(int i = 0;i<elita;i++){
            jedince.add(generacia.getJedince().get(i));
            jedince.get(i).mutacia();
            jedince.get(i).setFitness(0);
            jedince.get(i).otestuj(zahrada);
            avgFitness += jedince.get(i).getFitness();
            if(jedince.get(i).getFitness()>bestFitness)
                bestFitness = jedince.get(i).getFitness();
            if(jedince.get(i).getFitness() == zahrada.getSirka()*zahrada.getVyska()){
                System.out.printf("\nZahradka je pohrabana\n");
                jedince.get(i).getZahrada().print();
                System.exit(0);
            }
        }
        for(int i = elita;i<elita+krizenie;i++){
            jedince.add(new Jedinec(zahrada, generacia.getJedince().get(random.nextInt(generacia.getPocet())),generacia.getJedince().get(random.nextInt(generacia.getPocet()))));
            jedince.get(i).mutacia();
            jedince.get(i).otestuj(zahrada);
            avgFitness += jedince.get(i).getFitness();
            if(jedince.get(i).getFitness()>bestFitness)
                bestFitness = jedince.get(i).getFitness();
            if(jedince.get(i).getFitness() == zahrada.getSirka()*zahrada.getVyska()){
                System.out.printf("\nZahradka je pohrabana\n");
                jedince.get(i).getZahrada().print();
                System.exit(0);
            }
        }
        for(int i = elita+krizenie;i<generacia.getJedince().size();i++){
            jedince.add(new Jedinec(generacia.getJedince().get(1).getGeny().length*2,zahrada));
            jedince.get(i).otestuj(zahrada);
            avgFitness += jedince.get(i).getFitness();
            if(jedince.get(i).getFitness()>bestFitness)
                bestFitness = jedince.get(i).getFitness();
            if(jedince.get(i).getFitness() == zahrada.getSirka()*zahrada.getVyska()){
                System.out.printf("\nZahradka je pohrabana\n");
                jedince.get(i).getZahrada().print();
                System.exit(0);
            }
        }
        avgFitness = avgFitness/generacia.getJedince().size();
        System.out.printf("Best fitness: %d\nAverage fitness: %d\n",bestFitness, avgFitness);
    }

    public void generuj(int pocet, Zahrada zahrada){
        this.pocet = pocet;
        for(int i = 0;i<pocet;i++){
            jedince.add(new Jedinec(zahrada.getObvod(), zahrada));
            jedince.get(i).otestuj(zahrada);
            if(jedince.get(i).getFitness() == zahrada.getSirka()*zahrada.getVyska()){
                System.out.printf("\nZahradka je pohrabana\n");
                jedince.get(i).getZahrada().print();
                System.exit(0);
            }
        }
    }
}
