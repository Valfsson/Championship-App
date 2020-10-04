package com.anna0520.sm;

import java.util.ArrayList;

public class PoleSport extends Competition {

private ArrayList<Division> divisionsPoleSport= new ArrayList<>();

    public PoleSport(String name) {
        super(name);
        divisionsPoleSport.add(0,new Division("Amateur"));
        divisionsPoleSport.add(1,new Division("Professional"));
        divisionsPoleSport.add(2,new Division("Elite"));
    }

    public ArrayList<Division> getDivisionsPoleSport() {
        return divisionsPoleSport;}
}
