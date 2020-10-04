package com.anna0520.sm;

import java.util.UUID;
import android.os.Parcel;
import android.os.Parcelable;

public class Athlete implements Parcelable {
    private UUID mId;
    private String name;
    private boolean compFeepaid;
   // private Category category; // ändra till competition som obligatoriskt ej categori
    private Competition competition;
   // private Division division;

    public Athlete(){
        mId=UUID.randomUUID();
    }

    //tillhör Parcel
    protected Athlete(Parcel in) {
        name = in.readString();
        compFeepaid = in.readByte() != 0;
    }

    //tillhör Parcel
    public static final Creator<Athlete> CREATOR = new Creator<Athlete>() {
        @Override
        public Athlete createFromParcel(Parcel in) {
            return new Athlete(in);
        }

        //tillhör Parcel
        @Override
        public Athlete[] newArray(int size) {
            return new Athlete[size];
        }
    };

    //tillhör Parcel
    @Override
    public int describeContents() {
        return 0;
    }

    // tollhör parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // dest.writeString(name);
        //   dest.writeString(name);

        dest.writeString(name);
        dest.writeByte((byte) (compFeepaid ? 1 : 0));
    }


    public UUID getId(){
        return mId;
    }

/*    public Division getDivision(){
        return division;
    }

    public void setDivision(Division division){
        this.division=division;
    }*/

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

    public boolean isCompFeepaid(){return compFeepaid; }

    public void setCompFeepaid(boolean compFeepaid) {
        this.compFeepaid = compFeepaid;
    }

   public Competition getCompetition(){
        return competition; }

   public void setCompetition(Competition competition){
        this.competition=competition;
   }

 /*  public Category getCategory(){
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public boolean equals(Object other){
        return (other instanceof Athlete) && ((Athlete)other).getId()== mId &&((Athlete)other).getCategory().getName().equalsIgnoreCase(category.getName());
    }

    public String toString(){
        return "Name: "+name+ ", Category:  "+category.getName()+", Paid: "+isCompFeepaid();
    }
*/

}

