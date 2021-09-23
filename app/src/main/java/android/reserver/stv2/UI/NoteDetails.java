package android.reserver.stv2.UI;

import android.content.Intent;
import android.os.Bundle;
import android.reserver.stv2.Database.Repository;
import android.reserver.stv2.Entities.Course;
import android.reserver.stv2.Entities.Note;
import android.reserver.stv2.R;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;

/**
 * This class is used to control the activity note details layout. It extends the
 * AppCompatActivity class.
 */
public class NoteDetails extends AppCompatActivity {

    EditText editNotes; // creates an editText variable to hold the notes
    int currentID; // creates an integer variable to hold the currentID
    Note currentNote; // creates a Note object to hold the currentNote
    List<Note> allNotes; // creates a list object to hold allNotes
    Repository repository; // creates a Repository object to hold the repository
    int loopCount = 0; // creates an integer variable to hold the loopCount
    private int courseID; // creates a private integer variable to hold the courseID

    /**
     * This method is the startup for the activity and associates the activity with a ViewModel as
     * well as binds data to class-scope variables
     * @param savedInstanceState saves the state of the activity for reloading
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call to the parent class constructor
        setContentView(R.layout.activity_note_details); // sets the layout view
        // sets up an Action Bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // instantiates Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setNotes(); // calls method to populate activity when an item has been sent


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
                deleteNote(); // calls method to delete the current note
                return true; // returns true
            case R.id.share: // if share option was selected
                setSharing(); // calls method to share note
                return true; // returns true

        }
        return super.onOptionsItemSelected(item); // returns parent class of item selected

    }
    /** This method is used to add the menu note details to the layout
     * @param menu is the menu to be added
     * @return true for the boolean condition
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater(); // creates a class variable menuInflater
        inflater.inflate(R.menu.menu_note_details, menu); // assigns inflater to inflate menu
        return true; // returns true
    }
    /**
     * This method is used to retrieve the data from the layout and save it to the database when
     * the save button is selected.
     * @param view the view that contains the data
     */
    public void saveNotes(View view) {
        // creates an integer variable to hold the size of the allCourses List
        int idCount = allNotes.get(allNotes.size()-1).getNoteID();
        // creates an integer variable to hold the currentCourseID if a note has been sent
        int currentCourseID = getIntent().getIntExtra("currentCourseID", -1);
        // assigns editText field to class variable
        editNotes = findViewById(R.id.notesEditText);
        // creates String variable to hold the data from the editText field
        String newItem = editNotes.getText().toString();
        // branch statement to determine if the user is saving multiple assessments at a time
        if (loopCount != 0) // keeps track of idCount
            idCount = loopCount;
        try { // Possible NullPointerException
            if (newItem.isEmpty()){ // branch statement to verify that a name has been given
                Toast.makeText(getApplicationContext(), "A note must be created before" +
                        " saving!", Toast.LENGTH_LONG).show();
            }
            else { // if a name has been given
                // branch statement to determine if the object already exists
                if (!checkNote(currentID)) { // if object does not exist calls insert method
                    // branch statement to determine if a courseID is attached to the object
                    // this is needed for when a user deletes an object and creates another before
                    // refreshing
                    if (courseID <= 0) { // if there is no courseID it uses the currentCourseID
                        Note newNote = new Note(++idCount, newItem, currentCourseID);
                        repository.insert(newNote);// calls insert method
                        // toast message to confirm save
                        Toast.makeText(getApplicationContext(), "The note was saved!",
                                Toast.LENGTH_LONG).show();
                        loopCount = idCount; // initializes class variable to keep active idCount
                    }
                    else {
                        // creates a new course object with parameters to be inserted into database
                        Note newNote = new Note(++idCount, newItem, courseID);
                        repository.insert(newNote); // calls insert method
                        // toast message to confirm save
                        Toast.makeText(getApplicationContext(), "The note was saved!",
                                Toast.LENGTH_LONG).show();
                        loopCount = idCount; //initializes class variable to keep active idCount
                    }
                } else { // if the object already exists calls update method
                    Note oldNote = new Note(currentID, newItem, currentNote.getCourseID());
                    repository.update(oldNote); // calls update method
                    Toast.makeText(getApplicationContext(), "The note was updated!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        catch (NullPointerException e){ // catches possible exception
            e.printStackTrace(); // prints exception to stack
        }
        editNotes.setText(""); // clears editText

    }
    /**
     * This method is used to set the note data in the proper editText fields when sent from
     * previous activity
     */
    public void setNotes (){
        // assigns the id to the class variable
        currentID = getIntent().getIntExtra("id", -1);
        // instantiates a new repository
        repository = new Repository(getApplication());
        // assigns the notes from the repository to the allNotes list
        allNotes = repository.getAllNotes();
        // uses the currentID to locate the currentNote object
        for (Note note:allNotes){ // gets all notes
            if(note.getNoteID() == currentID) // matches noteIDs
                currentNote = note; // assigns the located object to the variable
        }
        // verifies that an object exists to set
        if(currentNote!=null)   {
            // assigns editText field to class variable
            editNotes = findViewById(R.id.notesEditText);
            // sets the editText field with the data from the currentNote object
            editNotes.setText(currentNote.getNote());
        }

    }
    /**
     * This method is used to check if a note already exists
     * @param id the id of the assessment to be checked
     * @return boolean value
     */
    public boolean checkNote (int id){
        // retrieves all notes
        for (Note note: repository.getAllNotes()){
            if (note.getNoteID() == id) // see if id parameter matches
                return true; // true if does
        }return false; // false if not
    }

    /**
     * This method is used to set up sharing for notes
     */
    public void setSharing(){
        // checks to see if a note exist before sharing
        if (!checkNote(currentID)){
            // toast message informing the user of error
            Toast.makeText(getApplicationContext(), "Notes must be saved before sharing!",
                    Toast.LENGTH_LONG).show();
        }
        else { // if the note exist
            // assigns notesEditText to class variable
            editNotes = findViewById(R.id.notesEditText);
            // concatenates a string and stores it in created variable
            String note = "These are my notes for " + getCourseName(currentNote.getCourseID()) +
                    ": " + currentNote.getNote();
            Intent sendIntent = new Intent(); // creates a new intent object
            sendIntent.setAction(Intent.ACTION_SEND); // sets intent to send
            sendIntent.putExtra(Intent.EXTRA_TEXT, note); // intent extra note to be sent
            sendIntent.setType("text/plain"); // intent type
            // instantiates intent to send
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent); // starts activity

        }
    }

    /**
     * This method is used to locate a courseName by courseID to pass into the note to send
     * @param courseID the courseID to locate
     * @return the courseName
     */
    public String getCourseName (int courseID) {
        // gets all courses
        for (Course course: repository.getAllCourses()){
            if (course.getCourseID() == courseID) { // matches course by ID
                return course.getCourseName(); // returns that course name
            }
        }return null; // return null otherwise
    }
    /**
     * This method is used to delete a note and clear the editText fields after
     */
    public void deleteNote(){
        // creates an integer variable to hold the current id
        int id = getIntent().getIntExtra("id", - 1);
        // branch statement to verify that an object exist to be deleted
        if(id == -1){
            // toast message to inform the user of error
            Toast.makeText(getApplicationContext(), "There is no note to delete!",
                    Toast.LENGTH_LONG).show();
        } else{// if an object exist to delete
            // captures the courseID of the course before deleting for when the user creates a new
            // object before refreshing
            courseID = currentNote.getCourseID();
            repository.delete(currentNote); // deletes the note from the repository
            editNotes.setText(""); // clears the editText fields
            // toast message confirming deletion
            Toast.makeText(getApplicationContext(), "The note was deleted!",
                    Toast.LENGTH_LONG).show();
        }

    }
}