package android.reserver.stv2.UI;

import android.content.Intent;
import android.os.Bundle;
import android.reserver.stv2.Database.Repository;
import android.reserver.stv2.Entities.Assessment;
import android.reserver.stv2.Entities.Course;
import android.reserver.stv2.Entities.Note;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is used to control the activity assessment list layout. It extends the
 * AppCompatActivity class and implements the AdapterView.OnItemSelectedListener for utilizing a
 * spinner widget.
 */
public class AssessmentList extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    EditText editName; // creates an EditText variable to store the editName
    EditText editStart; // creates an EditText variable to store the editStart
    EditText editEnd; // creates an EditText variable to store the editEnd
    Spinner editStatus; // creates a Spinner to store the editStatus
    String status; // creates a String variable to store the status
    EditText editInstructorName; // creates an EditText variable to store the editInstructorName
    EditText editInstructorPhone; // creates an EditText variable to store the editInstructorPhone
    EditText editInstructorEmail; // creates an EditText variable to store the editInstructorEmail
    int currentID; // creates an integer variable to hold the assessment currentID
    List<Course> allCourses; // creates a list variable to hold all of the courses
    Course currentCourse; // creates a course object to store the currentCourse
    // creates a repository object to hold the assessment repository
    Repository assessmentRepository;
    // creates a repository object to hold the note repository
    Repository noteRepository;
    // creates a repository object to hold a general repository
    Repository repository;
    // creates a private integer variable to store the termID associated with currentCourse
    private int termID;
    // creates a class variable to hold the loopCount
    int loopCount = 0;

    /**
     * This method is the startup for the activity and associates the activity with a ViewModel as
     * well as binds data to class-scope variables
     * @param savedInstanceState saves the state of the activity for reloading
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState); // call to the parent class constructor
        setContentView(R.layout.activity_assessment_list); // sets the layout view
        // sets up an Action Bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // instantiates Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setCourse(); // calls method to populate activity when an item has been sent
        setSpinner(); // calls method to populate spinner
        refreshAssessmentList(); // calls to initialize recycleView list
        refreshNoteList(); // calls to initialize recycleView list
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
                refreshAssessmentList(); // calls method to refresh recycleView list
                refreshNoteList(); // calls method to refresh recycleView list
                // toast message to confirm refresh
                Toast.makeText(getApplicationContext(), "Refreshed!", Toast.LENGTH_LONG).show();
                return true; // returns true
            case R.id.delete: // if delete option was selected
                deleteCourse(); // calls method to delete the current course
                return true; // returns true
            case R.id.notify: // if notify option was selected
                    alertDetails(); // calls method to send to alertDetails activity
                    return true; // returns true
        }
        return super.onOptionsItemSelected(item); // returns parent class of item selected

    }

    /** This method is used to add the menu assessment list to the layout
     * @param menu is the menu to be added
     * @return true for the boolean condition
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); // creates a class variable menuInflater
        inflater.inflate(R.menu.menu_assessment_list, menu);// assigns inflater to inflate menu
        return true; // returns true
    }

    /**
     * This method is used create an intent for the assessmentDetails activity layout. It validates
     * that an object exist before adding assessments
     * @param view the view to be loaded
     */
    public void assessmentDetails(View view) {
        // creates a integer variable to hold the currentID
        int id = getIntent().getIntExtra("id", -1);
        // branch statement to validate that an object exists first before proceeding
        if (id == -1) { // if default value is present
            // creates a toast message to inform user of error
            Toast.makeText(getApplicationContext(), "You must save the course before adding" +
                    " assessments!", Toast.LENGTH_LONG).show();
        }
        else { // if an object exists
            // creates an intent variable to AssessmentDetails
            Intent intent = new Intent(AssessmentList.this, AssessmentDetails.class);
            // creates an extra for the intent passing the currentCourseID
            intent.putExtra("currentCourseID", currentCourse.getCourseID());
            // starts the activity
            startActivity(intent);
        }
    }

    /**
     * This method is used to create an intent for the noteDetails activity layout. It validates
     * that an object exist before adding notes
     * @param view the view to be loaded
     */
    public void noteDetails(View view) {
        // creates a integer variable to hold the currentID
        int id = getIntent().getIntExtra("id", -1);
        // branch statement to validate that an object exists first before proceeding
        if (id == -1) {
            // creates a toast message to inform user of error
            Toast.makeText(getApplicationContext(), "You must save the course before adding" +
                    " notes!", Toast.LENGTH_LONG).show();
        }
        else { // if an object exists
            // creates an intent variable to NoteDetails
            Intent intent = new Intent(AssessmentList.this, android.reserver.stv2.UI.NoteDetails.class);
            // creates an extra for the intent passing the currentCourseID
            intent.putExtra("currentCourseID", currentCourse.getCourseID());
            // starts the activity
            startActivity(intent);
        }
    }
    /**
     * This method is used to create an intent for the alertDetails activity layout. It validates
     * that an object exist before adding notes.
     */
    public void alertDetails(){
        // creates a integer variable to hold the currentID
        int id = getIntent().getIntExtra("id", -1);
        // assigns the courseNameEditText to the class variable editName
        editName = findViewById(R.id.courseNameEditText);
        // creates a variable to hold the text from the editName variable
        String newName = editName.getText().toString();
        // branch statement to verify that an object exist or that the editText field is not empty
        if (id == -1 || newName.isEmpty()) {
            // creates a toast message to inform user of error
            Toast.makeText(getApplicationContext(), "You must save the course before adding" +
                    " notifications!", Toast.LENGTH_LONG).show();
        }
        else { // if an object exists
            // creates an intent variable to AlertDetails
            Intent intent = new Intent(AssessmentList.this, android.reserver.stv2.UI.AlertDetails.class);
            // creates an extra for the intent passing the currentName
            intent.putExtra("courseName", currentCourse.getCourseName());
            // starts the activity
            startActivity(intent);
        }
    }

    /**
     * This method is used to retrieve the data from the layout and save it to the database when
     * the save button is selected.
     * @param view the view that contains the data
     */
    public void saveCourse(View view) {
        // creates an integer variable to hold the size of the allCourses List
        int idCount = allCourses.get(allCourses.size()-1).getCourseID();
        // creates an integer variable to hold the currentTermID if a term has been sent
        int currentTermID = getIntent().getIntExtra("currentTermID", -1);
        // assigns editText field to class variable
        editName = findViewById(R.id.courseNameEditText);
        // assigns editText field to class variable
        editStart = findViewById(R.id.courseStartEditText);
        // assigns editText field to class variable
        editEnd = findViewById(R.id.courseEndEditText);
        // assigns editText field to class variable
        editStatus = (Spinner) findViewById(R.id.StatusSpinner);
        // assigns editText field to class variable
        editInstructorName = findViewById(R.id.cINameEditText);
        // assigns editText field to class variable
        editInstructorPhone = findViewById(R.id.cIPhoneEditText);
        // assigns editText field to class variable
        editInstructorEmail = findViewById(R.id.cIEmailEditText);
        // creates String variable to hold the data from the editText field
        String newName = editName.getText().toString();
        // creates String variable to hold the data from the editText field
        String newStart = editStart.getText().toString();
        // creates String variable to hold the data from the editText field
        String newEnd = editEnd.getText().toString();
        // creates String variable to hold the data from the editText field
        String newInstructorName = editInstructorName.getText().toString();
        // creates String variable to hold the data from the editText field
        String newInstructorPhone = editInstructorPhone.getText().toString();
        // creates String variable to hold the data from the editText field
        String newInstructorEmail = editInstructorEmail.getText().toString();
        // branch statement to determine if the user is saving multiple courses at a time
        if (loopCount != 0) // keeps track of active idCount
            idCount = loopCount;
        try { // Possible NullPointerException
            // branch statement to verify that a name has been given
            if (newName.isEmpty()){
                // toast message to inform user of error
                Toast.makeText(getApplicationContext(), "A name must be entered to add a " +
                        "course!", Toast.LENGTH_LONG).show();
            }
            else { // if a name has been given
                // branch statement to determine if the object already exists
                if (!checkCourse(currentID)) { // if object does not exist calls insert method
                    // branch statement to determine if a termID is attached to the object
                    // this is needed for when a user deletes an object and creates another before
                    // refreshing
                    if (termID <= 0) {  // if there is no termID it uses the currentTermID
                        // creates a new course object with parameters to be inserted into database
                        Course newCourse = new Course(++idCount, newName, newStart, newEnd,
                                status, newInstructorName, newInstructorPhone, newInstructorEmail,
                                currentTermID);
                        repository.insert(newCourse); // calls insert method
                        // toast message to confirm save
                        Toast.makeText(getApplicationContext(), "The course was saved!",
                                Toast.LENGTH_LONG).show();
                        loopCount = idCount; // initializes class variable to keep active idCount
                    }
                    else {
                        // creates a new course object with parameters to be inserted into database
                        Course newCourse = new Course(++idCount, newName, newStart, newEnd,
                                status, newInstructorName, newInstructorPhone, newInstructorEmail,
                                termID);
                        repository.insert(newCourse); // calls insert method
                        // toast message to confirm save
                        Toast.makeText(getApplicationContext(), "The course was saved!",
                                Toast.LENGTH_LONG).show();
                        loopCount = idCount; // initializes class variable to keep active idCount
                    }
                } else { // if the object already exists calls update method
                    // creates a new course object with parameters to be updated in database
                    Course oldCourse = new Course(currentID, newName, newStart, newEnd, status,
                            newInstructorName, newInstructorPhone, newInstructorEmail,
                            currentCourse.getTermID());
                    // calls update method
                    repository.update(oldCourse);
                    // toast message to confirm update
                    Toast.makeText(getApplicationContext(), "The course was updated!",
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
        editInstructorName.setText(""); // clears editText
        editInstructorPhone.setText(""); // clears editText
        editInstructorEmail.setText(""); // clears editText
        editStatus.setSelection(0); // sets spinner back to 0

    }

    /**
     * This method is used to set the course data in the proper editText fields when sent from
     * previous activity
     */
    public void setCourse(){
        // assigns the id to the class variable
        currentID = getIntent().getIntExtra("id", -1);
        // instantiates a new repository
        repository = new Repository(getApplication());
        // assigns the courses from the repository to the allCourses list
        allCourses = repository.getAllCourses();
        // uses the currentID to locate the currentCourse object
        for (Course course:allCourses){ // gets allCourses
            if(course.getCourseID() == currentID) // matches courseIDs
                currentCourse = course; // assigns the located object to the variable currentCourse
        }
        // verifies that an object exists to set
        if(currentCourse!=null){
            // assigns editText field to class variable
            editName = findViewById(R.id.courseNameEditText);
            // assigns editText field to class variable
            editStart = findViewById(R.id.courseStartEditText);
            // assigns editText field to class variable
            editEnd = findViewById(R.id.courseEndEditText);
            // assigns editText field to class variable
            editInstructorName = findViewById(R.id.cINameEditText);
            // assigns editText field to class variable
            editInstructorPhone = findViewById(R.id.cIPhoneEditText);
            // assigns editText field to class variable
            editInstructorEmail = findViewById(R.id.cIEmailEditText);
            // sets the editText field with the data from the currentCourse object
            editName.setText(currentCourse.getCourseName());
            // sets the editText field with the data from the currentCourse object
            editStart.setText(currentCourse.getCourseStart());
            // sets the editText field with the data from the currentCourse object
            editEnd.setText(currentCourse.getCourseEnd());
            // sets the editText field with the data from the currentCourse object
            editInstructorName.setText(currentCourse.getInstructorName());
            // sets the editText field with the data from the currentCourse object
            editInstructorPhone.setText(currentCourse.getInstructorPhone());
            // sets the editText field with the data from the currentCourse object
            editInstructorEmail.setText(currentCourse.getInstructorEmail());
        }

    }

    /**
     * This method is used to initialize and refresh the AssessmentList recycleView
     */
    public void refreshAssessmentList(){
        // assigns the id to the variable currentID
        currentID = getIntent().getIntExtra("id", -1);
        // instantiates a new repository and assigns it to the assessmentRepository variable
        assessmentRepository = new Repository(getApplication());
        // creates a new list object to hold the filteredAssessments
        List<Assessment> filteredAssessments = new ArrayList<>();
        // assigns the assessmentRecyclerView to the created variable
        RecyclerView assessmentRecyclerView = findViewById(R.id.assessmentRecyclerView);
        // creates a new adaptor for the recycleView
        final AssessmentAdaptor assessmentAdaptor = new AssessmentAdaptor(this);
        // retrieves the assessments connected to the courseID
        for (Assessment assessment:repository.getAllAssessments()){ // retrieves all assessments
            if(assessment.getCourseID() == currentID) // locates assessments by id
                filteredAssessments.add(assessment); // adds assessments to filtered list
        }
        // sets the adaptor to the recycleView
        assessmentRecyclerView.setAdapter(assessmentAdaptor);
        // sets the filteredAssessments to the adaptor
        assessmentAdaptor.setAssessments(filteredAssessments);
        // sets the recycleView layout to linear
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
    /**
     * This method is used to initialize and refresh the NoteList recycleView
     */
    public void refreshNoteList(){
        // assigns the id to the variable currentID
        currentID = getIntent().getIntExtra("id", -1);
        // instantiates a new repository and assigns it to the noteRepository variable
        noteRepository = new Repository(getApplication());
        // creates a new list object to hold the filteredNotes
        List<Note> filteredNotes = new ArrayList<>();
        // assigns the noteRecyclerView to the created variable
        RecyclerView noteRecyclerView = findViewById(R.id.noteRecyclerView);
        // creates a new adaptor for the recycleView
        final NoteAdaptor noteAdaptor = new NoteAdaptor(this);
        // retrieves the notes connected to the courseID
        for (Note note: repository.getAllNotes()){ // retrieves all notes
            if(note.getCourseID() == currentID) // locates notes by id
                filteredNotes.add(note); // adds notes to the filtered list
        }
        // sets the adaptor to the recycleView
        noteRecyclerView.setAdapter(noteAdaptor);
        // sets the filteredAssessments to the adaptor
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // sets the recycleView layout to linear
        noteAdaptor.setNotes(filteredNotes);

    }

    /**
     * This method is used to check if a course already exists
     * @param id the id of the course to be checked
     * @return boolean value
     */
    public boolean checkCourse(int id){
        // retrieves all courses
        for(Course course:repository.getAllCourses()){
            if(course.getCourseID() == id) // see if id parameter matches
                return true; // true if does
        }return false; // false if not
    }

    /**
     * This method is used to create and set a spinner in the layout
     */
    public void setSpinner(){
        // assigns the StatusSpinner to the variable editStatus
        editStatus = findViewById(R.id.StatusSpinner);
        // creates an adaptor for the arrayList and spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.arrayStatus, android.R.layout.simple_spinner_item);
        // assigns the desired layout to the adaptor
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editStatus.setAdapter(adapter); // sets the adaptor tot the spinner variable
        // sets the listener to the spinner variable
        editStatus.setOnItemSelectedListener(this);
        // branch statement to set the spinner to the desired position
        if(checkCourse(currentID)) {
            // assigns the intent extra status to a variable
            CharSequence s = getIntent().getStringExtra("status");
            // creates an integer variable to hold the position of the spinner
            int spin = adapter.getPosition(s);
            // sets the spinner to the desired selection
            editStatus.setSelection(spin);
        }
    }

    /**
     * This method is used to delete a course and clear the editText fields after
     */
    public void deleteCourse(){
        // creates an integer variable to hold the current id
        int id = getIntent().getIntExtra("id", -1);
        // branch statement to verify that an object exist to be deleted
        if (id == -1){
            // toast message to inform the user of error
            Toast.makeText(getApplicationContext(), "There is no course to delete!",
                    Toast.LENGTH_LONG).show();
        }
        else { // if an object exist to delete
            // captures the termID of the course before deleting for when the user creates a new
            // object before refreshing
            termID = currentCourse.getTermID();
            deleteNotes(currentCourse.getCourseID()); // deletes connected notes
            deleteAssessments(currentCourse.getCourseID()); // deletes connected assessments
            repository.delete(currentCourse); // deletes the course from the repository
            editName.setText(""); // clears the editText fields
            editStart.setText(""); // clears the editText fields
            editEnd.setText(""); // clears the editText fields
            editInstructorName.setText(""); // clears the editText fields
            editInstructorPhone.setText(""); // clears the editText fields
            editInstructorEmail.setText(""); // clears the editText fields
            editStatus.setSelection(0); // sets the spinner to 0
            clearRecycleViews(); // clears recycleViews
            // toast message confirming deletion
            Toast.makeText(getApplicationContext(), "The course was deleted!",
                    Toast.LENGTH_LONG).show();

        }
    }

    /**
     * This method is used to delete any notes connected to a course when the course is deleted
     * @param id the id of the course being deleted
     */
    public void deleteNotes(int id){
        // retrieves all notes
        for (Note note: repository.getAllNotes()){
            if (note.getCourseID() == id){ // locates connected notes
                repository.delete(note); // deletes notes
            }
        }

    }
    /**
     * This method is used to delete any assessments connected to a course when the course is
     * deleted
     * @param id the id of the course being deleted
     */
    public void deleteAssessments(int id){
        // retrieves all assessments
        for (Assessment assessment: repository.getAllAssessments()){
            if (assessment.getCourseID() == id){ // locates connected assessments
                repository.delete(assessment); // deletes assessments
            }
        }

    }

    /**
     * This method is used to clear the recycleViews when a course is deleted
     */
    public void clearRecycleViews (){
        // creates a new repository and assigns it to variable
        assessmentRepository = new Repository(getApplication());
        // creates a new list variable for filteredAssessments
        List<Assessment> filteredAssessments = new ArrayList<>();
        // assigns recycleView to RecyclerView variable
        RecyclerView assessmentRecyclerView = findViewById(R.id.assessmentRecyclerView);
        // creates a new recycleView adaptor
        final AssessmentAdaptor assessmentAdaptor = new AssessmentAdaptor(this);
        // sets the adaptor to the recycleView
        assessmentRecyclerView.setAdapter(assessmentAdaptor);
        // sets the empty list to the adaptor
        assessmentAdaptor.setAssessments(filteredAssessments);
        // sets the recyclerView layout
        assessmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // creates a new repository and assigns it to variable
        noteRepository = new Repository(getApplication());
        // creates a new list variable for filteredAssessments
        List<Note> filteredNotes = new ArrayList<>();
        // assigns recycleView to RecyclerView variable
        RecyclerView noteRecyclerView = findViewById(R.id.noteRecyclerView);
        // creates a new recycleView adaptor
        final NoteAdaptor noteAdaptor = new NoteAdaptor(this);
        // sets the adaptor to the recycleView
        noteRecyclerView.setAdapter(noteAdaptor);
        // sets the empty list to the adaptor
        noteAdaptor.setNotes(filteredNotes);
        // sets the recyclerView layout
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}