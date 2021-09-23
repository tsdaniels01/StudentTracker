package android.reserver.stv2.UI;


/**
 * Project: C196 Mobile Application Development Performance Assessment
 * <p>
 * User: Timothy Daniels
 * ID: 001164972
 * Date: 7/5/2021
 * Time: 1:35 PM
 * <p>
 * Created with Android Studio
 */

import android.content.Intent;
import android.os.Bundle;
import android.reserver.stv2.Database.Repository;
import android.reserver.stv2.Entities.Assessment;
import android.reserver.stv2.Entities.Course;
import android.reserver.stv2.Entities.Note;
import android.reserver.stv2.Entities.Term;
import android.reserver.stv2.R;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This class is the MainActivity and controls the main activity layout which starts the
 * application. In addition, test data is given for the database.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method is the startup for the activity and associates the activity with a ViewModel as
     * well as binds data to class-scope variables
     * @param savedInstanceState saves the state of the activity for reloading
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);// call to the parent class constructor
        setContentView(R.layout.activity_main); // sets the layout view
        Repository repository = new Repository(getApplication()); // creates a new repository
        // creates a new term object for testing
        Term term = new Term(1, "First Semester", "9/01/21",
                "12/15/21");
        repository.insert(term); // repository insert method
        // creates a new term object for testing
        term = new Term(2, "Second Semester", "1/16/22",
                "4/15/22");
        repository.insert(term);// repository insert method
        // creates a new term object for testing
        term = new Term(3, "Third Semester", "9/03/22",
                "12/17/22");
        repository.insert(term);// repository insert method
        // creates a new course object for testing
        Course course = new Course(1, "Algebra 1", "9/01/21",
                "12/15/21", "plan to take", " James Pottery",
                "986-456-9632", "jpottery@gmail.com", 1);
        repository.insert(course);// repository insert method
        // creates a new course object for testing
        course = new Course(2, "Sociology", "9/01/21",
                "12/15/21", "plan to take", " Melanie Davis",
                "456-962-7854", "m_davis@gmail.com", 1);
        repository.insert(course);// repository insert method
        // creates a new course object for testing
        course = new Course(3, "Civil Rights", "1/16/22",
                "4/15/22", "plan to take", " Jonathan Gillispie",
                "215-564-5231", "gillispie4@wgu.edu", 2);
        repository.insert(course);// repository insert method
        // creates a new course object for testing
        course = new Course(4, "Physics", "1/16/22",
                "4/15/22", "plan to take", " Frank Vanderbilt ",
                "253-985-8745", "fvander09@wgu.edu", 2);
        repository.insert(course);// repository insert method
        // creates a new course object for testing
        course = new Course(5, "Statistics", "9/03/22",
                "12/17/22", "completed", " Katie Delanie ",
                "456-785-8523", "delanie_k@wgu.edu", 3);
        repository.insert(course);// repository insert method
        // creates a new assessment object for testing
        Assessment assessment = new Assessment(1, "P789",
                "Performance", "12/01/21", "12/14/21", 1);
        repository.insert(assessment);// repository insert method
        // creates a new assessment object for testing
        assessment = new Assessment(2, "O632", "objective",
                "12/01/21", "12/14/21", 1);
        repository.insert(assessment);// repository insert method
        // creates a new assessment object for testing
        assessment = new Assessment(3, "P124", "performance",
                "12/01/21", "12/14/21", 2);
        repository.insert(assessment);// repository insert method
        // creates a new assessment object for testing
        assessment = new Assessment(4, "P003", "performance",
                "4/01/22", "4/14/22", 3);
        repository.insert(assessment);// repository insert method
        // creates a new assessment object for testing
        assessment = new Assessment(5, "P098", "performance",
                "4/01/22", "4/14/22", 4);
        repository.insert(assessment);// repository insert method
        // creates a new assessment object for testing
        assessment = new Assessment(6, "O134", "objective",
                "12/03/22", "12/12/22", 5);
        repository.insert(assessment);// repository insert method
        // creates a new note object for testing
        Note note = new Note(1, "I hope this class is rewarding", 1);
        repository.insert(note);// repository insert method
        // creates a new note object for testing
        note = new Note(2, "I wish all my classes were like this one", 1);
        repository.insert(note);// repository insert method
        // creates a new note object for testing
        note = new Note(3, "This class is going to be the best", 3);
        repository.insert(note);// repository insert method
        // creates a new note object for testing
        note = new Note(4, "The teacher is supposed to be excellent", 4);
        repository.insert(note);// repository insert method
        // creates a new note object for testing
        note = new Note(5, "I should take this next semester if possible", 4);
        repository.insert(note);// repository insert method
        // creates a new note object for testing
        note = new Note(6, "Do I need this course to graduate?", 5);
        repository.insert(note);// repository insert method

    }

    /**
     * This method is used to create an intent for the TermList activity layout
     * @param view
     */
    public void enterTermList(View view) {
        // creates an intent variable to TermList
        Intent intent = new Intent(MainActivity.this, android.reserver.stv2.UI.TermList.class);
        startActivity(intent); // starts the activity


    }
}