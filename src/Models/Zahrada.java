package Models;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Lukas on 13-Apr-16.
 */
public class Zahrada {
    private int[][] mapa;
    private int sirka;
    private int vyska;
    private int mutaciaFaktor = 10;
    private int elitaFaktor = 10;
    private int krizenieFaktor = 80;
    private int pocetJedincov = 100;

    public int getMutaciaFaktor() {
        return mutaciaFaktor;
    }

    public void setMutaciaFaktor(int mutaciaFaktor) {
        this.mutaciaFaktor = mutaciaFaktor;
    }

    public int getElitaFaktor() {
        return elitaFaktor;
    }

    public void setElitaFaktor(int elitaFaktor) {
        this.elitaFaktor = elitaFaktor;
    }

    public int getKrizenieFaktor() {
        return krizenieFaktor;
    }

    public void setKrizenieFaktor(int krizenieFaktor) {
        this.krizenieFaktor = krizenieFaktor;
    }

    public int getPocetJedincov() {
        return pocetJedincov;
    }

    public void setPocetJedincov(int pocetJedincov) {
        this.pocetJedincov = pocetJedincov;
    }

    public Zahrada(int[][] mapa, int sirka, int vyska) {
        this.mapa = new int[sirka][];
        for(int i = 0;i<sirka;i++){
            this.mapa[i]=mapa[i].clone();
        }
        this.sirka = sirka;
        this.vyska = vyska;
    }

    public void print(){
        for(int i = 0;i<vyska;i++){
            for(int j = 0;j<sirka;j++){
                System.out.printf("%d ", mapa[j][i]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");
    }

    public Zahrada clone() {
        return new Zahrada(mapa, sirka, vyska);
    }

    public Zahrada() {
        mapa=null;
    }

    public void nacitaj(){
        Scanner scanner = new Scanner(System.in);
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        String fileZahradka = scanner.next();
        try {
            inputStream = new FileInputStream(fileZahradka);
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
            String riadok;
            riadok = reader.readLine();
            String[] parsed = riadok.split(" ");
            sirka = Integer.parseInt(parsed[0]);
            vyska = Integer.parseInt(parsed[1]);
            mutaciaFaktor = Integer.parseInt(parsed[2]);
            elitaFaktor = Integer.parseInt(parsed[3]);
            krizenieFaktor = Integer.parseInt(parsed[4]);
            pocetJedincov = Integer.parseInt(parsed[5]);
            mapa = new int[sirka][vyska];
            for(int i = 0;i<vyska;i++){
                riadok = reader.readLine();
                String[] parsed2 = riadok.split(" ");
                for(int j = 0;j<sirka;j++){
                    mapa[j][i] = Integer.parseInt(parsed2[j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] getMapa() {
        return mapa;
    }

    public void setMapa(int[][] mapa) {
        this.mapa = mapa;
    }

    public int getSirka() {
        return sirka;
    }

    public void setSirka(int sirka) {
        this.sirka = sirka;
    }

    public int getVyska() {
        return vyska;
    }

    public void setVyska(int vyska) {
        this.vyska = vyska;
    }

    public int getObvod() {
        return vyska*2+sirka*2;
    }
}
