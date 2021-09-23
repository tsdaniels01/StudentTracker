package android.reserver.stv2.UI;

import android.content.Intent;
import android.os.Bundle;
import android.reserver.stv2.Database.Repository;
import android.reserver.stv2.Entities.Course;
import android.reserver.stv2.Entities.Term;
import android.reserver.stv2.R;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to control the activity course list layout. It extends the
 * AppCompatActivity class.
 */
public class CourseList extends AppCompatActivity {

    EditText editName; // creates an EditText variable to store the editName
    EditText editStart; // creates an EditText variable to store the editStart
    EditText editEnd; // creates an EditText variable to store the editEnd
    int currentID; // creates an integer variable to hold the course currentID
    List<Term>  allTerms; // creates a list variable to hold all of the terms
    Term currentTerm; // creates a term object to hold the currentTerm
    Repository repository; // creates a repository object to hold a general repository
    RecyclerView recyclerView; // creates a recyclerView variable to hold the recyclerView
    int loopCount = 0; // creates a class variable to hold the loopCount

    /**
     * This method is the startup for the activity and associates the activity with a ViewModel as
     * well as binds data to class-scope variables
     * @param savedInstanceState saves the state of the activity for reloading
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); // call to the parent class constructor
        setContentView(R.layout.activity_course_list); // sets the layout view
        // sets up an Action Bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // instantiates Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTerm(); // calls method to populate activity when an item has been sent
        refreshList(); // calls to initialize recycleView list
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
            case R.id.refresh: // if refresh option was selected
                refreshList(); // calls method to refresh recycleView list
                // toast message to confirm refresh
                Toast.makeText(getApplicationContext(), "Refreshed!", Toast.LENGTH_LONG).show();
                return true; // returns true
            case R.id.delete: // if delete option was selected
                deleteTerm(); // calls method to delete the current term
                return true; // returns true

        }
        return super.onOptionsItemSelected(item); // returns parent class of item selected

    }
    /** This method is used to add the menu course list to the layout
     * @param menu is the menu to be added
     * @return true for the boolean condition
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); // creates a class variable menuInflater
        inflater.inflate(R.menu.menu_course_list, menu); // assigns inflater to inflate menu
        return true; // returns true
    }

    /**
     * This method is used create an intent for the assessmentList activity layout. It validates
     * that an object exist before adding assessments
     * @param view the view to be loaded
     */
    public void assessmentList(View view) {
        // creates a integer variable to hold the currentID
        int id = getIntent().getIntExtra("id", -1);
        // branch statement to validate that an object exists first before proceeding
        if(id == -1){
            // creates a toast message to inform user of error
            Toast.makeText(getApplicationContext(), "You must save the term before" +
                    " adding a course!", Toast.LENGTH_LONG).show();
        }
        else { // if an object exists
            // creates an intent variable to AssessmentList
            Intent intent = new Intent(CourseList.this, android.reserver.stv2.UI.AssessmentList.class);
            // creates an extra for the intent passing the currentTermID
            intent.putExtra("currentTermID", currentTerm.getTermID());
            // starts the activity
            startActivity(intent);
        }
    }
    /**
     * This method is used to retrieve the data from the layout and save it to the database when
     * the save button is selected.
     * @param view the view that contains the data
     */
    public void saveTerm (View view)    {
        // creates an integer variable to hold the size of the allCourses List
        int idCount = allTerms.get(allTerms.size()-1).getTermID();
        // creates an integer variable to hold the currentTermID if a term has been sent
        int i = getIntent().getIntExtra("id", -1);
            // assigns editText field to class variable
            editName = findViewById(R.id.editTextTermName);
            // assigns editText field to class variable
            editStart = findViewById(R.id.editTextStart);
            // assigns editText field to class variable
            editEnd = findViewById(R.id.editTextEnd);
            // creates String variable to hold the data from the editText field
            String screenName = editName.getText().toString();
            // creates String variable to hold the data from the editText field
            String screenStart = editStart.getText().toString();
            // creates String variable to hold the data from the editText field
            String screenEnd = editEnd.getText().toString();
        // branch statement to determine if the user is saving multiple assessments at a time
        if (loopCount != 0) // keeps track of idCount
            idCount = loopCount;
        try { // Possible NullPointerException
                // branch statement to verify that a name has been given
                if (screenName.isEmpty()) {
                    // toast message to inform user of error
                    Toast.makeText(getApplicationContext(), "A name must be entered to add " +
                            "a term!", Toast.LENGTH_LONG).show();
                } else { // if a name has been given
                    // branch statement to determine if the object already exists
                    if (!checkTerm(i)) { // if object does not exist calls insert method
                        // creates a new term object with parameters to be inserted into database
                        Term newTerm = new Term(++idCount, screenName, screenStart, screenEnd);
                        repository.insert(newTerm); // calls insert method
                        // toast message to confirm save
                        Toast.makeText(getApplicationContext(), "The term was saved!",
                                Toast.LENGTH_LONG).show();
                        loopCount = idCount; // initializes class variable to keep active idCount
                    } else {
                        // creates a new term object with parameters to be updated in database
                        Term oldTerm = new Term(i, screenName, screenStart, screenEnd);
                        repository.update(oldTerm); // calls update method
                        // toast message to confirm save
                        Toast.makeText(getApplicationContext(), "The term was updated!",
                                Toast.LENGTH_LONG).show();
                        loopCount = idCount; // initializes class variable to keep active idCount
                    }
                }
            }
            catch (NullPointerException e){ // catches possible exception
                e.printStackTrace(); // prints exception to stack
            }
        editName.setText(""); // clears editText
        editStart.setText(""); // clears editText
        editEnd.setText(""); // clears editText
    }
    /**
     * This method is used to set the term data in the proper editText fields when sent from
     * previous activity
     */
    public void setTerm(){
        // assigns the id to the class variable
        currentID = getIntent().getIntExtra("id", -1);
        // instantiates a new repository
        repository=new Repository(getApplication());
        // assigns the terms from the repository to the allTerms list
        allTerms=repository.getAllTerms();
        // uses the currentID to locate the currentTerm object
        for(Term term:allTerms){ // gets allTerms
            if(term.getTermID()==currentID) // matches termIDs
                currentTerm=term; // assigns the located object to the variable currentTerm
        }
        // verifies that an object exists to set
        if(currentTerm!=null){
            // assigns editText field to class variable
            editName=findViewById(R.id.editTextTermName);
            // assigns editText field to class variable
            editStart=findViewById(R.id.editTextStart);
            // assigns editText field to class variable
            editEnd= findViewById(R.id.editTextEnd);
            // sets the editText field with the data from the currentTerm object
            editName.setText(currentTerm.getTermName());
            // sets the editText field with the data from the currentTerm object
            editStart.setText(currentTerm.getTermStart());
            // sets the editText field with the data from the currentTerm object
            editEnd.setText(currentTerm.getTermEnd());
        }

    }
    /**
     * This method is used to initialize and refresh the course recycleView
     */
    public void refreshList() {
        // assigns the id to the variable currentID
        currentID = getIntent().getIntExtra("id", -1);
        // assigns the courseRecyclerView to the class variable
        recyclerView=findViewById(R.id.courseRecyclerView);
        // creates a new adaptor for the recycleView
        final android.reserver.stv2.UI.CourseAdaptor courseAdaptor = new android.reserver.stv2.UI.CourseAdaptor(this);
        // sets the adaptor to the recycleView
        recyclerView.setAdapter(courseAdaptor);
        // sets the recycleView layout to linear
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // creates a list to hold the filtered courses
        List<Course> filteredCourses= new ArrayList<>();
        // retrieves the courses connected to the termID
        for(Course course:repository.getAllCourses()){ // gets all courses
            if(course.getTermID()==currentID) // locates courses by id
                filteredCourses.add(course); // adds courses to filtered list
        }
        courseAdaptor.setCourses(filteredCourses); // sets the filteredCourses to the adaptor

    }
    /**
     * This method is used to check if a term already exists
     * @param id the id of the course to be checked
     * @return boolean value
     */
    public boolean checkTerm (int id) {
        for (Term term : repository.getAllTerms()) { // gets all terms
            if (term.getTermID() == id) // matches by id
                return true; // true if does
        }return false; // false if not
    }

