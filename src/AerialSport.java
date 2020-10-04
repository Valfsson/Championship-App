package com.anna0520.sm;

import java.util.ArrayList;

public class AerialSport extends Competition{

    private ArrayList<Division> divisionsAerialSport= new ArrayList<>();

    public AerialSport(String name) {
        super(name);

        divisionsAerialSport.add(0,new Division("Amateur"));
       divisionsAerialSport.add(1,new Division("Professional"));
       divisionsAerialSport.add(2,new Division("Elite"));
    }

    public ArrayList<Division> getDivisionsAerialSport() {
        return divisionsAerialSport;}

}
