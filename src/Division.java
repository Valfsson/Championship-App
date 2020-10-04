package com.anna0520.sm;

import java.util.ArrayList;

public class Division {

    private String name;
    private Category category;
   // private ArrayList<Category> allCategories; //includes all the categories for this devision
    //private ArrayList <Athlete> allAthletesInDivisiont; //althlets fro all categories competing in this division


    public Division(String name) {
        this.name=name;
    }


    public String getName(){return name;}

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category category){
        this.category=category;
    }
}
