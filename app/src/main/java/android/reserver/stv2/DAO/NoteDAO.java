package android.reserver.stv2.DAO;

import android.reserver.stv2.Entities.Note;

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
 * This is an Data Access Object interface for the note entity model. It allows for CRUD operations
 * to be performed on the database.

 */
@Dao
public interface NoteDAO {

    /**
     * This method is an interface insert statement to insert a note into the database
     * @param note the note to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note); // Insert method

    /**
     * This method is an interface update statement to update a note into the database
     * @param note the note to be updated
     */
    @Update
    void update(Note note); // Update method
    /**
     * This method is an interface delete statement to delete a note into the database
     * @param note the note to be deleted
     */
    @Delete
    void delete(Note note); // Delete method
    /**
     *  This method is an interface query statement to execute a query on the database
     * @return the query results
     */
    @Query("Select * FROM note_table ORDER BY noteID ASC") // Optional Query method
    List<Note> getAllNotes();
}
