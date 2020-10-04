package com.anna0520.sm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddAthleteActivity extends AppCompatActivity {


    private Spinner mDivisionSpinner;
    private Spinner mCategorySpinner;
    private EditText mAthName;
    private EditText mAthCategory;//change to spinner
    private Button mSave;
    private CheckBox mCompFeepaidCheck;
    private String mName;
    private String mCategory;
    private String mCompetition;
    private String mDivision;
    private boolean mFeePaid;
    private RadioGroup competitionsGroup;

    private static final String ATHLETE_NAME="Athlete_name";
    private static final String ATHLETE_CATEGORY="Athlete_category";
    private static final String ATHLETE_FEE_STATUS="Athlete_fee_status";
   private static final String ATHLETE_COMPETITION="Athlete_competition";
    private static final String ATHLETE_DIVISION="Athlete_division";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_athlete_activity);

        //back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FA270A5A"))); //supportActionbar to violet color

        mDivisionSpinner=(Spinner)findViewById(R.id.division_spinner) ;
        mDivisionSpinner.setEnabled(false);//not able to choose division before chosen the competion
        mCategorySpinner=(Spinner)findViewById(R.id.category_spinner);
        mCategorySpinner.setEnabled(false);//can't choose category before choosing the competition
        mAthName=(EditText)findViewById(R.id.name_of_athlet);
        mCompFeepaidCheck = (CheckBox) findViewById(R.id.paid_comp_fee_button);
        mSave= (Button) findViewById(R.id.save_button);

        //buttongroup
        competitionsGroup=(RadioGroup)findViewById(R.id.competition);
        competitionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.sport_category:
                            //set categories i sipper
                            // categories spinner active
                        RadioButton radioButton = (RadioButton) findViewById(checkedId);
                        Toast.makeText(AddAthleteActivity.this,
                                radioButton.getText(), Toast.LENGTH_SHORT).show();
                        mCompetition=radioButton.getText().toString();
                            break;
                    case R.id.artistic_category:
                            //set categories i sipper
                            // categories spinner active
                        RadioButton radioButton2 = (RadioButton) findViewById(checkedId);
                        Toast.makeText(AddAthleteActivity.this,
                                radioButton2.getText(), Toast.LENGTH_SHORT).show();
                        mCompetition=radioButton2.getText().toString();
                            break;
                    case R.id.aerial_Sport_category:
                            //set categories i sipper
                            // categories spinner active
                        RadioButton radioButton3 = (RadioButton) findViewById(checkedId);
                        Toast.makeText(AddAthleteActivity.this,
                                radioButton3.getText(), Toast.LENGTH_SHORT).show();
                        mCompetition=radioButton3.getText().toString();
                            break;
                    case R.id.ultra_pole_category:
                            //no division
                            //no category
                        RadioButton radioButton4 = (RadioButton) findViewById(checkedId);
                        Toast.makeText(AddAthleteActivity.this,
                                radioButton4.getText(), Toast.LENGTH_SHORT).show();
                        mCompetition=radioButton4.getText().toString();
                            break;
                    case R.id.para_pole:
                            //same division as sport
                            //only one category--> para pole
                        RadioButton radioButton5 = (RadioButton) findViewById(checkedId);
                        Toast.makeText(AddAthleteActivity.this,
                                radioButton5.getText(), Toast.LENGTH_SHORT).show();
                        mCompetition=radioButton5.getText().toString();
                            break;
                }

           setmDivisionSpinner(mCompetition); //sets division spinner depending on the competition choosen
            mDivisionSpinner.setEnabled(true);//can now choose from spinner
           setCategorySpinner(mCompetition);//sets category depending on competition
           mCategorySpinner.setEnabled(true);//can choose the category now
            }
        });

       //when the division is choosen category apears
        mDivisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(

        ) {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDivision= mDivisionSpinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//when the category is chosen
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategory=mCategorySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getInputInfo();
                createIntent();
                finish(); //activity ends and on ResultIntent with athlet's info sends to AllAthletesActivity
            }
        });
    }

    public void setCategorySpinner(String competition){

     ArrayList <String> category_array=new ArrayList<>();

        switch(competition){
            case "Pole Sport":
            case "Aerial Sport":
            case "Para Pole":
                category_array.add("Pre-novice (6-9)") ;
                category_array.add("Novice female (10-14)") ;
                category_array.add("Novice male (10-14)");
                category_array.add("Junior female (15-17)") ;
                category_array.add("Junior male (15-17)") ;
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
                category_array.add("Junior (14-17)") ;
                category_array.add("Senior Woman (18+)") ;
                category_array.add("Senior Man (18+)") ;
                category_array.add("Doubles (18+)") ;
                category_array.add("Masters Woman (40+)" );
                category_array.add("Masters Man (40+)") ;
                break;
            case "Ultra Pole":
                category_array.add("Elite");}

      ArrayAdapter<String> cat_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,category_array);
      mCategorySpinner.setAdapter(cat_adapter);
    }

    public void setmDivisionSpinner(String competition){ //sets up division depending on the competition

        ArrayList<String> val_array=new ArrayList<>();//arrayList for division spinner

       switch(competition){
          case "Pole Sport":
          case "Aerial Sport":
          case "Para Pole":
            val_array.add("Amateur") ;
            val_array.add("Professional") ;
            val_array.add("Elite");
            break;
          case "Artistic Pole":
               val_array.add("Amateur") ;
               val_array.add("Semi-Professional") ;
               val_array.add("Professional") ;
               break;
           case "Ultra Pole":
                   val_array.add("Elite");
       }

       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, val_array);
       mDivisionSpinner.setAdapter(adapter);

    }

   @Override
   public void onSaveInstanceState (Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("Athlete_name",mName);
        savedInstanceState.putString("Athlete_category",mCategory);
        savedInstanceState.putBoolean("Athlete_fee_status",mFeePaid);
        savedInstanceState.putString("Athlete_competition",mCompetition);
        savedInstanceState.putString("Athlete_division",mDivision);
   }

   @Override
   public void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
       mName=savedInstanceState.getString("Athlete_name");
       mCategory=savedInstanceState.getString("Athlete_category");
       mFeePaid=savedInstanceState.getBoolean("Athlete_fee_status");
       mCompetition=savedInstanceState.getString("Athlete_competition");
       mDivision=savedInstanceState.getString("Athlete_division");
   }


    public boolean onOptionsItemSelected(MenuItem item){
       //when user chooses to go back to menu, the imput doest save
        Intent myIntent = new Intent(getApplicationContext(), AllAthletesActivity.class);
        setResult(AddAthleteActivity.RESULT_CANCELED,myIntent);
        finish();
        return true;
    }

    private void getInputInfo(){
      //hämtar information som användare matat in
        mName=mAthName.getText().toString();
        mFeePaid=mCompFeepaidCheck.isChecked();

    }

    private void createIntent(){
 // information som skickas tillbaka till "AllAthletesActivity"
        Intent myIntent = new Intent();
       myIntent.putExtra("Athlete_name",mName);
        myIntent.putExtra("Athlete_category",mCategory);
        myIntent.putExtra("Athlete_fee_status",mFeePaid);
        myIntent.putExtra("Athlete_competition",mCompetition);
        myIntent.putExtra("Athlete_division",mDivision);
        setResult(AddAthleteActivity.RESULT_OK,myIntent);

    }

}
