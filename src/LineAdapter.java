package com.anna0520.sm;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

public class LineAdapter extends RecyclerView.Adapter<LineAdapter.MyLineHolder> { //ADAPTER

    private ArrayList<Athlete> mAthletes;
    private LayoutInflater mInflater;


    LineAdapter(Context context, ArrayList<Athlete> mAthletes) {
        this.mInflater = LayoutInflater.from(context);
        this.mAthletes = mAthletes;
//        mAthletesFull= new ArrayList<>(mAthletes);
    }

    @Override
    public MyLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.athlete_line_view, parent, false);
        return new MyLineHolder(view);
    }


    @Override
    public void onBindViewHolder(MyLineHolder holder, int position) {
        //get element from dataset at this position
        //replace the contents of the view with that element
       if(mAthletes.get(position)!=null) {
            String sAthlete = mAthletes.get(position).getName();
            holder.name.setText(sAthlete);
           // if (!mAthletes.get(position).getCategory().getName().isEmpty()) {
         if(mAthletes.get(position).getCompetition()!=null) {

             //Competition
             String aCompetition=mAthletes.get(position).getCompetition().getName();

             switch (aCompetition){

                 case "Pole Sport":
                     holder.competition.setText("Sport");
                     break;
                 case "Para Pole":
                     holder.competition.setText("Para");
                     break;
                 case "Ultra Pole":
                     holder.competition.setText("Ultra");
                     break;
                 case "Artistic Pole":
                     holder.competition.setText("Art");
                     break;
                 case "Aerial Sport":
                     holder.competition.setText("Aerial");
                     break;
             }

             if (mAthletes.get(position).getCompetition().getDevision() != null) {
                 String sAthleteDivision = mAthletes.get(position).getCompetition().getDevision().getName();

                 if (sAthleteDivision.equalsIgnoreCase("Amateur")) {
                     holder.division.setText("Amateur");
                 } else if (sAthleteDivision.equalsIgnoreCase("Professional")) {
                     holder.division.setText("Pro");
                 } else if (sAthleteDivision.equalsIgnoreCase("Semi-Professional")) {
                     holder.division.setText("SemiPro");
                 } else if (sAthleteDivision.equalsIgnoreCase("Elite")) {
                     holder.division.setText("Elite");
                 }

                 if (mAthletes.get(position).getCompetition().getDevision().getCategory() != null) {
                     String sAthleteCat = mAthletes.get(position).getCompetition().getDevision().getCategory().getName();
                     holder.category.setText(sAthleteCat);
                 }
             }
         }


        /*    boolean paid = mAthletes.get(position).isCompFeepaid();
            if (paid == true) {
                holder.paid.setText("paid");
            } else {
                holder.paid.setText("not paid");
            }*/
        }

    } //onBindViewHolder ends

    @Override
    public int getItemCount() {
        return mAthletes.size();
    }


    public class MyLineHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //VIEW HOLDER

        public TextView competition;
        public TextView name;
        public TextView category;
        public TextView division;
        public ImageView delete;


        MyLineHolder(final View itemView) {
            super(itemView);
            competition=itemView.findViewById(R.id.a_competition_view);
            name = itemView.findViewById(R.id.main_line_name);
            category = itemView.findViewById(R.id.main_line_category);
            division=itemView.findViewById(R.id.main_line_division);
            delete=itemView.findViewById(R.id.delete_athlete);
            delete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // tar bort atlet
        if (v.equals(delete)){
            removeAt(getAdapterPosition());
        }
        }

    } //my line holder



    public void removeAt(int position){

        mAthletes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mAthletes.size());
    }

    Athlete getItem(int id) {
        return mAthletes.get(id);
    }
}



