package com.anna0520.sm;

import java.util.ArrayList;

//This Class will be used for further development of the app.
    //Where possibility to categories the list by the category will be implemented
public class Category {
    private String name;
   // private ArrayList<Athlete> athletesForCategory;

    public Category(String name) {
        this.name=name;
    }

    public String getName(){
        return name;
    }

    public void setName(String catName){
        name=catName;
    }

}
