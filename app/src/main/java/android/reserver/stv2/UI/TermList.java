package android.reserver.stv2.UI;

import android.content.Intent;
import android.os.Bundle;
import android.reserver.stv2.Database.Repository;
import android.reserver.stv2.Entities.Term;
import android.reserver.stv2.R;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

/**
 * This class is used to control the activity term list layout. It extends the
 * AppCompatActivity class.
 */
public class TermList extends AppCompatActivity {

    Repository repository; // creates a variable to hold the repository

    /**
     * This method is the startup for the activity and associates the activity with a ViewModel as
     * well as binds data to class-scope variables
     * @param savedInstanceState saves the state of the activity for reloading
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call to the parent class constructor
        setContentView(R.layout.activity_term_list); // sets the layout view
        // sets up an Action Bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        // instantiates Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        inflater.inflate(R.menu.menu_term_list, menu); // assigns inflater to inflate menu
        return true; // returns true
    }

    /**
     * This method is used to create an intent for the CourseList activity layout
     * @param view
     */
    public void courseList(View view) {
        // creates a variable to hold the new intent
        Intent intent = new Intent(TermList.this, CourseList.class);
        startActivity(intent); // starts the activity
    }

    /**
     * This method is used to initialize and refresh the termList RecyclerView
     */
    public void refreshList(){
        // creates a new repository and assigns it to class variable
        repository = new Repository(getApplication());
        // creates a list to hold allTerms
        List<Term> allTerms = repository.getAllTerms();
        // assign the termsRecyclerView to a new variable
        RecyclerView recyclerView = findViewById(R.id.termsRecyclerView);
        // creates an adaptor for the recyclerView
        final TermAdaptor termAdaptor = new TermAdaptor(this);
        // sets the adaptor to the recyclerView
        recyclerView.setAdapter(termAdaptor);
        // sets the recyclerView layout to linear
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // sets the allTerms list to the adaptor
        termAdaptor.setTerms(allTerms);

    }

}