    /**
     * This method is used to check if a term has connected courses before deleting
     * @param currentTerm  the term to check
     * @return boolean value
     */
    public boolean checkCourses (Term currentTerm) {
        for (Course course : repository.getAllCourses()) { // gets all courses
            if (course.getTermID() == currentTerm.getTermID()) { // locates if any courses exist
                return true; // if so true
            }
        }return false; // else false
    }
    /**
     * This method is used to delete a term and verifies no connected courses exists and clear
     * the editText fields after
     */
    public void deleteTerm () {
        // creates an integer variable to hold the current id
        int id = getIntent().getIntExtra("id", -1);
        // branch statement to verify that an object exist to be deleted
        if (id == -1) {
            // toast message to inform the user of error
            Toast.makeText(getApplicationContext(), "There's no term to delete!",
                    Toast.LENGTH_LONG).show();
        } else {
            // branch statement to check if term has connected courses
            if (checkCourses(currentTerm)) {
                // toast message to inform the user of error
                Toast.makeText(getApplicationContext(), "Can't delete a Term with Courses!",
                        Toast.LENGTH_LONG).show();
            } else { // if doesn't
                repository.delete(currentTerm); // deletes the term from the repository
                editName.setText(""); // clears the editText fields
                editStart.setText(""); // clears the editText fields
                editEnd.setText(""); // clears the editText fields
                // toast message confirming deletion
                Toast.makeText(getApplicationContext(), "The term was deleted!",
                        Toast.LENGTH_LONG).show();
            }

        }

    }
}