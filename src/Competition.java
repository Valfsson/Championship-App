package com.anna0520.sm;

import java.util.ArrayList;

public class Competition {

    //for Aerial Sports och Ultra  competititon (that doesn´´ need any divisions or categories)

    private String name;
    private Division division;
  //  private ArrayList <Division> allDivisions; //all the divisions for this competition
  //  private ArrayList <Athlete> allAtheletsInCompetition; //all the athlets competing in all divisions and categories in this competition

    public Competition(String name){
        this.name=name;
    }

    public String getName(){
         return name;
    }

    public Division getDevision(){
        return division;
    }

    public void setDivision(Division division){
        this.division=division;
    }

}
