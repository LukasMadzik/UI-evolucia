package com.company;

import Models.Generacia;
import Models.Jedinec;
import Models.Zahrada;

import java.io.*;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
        Zahrada zahrada = new Zahrada();
        zahrada.nacitaj();
        Generacia generacia = new Generacia();
        System.out.printf("\nGeneracia 0:\n");
        generacia.generuj(zahrada.getPocetJedincov(), zahrada);
        for(int i = 0;i<100000;i++){
            System.out.printf("\nGeneracia %d:\n",i+1);
            generacia = new Generacia(generacia, zahrada);
        }
    }
}
