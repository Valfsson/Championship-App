package com.anna0520.sm;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class AllAthletesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private RecyclerView allAthletsRView;
    private SearchView msearchView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Athlete> allAthletes;// list som sparas lokalt i appen
    private Competition mCompetition;
    private ArrayList<Competition> allCompetitions;
    private Button mSortBtn;

    private Spinner mSpinnerCompetition, mSpinnerDivision, mSpinnerCategory;


    static final int NEW_ATHLETE_REQUEST = 1;
    private static final String ATHLETES_LIST = "Athletes_list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_athlets_activity_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //prevents FAB from jumping up when the keyboard pops up


        //adds back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FA270A5A"))); //setting action bar color to match the design


        loadData(); //all the registrated athletes from the internal storage

        createCompetitionsList(); //list of all 5 competitions

        mSpinnerCompetition = (Spinner) findViewById(R.id.spinner_competition);
        setSpinnerCompetition();
        mSpinnerCompetition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String input_competition = mSpinnerCompetition.getSelectedItem().toString();


                //on somethin choosen
                setDivisionSpinner(input_competition); //sets division spinner depending on the competition choosen
                mSpinnerDivision.setEnabled(true); //can now choose from spinner
                setCategorySpinner(input_competition); //sets category depending on competition
                mSpinnerCategory.setEnabled(true);//can choose the category now

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerDivision = (Spinner) findViewById(R.id.spinner_devision);
        mSpinnerDivision.setEnabled(false); //can choose after competition is chosen;

        mSpinnerCategory = (Spinner) findViewById(R.id.spinner_category);
        mSpinnerCategory.setEnabled(false); //can choose after competition is chosen

        //new button "sort"
        mSortBtn = (Button) findViewById(R.id.sort_btn);
        mSortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSortInput();// gets how many of 3 sorting spinners is used + gets selected alternative
            }
        });

        //implement sorts for all

        //All of the athletes shown as a RecycleView
        allAthletsRView = (RecyclerView) findViewById(R.id.all_athlets_Recycle_View);
        updateAdapter();

        msearchView = (SearchView) findViewById(R.id.searchView_alla);
        msearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override

            public boolean onQueryTextSubmit(String query) { //searches after submiting

                ArrayList<Athlete> filteredAthletes = new ArrayList<>();

                String searchInput = msearchView.getQuery().toString().toLowerCase().trim();
                for (Athlete athlete : allAthletes) {
                    if (athlete.getName().toLowerCase().equalsIgnoreCase(searchInput) || athlete.getName().toLowerCase().startsWith(searchInput) //search of name of the athlete
                            || athlete.getCompetition().getName().toLowerCase().equalsIgnoreCase(searchInput) || athlete.getCompetition().getName().toLowerCase().startsWith(searchInput)//search of name of the competition
                            || athlete.getCompetition().getDevision().getName().toLowerCase().equalsIgnoreCase(searchInput) || athlete.getCompetition().getDevision().getName().toLowerCase().startsWith(searchInput)  ////search of name of the division
                            || athlete.getCompetition().getDevision().getCategory().getName().toLowerCase().equalsIgnoreCase(searchInput) || athlete.getCompetition().getDevision().getCategory().getName().toLowerCase().startsWith(searchInput)) { //
                        filteredAthletes.add(athlete);
                    }
                }


                setTemporaryAdapter(filteredAthletes);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<Athlete> filteredAthletes = new ArrayList<>();

                String searchInput = msearchView.getQuery().toString().toLowerCase().trim();
                for (Athlete athlete : allAthletes) {
                    if (athlete.getName().toLowerCase().equalsIgnoreCase(searchInput) || athlete.getName().toLowerCase().startsWith(searchInput) //search of name of the athlete
                            || athlete.getCompetition().getName().toLowerCase().equalsIgnoreCase(searchInput) || athlete.getCompetition().getName().toLowerCase().startsWith(searchInput)//search of name of the competition
                            || athlete.getCompetition().getDevision().getName().toLowerCase().equalsIgnoreCase(searchInput) || athlete.getCompetition().getDevision().getName().toLowerCase().startsWith(searchInput)  ////search of name of the division
                            || athlete.getCompetition().getDevision().getCategory().getName().toLowerCase().equalsIgnoreCase(searchInput) || athlete.getCompetition().getDevision().getCategory().getName().toLowerCase().startsWith(searchInput)) { //
                        filteredAthletes.add(athlete);
                    }
                }

                setTemporaryAdapter(filteredAthletes);


                return true;
            }
        });

        msearchView.setOnCloseListener(new SearchView.OnCloseListener() { //chows list of all registreted athlets without sorting
            @Override
            public boolean onClose() {
                msearchView.onActionViewCollapsed();
                updateAdapter();
                return true;
            }
        });


        //FAB som lägger till en ny atlet
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msearchView.onActionViewCollapsed();
                Intent intent = new Intent(AllAthletesActivity.this, AddAthleteActivity.class);
                startActivityForResult(intent, NEW_ATHLETE_REQUEST);
            }
        });

    } //onCreate ends


    public void getSortInput() {

        String input_category, input_division, input_competition;

        if (mSpinnerCompetition.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All")) {
            updateAdapter();//show all athletes
        }
        else if ( !mSpinnerCompetition.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All")
                && mSpinnerDivision.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All") && mSpinnerCategory.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All")) {
            input_competition = mSpinnerCompetition.getSelectedItem().toString();
            sortBy(input_competition); //sorting by one parameter
        } else if (!mSpinnerDivision.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All") && mSpinnerCompetition.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All") && mSpinnerCategory.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All")) {
            input_division = mSpinnerDivision.getSelectedItem().toString();
            sortBy(input_division); //sorting by one parameter
        } else if (!mSpinnerCategory.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All") && mSpinnerDivision.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All") && mSpinnerCompetition.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All")) {
            input_category = mSpinnerCategory.getSelectedItem().toString();
            sortBy(input_category);//sorting by one parameter
        } else if (!mSpinnerCompetition.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All")&& !mSpinnerDivision.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All") && mSpinnerCategory.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All")) {
            input_competition = mSpinnerCompetition.getSelectedItem().toString();
            input_division = mSpinnerDivision.getSelectedItem().toString();
            sortByComDiv(input_competition, input_division); //sort by two parameters
        } else if (!mSpinnerCompetition.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All") && mSpinnerDivision.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All") && !mSpinnerCategory.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All")) {
            input_competition = mSpinnerCompetition.getSelectedItem().toString();
            input_category = mSpinnerCategory.getSelectedItem().toString();
            sortByComCat(input_competition, input_category); //sort by two parameters
        } else if (!mSpinnerCompetition.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All") && !mSpinnerDivision.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All") && !mSpinnerCategory.getSelectedItem().toString().toLowerCase().equalsIgnoreCase("All")) {
            input_division = mSpinnerDivision.getSelectedItem().toString();
            input_category = mSpinnerCategory.getSelectedItem().toString();
            input_competition = mSpinnerCompetition.getSelectedItem().toString();
            sortBy(input_competition, input_division, input_category);

            //sort by two parameters
        }

    }

    public void sortBy(String a) {
        ArrayList<Athlete> athletesSortedList = new ArrayList<>();

        for(Athlete athlete: allAthletes){
            if(athlete.getCompetition().getName().toLowerCase().equalsIgnoreCase(a) ||
            athlete.getCompetition().getDevision().getName().toLowerCase().equalsIgnoreCase(a) ||
            athlete.getCompetition().getDevision().getCategory().getName().toLowerCase().equalsIgnoreCase(a)){
                athletesSortedList.add(athlete);
            }

        }

        setTemporaryAdapter(athletesSortedList);
    }

    public void sortByComDiv(String competition, String division) {
        ArrayList<Athlete> athletesSortedList = new ArrayList<>();

        for(Athlete athlete: allAthletes){
            if(athlete.getCompetition().getName().toLowerCase().equalsIgnoreCase(competition)
                    && athlete.getCompetition().getDevision().getName().toLowerCase().equalsIgnoreCase(division)){
                athletesSortedList.add(athlete);
            }
        }

        setTemporaryAdapter(athletesSortedList);
    }

    public void sortByComCat(String competition, String category) {
        ArrayList<Athlete> athletsSortedList = new ArrayList<>();

        for(Athlete athlete: allAthletes){
            if(athlete.getCompetition().getName().toLowerCase().equalsIgnoreCase(competition)
                    && athlete.getCompetition().getDevision().getCategory().getName().toLowerCase().equalsIgnoreCase(category)){
                athletsSortedList.add(athlete);
            }
        }

        setTemporaryAdapter(athletsSortedList);
    }

    public void sortBy(String competition, String division, String category) {
        ArrayList<Athlete> athletesSortedList = new ArrayList<>();
        for (Athlete athlete : allAthletes) {
            if (athlete.getCompetition().getName().toLowerCase().equalsIgnoreCase(competition)
                    && athlete.getCompetition().getDevision().getName().toLowerCase().equalsIgnoreCase(division) &&
                    athlete.getCompetition().getDevision().getCategory().getName().toLowerCase().equalsIgnoreCase(category)) {
                athletesSortedList.add(athlete);
            }
        }

        setTemporaryAdapter(athletesSortedList);

    }

    public void setTemporaryAdapter(ArrayList list){
        RecyclerView.Adapter temporaryAdapter = new LineAdapter(AllAthletesActivity.this, list);
        allAthletsRView.setAdapter(temporaryAdapter);
    }

    public void setSpinnerCompetition() {
        ArrayList<String> competition_array = new ArrayList<>();
        competition_array.add("All");
        competition_array.add("Pole Sport");
        competition_array.add("Artistic Pole");
        competition_array.add("Aerial Sport");
        competition_array.add("Para Pole");
        competition_array.add("Ultra Pole");

        ArrayAdapter<String> comp_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, competition_array);
        mSpinnerCompetition.setAdapter(comp_adapter);

    }

    public void setCategorySpinner(String competition) {

        ArrayList<String> category_array = new ArrayList<>();

        switch (competition) {
            case "Pole Sport":
            case "Aerial Sport":
            case "Para Pole":
                category_array.add("All");
                category_array.add("Pre-novice (6-9)");
                category_array.add("Novice female (10-14)");
                category_array.add("Novice male (10-14)");
                category_array.add("Junior female (15-17)");
                category_array.add("Junior male (15-17)");
                category_array.add("Senior woman (18-39)");
                category_array.add("Senior man (18-39)");
                category_array.add("Masters woman (40+)");
                category_array.add("Masters man (40+)");
                category_array.add("Masters woman (50+)");
                category_array.add("Masters man (50+)");
                category_array.add("Youth doubles (10-17)");
                category_array.add("Senior double m/m");
                category_array.add("Senior double w/m");
                category_array.add("Senior double w/w");

                break;
            case "Artistic Pole":
                category_array.add("All");
                category_array.add("Junior (14-17)");
                category_array.add("Senior Woman (18+)");
                category_array.add("Senior Man (18+)");
                category_array.add("Doubles (18+)");
                category_array.add("Masters Woman (40+)");
                category_array.add("Masters Man (40+)");
                break;
            case "Ultra Pole":
                category_array.add("Elite");
        }

        ArrayAdapter<String> cat_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, category_array);
        mSpinnerCategory.setAdapter(cat_adapter);
    }

    public void setDivisionSpinner(String competition) { //sets up division depending on the competition

        ArrayList<String> val_array = new ArrayList<>();//arrayList for division spinner

        switch (competition) {
            case "Pole Sport":
            case "Aerial Sport":
            case "Para Pole":
                val_array.add("All");
                val_array.add("Amateur");
                val_array.add("Professional");
                val_array.add("Elite");
                break;
            case "Artistic Pole":
                val_array.add("All");
                val_array.add("Amateur");
                val_array.add("Semi-Professional");
                val_array.add("Professional");
                break;
            case "Ultra Pole":
                val_array.add("Elite");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, val_array);
        mSpinnerDivision.setAdapter(adapter);

    }

    private void saveData() {
        //sparar allAthletes list lokalt i appen
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(allAthletes);
        editor.putString("all athletes", json);
        editor.apply();

    }

    public void loadData() {
        //laddar upp allAthletes list med alla sparade atleter lokalt från appen
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("all athletes", null);
        Type type = new TypeToken<ArrayList<Athlete>>() {
        }.getType();
        allAthletes = gson.fromJson(json, type);

        if (allAthletes == null) {
            allAthletes = new ArrayList<>();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        saveData();
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        finish();
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("Athletes_list", allAthletes);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allAthletes = savedInstanceState.getParcelableArrayList("Athletes_list");
    }

    @Override
    public void onBackPressed() {
        saveData();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        super.onActivityResult(requestCode, resultCode, resultIntent);

        if (requestCode == NEW_ATHLETE_REQUEST) {
            if (resultCode == RESULT_OK && resultIntent != null) {

                String athleteName = resultIntent.getStringExtra("Athlete_name");
                String athleteCategory = resultIntent.getStringExtra("Athlete_category");
                Boolean feeStatus = resultIntent.getExtras().getBoolean("Athlete_fee_status");
                String athleteDivision = resultIntent.getStringExtra("Athlete_division");
                String competitionString = resultIntent.getStringExtra("Athlete_competition");


                updateList(athleteName, competitionString, athleteDivision, athleteCategory, feeStatus);

                saveData();
                loadData();
                updateAdapter();
            }
        }

    } //onActivityResult

    public void createCompetitionsList() {

        //creates a list with all 5 competitions
        allCompetitions = new ArrayList<>();
        allCompetitions.add(new Competition("Para Pole"));
        allCompetitions.add(new Competition("Ultra Pole"));
        allCompetitions.add(new ArtisticPole("Artistic Pole"));
        allCompetitions.add(new AerialSport("Aerial Sport"));
        allCompetitions.add(new PoleSport("Pole Sport"));

    }

    public void updateAdapter() {
        allAthletsRView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LineAdapter(this, allAthletes);
        allAthletsRView.setAdapter(mAdapter);


    }

    public void updateList(String aName, String competitionName, String athleteDivision, String bCategory, Boolean cFee) {

        //adds new athlete with data from AddAthleteActivity

        //titta om Athlete i samma tävling redan finns
        Athlete athlete = new Athlete();
        athlete.setName(aName);

        //ArrayList<Division> divisionsList=new ArrayList<>();

        // sets Competition
        for (int i = 0; i < allCompetitions.size(); i++) {
            if (allCompetitions.get(i).getName().equalsIgnoreCase(competitionName)) {
                athlete.setCompetition(allCompetitions.get(i));
                //sets division for competition
            }
        }

        //sets division
        athlete.getCompetition().setDivision(new Division(athleteDivision));
        //check that that devision doesn't exist yet

        //adding category
        athlete.getCompetition().getDevision().setCategory(new Category(bCategory));

        //set competition fee
        athlete.setCompFeepaid(cFee);

        allAthletes.add(athlete);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


}

