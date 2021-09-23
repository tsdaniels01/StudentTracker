package android.reserver.stv2.UI;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.reserver.stv2.R;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is a controller for the alert details activity. It is utilized for setting up
 * notifications for courses and assessment objects. It extends the AppCompatActivity class and
 * implements an OnItemSelectedListener for a spinner widget.
 */
public class AlertDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static int numAlert; // creates a static variable to store the numAlert
    EditText editTitle; // creates a variable to store the EditText editTitle
    EditText editStart; // creates a variable to store the EditText editStart
    Spinner editStatus; // creates a spinner variable to store the editStatus
    String assessmentTitle; // creates a variable to store the assessmentTitle
    String courseTitle; // creates a variable to store the courseTitle
    String status; // creates a variable to store the status
    Long newStartDate; // creates a Long variable to store the newStartDate

    /**
     * This method is the startup for the activity and associates the activity with a ViewModel as
     * well as binds data to class-scope variables
     * @param savedInstanceState saves the state of the activity for reloading
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call to the parent class constructor
        setContentView(R.layout.activity_alert_details); // sets the layout view
        // sets up an Action Bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // instantiates Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // assigns the EditTextAlertTitle to the editTitle variable
        editTitle = findViewById(R.id.editTextAlertTitle);
        // assigns extra courseName to the courseTitle variable
        courseTitle = getIntent().getStringExtra("courseName");
        // assigns extra assessmentName to the assessmentTitle variable
        assessmentTitle = getIntent().getStringExtra("assessmentName");
        // branches to determine if a courseTitle or AssessmentTitle was sent and sets the Text
        if (courseTitle == null) // if courseTitle is null sets the opposite
            editTitle.setText(assessmentTitle);
        else // else sets courseTitle
            editTitle.setText(courseTitle);
        // assigns the AlertSpinner to the editStatus variable
        editStatus = findViewById(R.id.AlertSpinner);
        // creates an adapter for the xml array and sets up the simple spinner item layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayAlert, android.R.layout.simple_spinner_item);
        // instantiates the adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // sets the adapter to the editStatus spinner variable
        editStatus.setAdapter(adapter);
        // sets up the OnItemSelectedListener to the editStatus variable
        editStatus.setOnItemSelectedListener(this);
    }

    /**
     * This method is used as a callback for the selected item in the editStatus spinner
     * @param parent the AdapterView where it was selected
     * @param view the view is the widget used
     * @param position the position of the item selected
     * @param id the row id of selection
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // assigns the String value of the position to the status variable
        status = parent.getItemAtPosition(position).toString();
    }

    /**
     * This method is used when a selection is not present in the editStatus spinner
     * @param parent the adaptorView with no selection
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This method is used for the Action Bar menu and navigates to the previous screen when
     * clicked
     * @param item the menu option selected
     * @return true for the boolean condition
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { // switch branch statement
            case android.R.id.home: // if home option was selected
                this.finish(); // finishes the selection
                return true; // returns true

        }
        return super.onOptionsItemSelected(item); // returns parent class of item selected

    }

    /**
     * This method is used with a button to confirm the alert and retrieves the inputted data to
     * set up the notification
     * @param view the widget that is used
     */
    public void confirmAlert(View view) {
        // assigns the editTextAlertTitle to the editTitle variable
        editTitle = findViewById(R.id.editTextAlertTitle);
        // assigns the editTextStart to the editStart variable
        editStart = findViewById(R.id.editTextStart);
        // retrieves the String value of editTitle and assigns it to a String variable
        String alertTitle = editTitle.getText().toString();
        // retrieves the String value of editStart and assigns it to a String variable
        String startDate = editStart.getText().toString();
        // sets up a regex format for the startDate
        String regex = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        // creates a pattern variable for the regex format
        Pattern p = Pattern.compile(regex);
        // creates a matcher for the pattern
        Matcher m = p.matcher(startDate);
        // creates a variable to hold the matcher results
        boolean match = m.matches();
        // creates a branch statement for the startDate variable
        if (startDate.isEmpty()) {
            // returns a toast message if the startDate is empty
            Toast.makeText(getApplicationContext(), "A date must be entered to create a " +
                    "notification!", Toast.LENGTH_LONG).show();
        } else if (!match) {
            // returns a toast message if the match is false
            Toast.makeText(getApplicationContext(), "A valid date must be entered to create " +
                    "a notification. Please follow the pattern provided.", Toast.LENGTH_LONG)
                    .show();
            editStart.setText(""); // clears the editStart to provide the hint pattern again
        } else {
            // if pattern matches
            String s = status;  // creates a String variable to store the status position
            // creates a String variable to present message for the notification
            String message = alertTitle + " is " + s;
            // creates a String to format the date
            String dateFormat = "MM/dd/yy";
            // creates a SimpleDateFormat for the dateFormat variable
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat, Locale.US);
            // creates an intent for the MyReceiver class
            Intent intent = new Intent(AlertDetails.this, MyReceiver.class);
            // provides the message as an extra passed to the receiver
            intent.putExtra("title", message);
            // creates a pendingIntent and assigns it to the broadcast
            PendingIntent sender = PendingIntent.getBroadcast(AlertDetails.this, ++numAlert,
                    intent, 0);
            // creates an instance of AlarmManager for the notification
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Date start = null; // creates an instance of the date variable
            try { // possible parse exception
                // creates an instance of the start variable to parse the date to format
                start = simpleDateFormat.parse(startDate);
            } catch (ParseException e) { // catches exception
                e.printStackTrace(); // prints exception
            }
            // creates an instance of newStartDate and assigns it the start variable
            newStartDate = start.getTime();
            // instantiates the alarmManager to notify on provided newStartDate
            alarmManager.set(AlarmManager.RTC_WAKEUP, newStartDate, sender);
            // toast message for confirmation
            Toast.makeText(getApplicationContext(), "The notification has been set!",
                    Toast.LENGTH_LONG).show();
            editTitle.setText("");
            editStart.setText("");
            editStatus.setSelection(0);
        }
    }
}