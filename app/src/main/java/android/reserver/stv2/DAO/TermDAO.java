package android.reserver.stv2.DAO;

import android.reserver.stv2.Entities.Term;

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
 * This is an Data Access Object interface for the term entity model. It allows for CRUD operations
 * to be performed on the database.
 */
@Dao
public interface TermDAO {
    /**
     * This method is an interface insert statement to insert a term into the database
     * @param term the term to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term); // Insert Method

    /**
     * This method is an interface update statement to update a term into the database
     * @param term the term to be updated
     */
    @Update
    void update(Term term); // Update method

    /**
     * This method is an interface delete statement to delete a term into the database
     * @param term the term to be deleted
     */
    @Delete
    void delete(Term term); // Delete method
    /**
     *  This method is an interface query statement to execute a query on the database
     * @return the query results
     */
    @Query("Select * FROM term_table ORDER BY termID ASC") // Optional Query method
    List<Term> getAllTerms();
}
