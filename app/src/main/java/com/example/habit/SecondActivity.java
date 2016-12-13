package com.example.habit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SecondActivity extends MainActivity {

    HabitDbHelper habitDb;

    //Intialize string variables
    String Habit, Time, Frequency;
    //Inialize EditText boxes and button
    public EditText InputHabit;
    private EditText InputTime;
    private EditText InputFrequency;
    private Button AddHabit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Display activity_second layout
        setContentView(R.layout.activity_second);

        //create the database for habits
        habitDb = new HabitDbHelper(this);


        //Variables assigned based on ID
        InputHabit = (EditText) findViewById(R.id.InputHabit);
        InputTime = (EditText) findViewById(R.id.InputTime);
        InputFrequency = (EditText) findViewById(R.id.InputFrequency);
        AddHabit = (Button) findViewById(R.id.AddHabit);

        //User fills in information and adds habit
        AddHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Convert EditText to String value
                final String inHabit = InputHabit.getText().toString();
                final String inTime = InputTime.getText().toString();
                final String inFrequency = InputFrequency.getText().toString();


                Habit = inHabit;
                Time = inTime;
                Frequency = inFrequency;


                //add data to the database
                boolean worked = habitDb.insertData(inHabit, inTime, inFrequency);



                //Uses Intent to send Strings to MainActivity
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                intent.putExtra("HabitInput", Habit);
                intent.putExtra("TimeInput", Time);
                intent.putExtra("FrequencyInput", Frequency);
                startActivity(intent);

            }
        });


    }



}
