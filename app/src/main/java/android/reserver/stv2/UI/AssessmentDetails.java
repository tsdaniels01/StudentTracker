package android.reserver.stv2.UI;

import android.content.Intent;
import android.os.Bundle;
import android.reserver.stv2.Database.Repository;
import android.reserver.stv2.Entities.Assessment;
import android.reserver.stv2.R;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;

/**
 * This class is used to control the activity assessment details layout. It extends the
 * AppCompatActivity class and implements the AdapterView.OnItemSelectedListener for utilizing a
 * spinner widget.
 */
public class AssessmentDetails extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    EditText editName; // creates an EditText variable to store the editName
    Spinner editType; // creates a spinner variable to store the editType
    EditText editStart; // creates an EditText variable to store the editStart
    EditText editEnd; // creates an EditText variable to store the editEnd
    String type; // creates a variable to store the type
    List<Assessment> allAssessments; // creates a list variable to hold the assessments
    int currentID; // creates an integer variable to hold the currentID
    Assessment currentAssessment; // creates an assessment object to hold the currentAssessment
    Repository repository; // creates a repository object to hold the repository
    private int courseID; // creates an integer to hold the courseID
    int loopCount = 0; // creates an integer variable to hold the loopCount

    /**
     * This method is the startup for the activity and associates the activity with a ViewModel as
     * well as binds data to class-scope variables
     * @param savedInstanceState saves the state of the activity for reloading
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call to the parent class constructor
        // sets the layout view
        setContentView(R.layout.activity_assessment_details);
        // sets up an Action Bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // instantiates Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setAssessment(); // calls method to populate activity when an item has been sent
        setSpinner(); // calls method to populate spinner with items

    }
    /**
     * This method is used as a callback for the selected item in the editType spinner
     * @param parent the AdapterView where it was selected
     * @param view the view is the widget used
     * @param position the position of the item selected
     * @param id the row id of selection
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // assigns the String value of the position to the type variable
        type = parent.getItemAtPosition(position).toString();
    }
    /**
     * This method is used when a selection is not present in the editType spinner
     * @param parent the adaptorView with no selection
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This method is used for the Action Bar menu and navigates to the previous screen when
     * clicked or allows other options to be selected
     * @param item the menu option selected
     * @return true for the selected option
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { // switch branch statement
            case android.R.id.home: // if home option was selected
                this.finish(); // finishes selection
                return true; // returns true
            case R.id.delete: // if delete option was selected
                deleteAssessment(); // calls method to delete the current assessment
                return true; // returns true
            case R.id.notify: // if notify option was selected
                alertDetails(); // calls method to send to alertDetails activity
                return true; // returns true

        }
        return super.onOptionsItemSelected(item); // returns parent class of item selected

    }
    /** This method is used to add the menu assessment details to the layout
     * @param menu is the menu to be added
     * @return true for the boolean condition
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); // creates a class variable menuInflater
        inflater.inflate(R.menu.menu_assessment_details, menu); // assigns inflater to inflate menu
        return true; // returns true
    }
    /**
     * This method is used to create an intent for the alertDetails activity layout. It validates
     * that an object exist before adding notes.
     */
    public void alertDetails(){
        // creates a integer variable to hold the currentID
        int id = getIntent().getIntExtra("id", -1);
        // assigns the courseNameEditText to the class variable editName
        editName = findViewById(R.id.assessmentNameEditText);
        // creates a variable to hold the text from the editName variable
        String newName = editName.getText().toString();
        // branch statement to verify that an object exist or that the editText field is not empty
        if (id == -1 || newName.isEmpty()){
            // creates a toast message to inform user of error
            Toast.makeText(getApplicationContext(), "An assessment must be saved before " +
                    "setting notifications!", Toast.LENGTH_LONG).show();
        }
        else{// if an object exists
            // creates an intent variable to AlertDetails
            Intent intent = new Intent(AssessmentDetails.this, android.reserver.stv2.UI.AlertDetails.class);
            // creates an extra for the intent passing the currentName
            intent.putExtra("assessmentName", currentAssessment.getAssessmentName());
            // starts the activity
            startActivity(intent);
        }
    }
    /**
     * This method is used to retrieve the data from the layout and save it to the database when
     * the save button is selected.
     * @param view the view that contains the data
     */
    public void saveAssessment(View view) {
        // creates an integer variable to hold the size of the allCourses List
        int idCount = allAssessments.get(allAssessments.size()-1    ).getAssessmentID() ;
        // creates an integer variable to hold the currentCourseID if a term has been sent
        int currentCourseID = getIntent().getIntExtra("currentCourseID", -1 );
        // assigns editText field to class variable
        editName = findViewById(R.id.assessmentNameEditText);
        // assigns editText field to class variable
        editStart = findViewById(R.id.assessmentStartEditText);
        // assigns editText field to class variable
        editEnd = findViewById(R.id.assessmentEndEditText);
        // assigns editText field to class variable
        editType = findViewById(R.id.TypeSpinner);
        // creates String variable to hold the data from the editText field
        String newName = editName.getText().toString();
        // creates String variable to hold the data from the editText field
        String newStart = editStart.getText().toString();
        // creates String variable to hold the data from the editText field
        String newEnd = editEnd.getText().toString();
        // branch statement to determine if the user is saving multiple assessments at a time
        if (loopCount != 0) // keeps track of idCount
            idCount = loopCount;
        try { // Possible NullPointerException
            // branch statement to verify that a name has been given
            if (newName.isEmpty()) {
                // toast message to inform user of error
                Toast.makeText(getApplicationContext(), "The assessment must have a name " +
                        "before saving!", Toast.LENGTH_LONG).show();
            }
            else { // if a name has been given
                // branch statement to determine if the object already exists
                if (!checkAssessment(currentID)) { // if object does not exist calls insert method
                    // branch statement to determine if a courseID is attached to the object
                    // this is needed for when a user deletes an object and creates another before
                    // refreshing
                    if (courseID <= 0) { // if there is no courseID it uses the currentCourseID
                        Assessment newAssessment = new Assessment(++idCount, newName, type,
                                newStart, newEnd, currentCourseID);
                        repository.insert(newAssessment); // calls insert method
                        // toast message to confirm save
                        Toast.makeText(getApplicationContext(), "The assessment was saved!",
                                Toast.LENGTH_LONG).show();
                        loopCount = idCount; // initializes class variable to keep active idCount
                    }
                    else {
                        // creates a new course object with parameters to be inserted into database
                        Assessment newAssessment = new Assessment(++idCount, newName, type,
                                newStart, newEnd, courseID);
                        repository.insert(newAssessment); // calls insert method
                        // toast message to confirm save
                        Toast.makeText(getApplicationContext(), "The assessment was saved!",
                                Toast.LENGTH_LONG).show();
                        loopCount = idCount; //initializes class variable to keep active idCount
                    }
                } else { // if the object already exists calls update method
                    Assessment oldAssessment = new Assessment(currentID, newName, type, newStart,
                            newEnd, currentAssessment.getCourseID());
                    repository.update(oldAssessment); // calls update method
                    // toast message to confirm update
                    Toast.makeText(getApplicationContext(), "The assessment was updated!",
                            Toast.LENGTH_LONG).show();
                }

            }
        }
        catch (NullPointerException e){ // catches possible exception
            e.printStackTrace(); // prints exception to stack
        }
        editName.setText(""); // clears editText
        editStart.setText(""); // clears editText
        editEnd.setText(""); // clears editText
        editType.setSelection(0); // sets spinner back to 0
    }
    /**
     * This method is used to set the assessment data in the proper editText fields when sent from
     * previous activity
     */
    public void setAssessment(){
        // assigns the id to the class variable
        currentID = getIntent().getIntExtra("id", -1);
        // instantiates a new repository
        repository = new Repository(getApplication());
        // assigns the assessments from the repository to the allAssessments list
        allAssessments = repository.getAllAssessments();
        // uses the currentID to locate the currentAssessment object
        for (Assessment assessment:allAssessments){ // gets all assessments
            if(assessment.getAssessmentID() == currentID) // matches assessmentIDs
                currentAssessment = assessment; // assigns the located object to the variable
        }
        // verifies that an object exists to set
        if (currentAssessment!=null) {
            // assigns editText field to class variable
            editName = findViewById(R.id.assessmentNameEditText);
            // assigns editText field to class variable
            editStart = findViewById(R.id.assessmentStartEditText);
            // assigns editText field to class variable
            editEnd = findViewById(R.id.assessmentEndEditText);
            // sets the editText field with the data from the currentAssessment object
            editName.setText(currentAssessment.getAssessmentName());
            // sets the editText field with the data from the currentAssessment object
            editStart.setText(currentAssessment.getAssessmentStart());
            // sets the editText field with the data from the currentAssessment object
            editEnd.setText(currentAssessment.getAssessmentEnd());
        }

    }
    /**
     * This method is used to check if an assessment already exists
     * @param id the id of the assessment to be checked
     * @return boolean value
     */
    public boolean checkAssessment (int id){
         // retrieves all assessments
        for(Assessment assessment:repository.getAllAssessments()){
            if(assessment.getAssessmentID() == id) // see if id parameter matches
                return true; // true if does
        }return false; // false if doesn't
    }

    /**
     * This method is used to create and set a spinner in the layout
     */
    public void setSpinner(){
        // assigns the typeSpinner to the editType variable
        editType = findViewById(R.id.TypeSpinner);
        // creates an adaptor for the arrayList and spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayType, android.R.layout.simple_spinner_item);
        // assigns the desired layout to the adaptor
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // sets the adaptor tot the spinner variable
        editType.setAdapter(adapter);
        // sets the listener to the spinner variable
        editType.setOnItemSelectedListener(this);
        // branch statement to set the spinner to the desired position
        if(checkAssessment(currentID)){
            // assigns the intent extra type to a variable
            CharSequence s = getIntent().getStringExtra("type");
            // creates an integer variable to hold the position of the spinner
            int spin = adapter.getPosition(s);
            // sets the spinner to the desired selection
            editType.setSelection(spin);
        }
    }
    /**
     * This method is used to delete an assessment and clear the editText fields after
     */
    public void deleteAssessment(){
        // creates an integer variable to hold the current id
        int id = getIntent().getIntExtra("id", -1);
        // branch statement to verify that an object exist to be deleted
        if (id == -1){
            // toast message to inform the user of error
            Toast.makeText(getApplicationContext(), "There is no assessment to delete!",
                    Toast.LENGTH_LONG).show();
        }
        else{ // if an object exist to delete
            // captures the courseID of the course before deleting for when the user creates a new
            // object before refreshing
            courseID = currentAssessment.getCourseID();
            repository.delete(currentAssessment); // deletes the assessment from the repository
            // toast message confirming deletion
            Toast.makeText(getApplicationContext(), "The assessment was deleted!",
                    Toast.LENGTH_LONG).show();
            editName.setText(""); // clears the editText fields
            editStart.setText(""); // clears the editText fields
            editEnd.setText(""); // clears the editText fields
            editType.setSelection(0); // sets the spinner to 0
        }
    }


}