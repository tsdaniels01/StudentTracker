package android.reserver.stv2.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
 * This class is used create a Note object and assigns the entity to the note_table
 * in the database
 */
@Entity (tableName = "note_table") // assigns note_table to entity
public class Note {

    // assigns the noteID as the primary key and enables auto-generating the id
    @PrimaryKey (autoGenerate = true)
    private int noteID; // creates a variable to store the noteID
    private String note; // creates a variable to store the note
    private int courseID; // creates a variable to store the courseID

    /**
     * This method is a constructor for the note object
     * @param noteID integer parameter
     * @param note String parameter
     * @param courseID integer parameter
     */
    public Note(int noteID, String note, int courseID) {
        this.noteID = noteID; // assigns parameter to respective private variable
        this.note = note; // assigns parameter to respective private variable
        this.courseID = courseID; // assigns parameter to respective private variable
    }

    /**
     * This method is a getter for the noteID
     * @return the noteID integer
     */
    public int getNoteID() {
        return noteID;
    }

    /**
     * This method is a setter for the noteID
     * @param noteID the noteID to be set
     */
    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    /**
     * This method is a getter for the note
     * @return the note String
     */
    public String getNote() {
        return note;
    }

    /**
     * This method is a setter for the note
     * @param note the note to be set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * This method is a getter for the courseID
     * @return the courseID integer
     */
    public int getCourseID() {
        return courseID;
    }

    /** This method is a setter for the courseID
     * @param courseID the courseID to be set
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
