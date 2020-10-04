package com.anna0520.sm;

import java.util.ArrayList;

public class ArtisticPole extends Competition {


    private ArrayList <Division> divisionsArtisticPole= new ArrayList<>(); //ensum


    public ArtisticPole(String name) {
        super(name);
        divisionsArtisticPole.add(0,new Division("Amateur"));
        divisionsArtisticPole.add(1,new Division("Semi-Professional"));
        divisionsArtisticPole.add(2,new Division("Professional"));
    }

    public ArrayList<Division> getDivisionsArtisticPole() {
        return divisionsArtisticPole;
    }


}
