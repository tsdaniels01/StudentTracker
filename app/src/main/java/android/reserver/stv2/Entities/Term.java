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
 * This class is used create a Term object and assigns the entity to the term_table
 * in the database
 */
@Entity (tableName = "term_table") // assigns the term_table to the entity
public class Term {

    // assigns the courseID as the primary key and enables auto-generating the id
    @PrimaryKey(autoGenerate = true)
    private int termID; // creates a variable to store the termID
    private String termName; // creates a variable to store the termName
    private String termStart; // creates a variable to store the termStart
    private String termEnd; // creates a variable to store the termEnd

    /**
     * This method is a constructor for the term object
     * @param termID integer parameter
     * @param termName String parameter
     * @param termStart String parameter
     * @param termEnd String parameter
     */
    public Term(int termID, String termName, String termStart, String termEnd) {
        this.termID = termID; // assigns the parameter to the respective private variable
        this.termName = termName; // assigns the parameter to the respective private variable
        this.termStart = termStart; // assigns the parameter to the respective private variable
        this.termEnd = termEnd; // assigns the parameter to the respective private variable

    }

    /**
     * This method is used to override the toString method for output purposes
     * @return the termName
     */
    @Override
    public String toString() {
        // return termName
        return "Term{" +
                "termName='" + termName + '\'' +
                '}';
    }

    /**
     * This method is a getter for the termID
     * @return the termID integer
     */
    public int getTermID() {
        return termID;
    }

    /**
     * This method is a setter for the termID
     * @param termID the termID to be set
     */
    public void setTermID(int termID) {
        this.termID = termID;
    }

    /**
     * This method is a getter for the termName
     * @return the termName String
     */
    public String getTermName() {
        return termName;
    }

    /**
     * This method is a setter for the termName
     * @param termName the termName to be set
     */
    public void setTermName(String termName) {
        this.termName = termName;
    }

    /**
     * This method is a getter for the termStart
     * @return the termStart String
     */
    public String getTermStart() {
        return termStart;
    }

    /**
     * This method is a setter for the termStart
     * @param termStart the termStart to be set
     */
    public void setTermStart(String termStart) {
        this.termStart = termStart;
    }

    /**
     * This method is a getter for the termEnd
     * @return the termEnd String
     */
    public String getTermEnd() {
        return termEnd;
    }

    /**
     * This method is a setter for the termEnd
     * @param termEnd the termEnd to be set
     */
    public void setTermEnd(String termEnd) {
        this.termEnd = termEnd;
    }

}

