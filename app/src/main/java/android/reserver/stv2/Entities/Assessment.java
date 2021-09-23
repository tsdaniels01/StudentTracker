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
 * This class is used create an Assessment object and assigns the entity to the assessment_table
 * in the database
 */
@Entity (tableName = "assessment_table") // assigns to assessment_table
public class Assessment {

    // assigns the assessmentID as the primary key and enables auto-generating the id
    @PrimaryKey(autoGenerate = true)
    private int assessmentID; // creates a variable to store the assessmentID
    private String assessmentName; // creates a variable to store the assessmentName
    private String type; // creates a variable to store the type
    private String assessmentStart; // creates a variable to store the start date
    private String assessmentEnd; // creates a variable to store the end date
    private int courseID; // creates a variable to store the courseID

    /**
     * This method is a constructor for the assessment object
     * @param assessmentID integer parameter
     * @param assessmentName String parameter
     * @param type String parameter
     * @param assessmentStart  String parameter
     * @param assessmentEnd String parameter
     * @param courseID integer parameter
     */
    public Assessment(int assessmentID, String assessmentName, String type, String assessmentStart,
                      String assessmentEnd, int courseID) {
        this.assessmentID = assessmentID; // assigns parameter with respective named variable
        this.assessmentName = assessmentName; // assigns parameter with respective named variable
        this.type = type; // assigns parameter with respective named variable
        this.assessmentStart = assessmentStart; // assigns parameter with respective named variable
        this.assessmentEnd = assessmentEnd; // assigns parameter with respective named variable
        this.courseID = courseID; // assigns parameter with respective named variable
    }

    /**
     * This method is used to override the toString method for output purposes
     * @return the assessmentName and type variables
     */
    @Override
    public String toString() {
        return "Assessment{" +
                "assessmentName='" + assessmentName + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    /**
     * This method is used to get the assessmentID
     * @return the assessmentID integer
     */
    public int getAssessmentID() {
        return assessmentID;
    }

    /**
     * This method is used to set the assessmentID
     * @param assessmentID the assessmentID to be set
     */
    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID; // assigns the parameter to local variable
    }

    /**
     * This method is used to get the assessmentName
     * @return the assessmentName String
     */
    public String getAssessmentName() {
        return assessmentName;
    }

    /**
     * This method is used to set the assessmentName
     * @param assessmentName the assessment name to be set
     */
    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName; // assigns the parameter to local variable
    }

    /**
     * This method is used to get the assessment type
     * @return the type String
     */
    public String getType() {
        return type;
    }

    /**
     * This method is used to set the assessment type
     * @param type the type to be set
     */
    public void setType(String type) {
        this.type = type; // assigns the parameter to local variable
    }

    /**
     * This method is used to get the assessmentStart
     * @return the assessmentStart String
     */
    public String getAssessmentStart() {
        return assessmentStart;
    }

    /**
     * This method is used to set the assessmentStart
     * @param assessmentStart the assessmentStart to be set
     */
    public void setAssessmentStart(String assessmentStart) {
        this.assessmentStart = assessmentStart; // assigns the parameter to local variable
    }

    /**
     * This method is used to get the assessmentEnd
     * @return the assessmentEnd String
     */
    public String getAssessmentEnd() {
        return assessmentEnd;
    }

    /**
     * This method is used to set the assessmentEnd
     * @param assessmentEnd the assessmentEnd to be set
     */
    public void setAssessmentEnd(String assessmentEnd) {
        this.assessmentEnd = assessmentEnd; // assigns the parameter to local variable
    }

    /**
     * This method is used to get the courseID
     * @return the courseID
     */
    public int getCourseID() {
        return courseID;
    }

    /**
     * This method is used to set the courseID
     * @param courseID the courseID to be set
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID; // assigns the parameter to local variable
    }

}
