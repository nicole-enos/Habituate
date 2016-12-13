package com.example.habit;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends AppCompatActivity {
    private ListView mHabitListView;
    private ArrayAdapter<String> mAdapter;
    private static final String TAG = "MainActivity";
    HabitDbHelper habitDb;

    //Button goes to SecondActivity screenl
    private Button nexthabit;
    //Button displays habit user inputteldl
    private Button ShowHabit;
    //Initializes variables and TextView boxesl
    private TextView HabitDisplay1, TimeDisplay1, FrequencyDisplay1;
    String HabitInput, TimeInput, FrequencyInput;

    private Button DeleteButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Display activity_main layout
        setContentView(R.layout.activity_main);

        //mHabitListView = (ListView) findViewById(R.id.list_habits);

        //create the database for habits
        habitDb = new HabitDbHelper(this);

        //Variables assigned based on ID
        nexthabit = (Button) findViewById(R.id.nexthabit);
        ShowHabit = (Button) findViewById(R.id.ShowHabit);
        DeleteButton = (Button) findViewById(R.id.DeleteButton);
        HabitDisplay1 = (TextView) findViewById(R.id.HabitDisplay1);
        TimeDisplay1 = (TextView) findViewById(R.id.TimeDisplay1);
        FrequencyDisplay1 = (TextView) findViewById(R.id.FrequencyDisplay1);

        //Receive and assign Strings from Intent
        Intent intent = getIntent();
        HabitInput = intent.getStringExtra("HabitInput");
        TimeInput = intent.getStringExtra("TimeInput");
        FrequencyInput = intent.getStringExtra("FrequencyInput");


        //User clicks button to go to SecondActivity screen
        nexthabit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int x = 14;
                int y = 00;
                int z = 30;

                calendar.set(Calendar.HOUR_OF_DAY, x);
                calendar.set(Calendar.MINUTE, y);
                calendar.set(Calendar.SECOND, z);

                Intent intent1 = new Intent(getApplicationContext(), Notification_receiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                long length = habitDb.getLength();
                if(length < 5) {
                    startActivity(new Intent(getApplicationContext(), SecondActivity.class));
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "5 is the max!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        //Sets the text of TextView boxes to user input
        ShowHabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HabitDisplay1.setText(HabitInput);
                //TimeDisplay1.setText(TimeInput);
                //FrequencyDisplay1.setText(FrequencyInput);
                Cursor res = habitDb.getAllData();
                if (res.getCount() == 0) {
                    // show message
                    showMessage("Error", "Nothing found");

                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Habit: " + res.getString(0) + "\n");
                    buffer.append("Time: " + res.getString(1) + "\n");
                    buffer.append("Frequency: " + res.getString(2) + "\n\n");
                }

                // Show all data
                showMessage("Data", buffer.toString());

            }

        });

        //When user chooses to delete
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deleted = habitDb.deleteData();
                Context context = getApplicationContext();
                CharSequence text = "Habits deleted!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }


        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
};

