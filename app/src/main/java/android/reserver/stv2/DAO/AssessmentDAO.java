package android.reserver.stv2.DAO;

import android.reserver.stv2.Entities.Assessment;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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

/**
 * This is an Data Access Object interface for the assessment entity model. It allows for CRUD
 * operations to be performed on the database.
 */
@Dao
public interface AssessmentDAO {

    /**
     * This method is an interface insert statement to insert an assessment into the database
     * @param assessment the assessment to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment); // Insert method

    /**
     * This method is an interface update statement to update an assessment into the database
     * @param assessment the assessment to be updated
     */
    @Update
    void update(Assessment assessment); // Update method

    /**
     * This method is an interface delete statement to delete an assessment into the database
     * @param assessment the assessment to be deleted
     */
    @Delete
    void delete(Assessment assessment); // Delete method

    /**
     *  This method is an interface query statement to execute a query on the database
     * @return the query results
     */
    @Query("SELECT * FROM assessment_table ORDER BY assessmentID ASC") // Optional Query method
    List<Assessment> getAllAssessments();

}
