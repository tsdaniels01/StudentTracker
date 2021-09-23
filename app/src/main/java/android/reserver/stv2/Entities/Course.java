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
 * This class is used create a Course object and assigns the entity to the course_table
 * in the database
 */
@Entity (tableName = "course_table") // assigns course_table to entity
public class Course {

    // assigns the courseID as the primary key and enables auto-generating the id
    @PrimaryKey(autoGenerate = true)
    private int courseID; // creates a variable to store the courseID
    private String courseName; // creates a variable to store the courseName
    private String courseStart; // creates a variable to store the courseStart
    private String courseEnd; // creates a variable to store the courseEnd
    private String status; // creates a variable to store the course Status
    private String instructorName; // creates a variable to store the instructorName
    private String instructorPhone; // creates a variable to store the instructorPhone
    private String instructorEmail; // creates a variable to store the instructorEmail
    private int termID; // creates a variable to store the termID

    /**
     * This method is a constructor for the Course object
     * @param courseID integer parameter
     * @param courseName String parameter
     * @param courseStart String parameter
     * @param courseEnd String parameter
     * @param status String parameter
     * @param instructorName String parameter
     * @param instructorPhone String parameter
     * @param instructorEmail String parameter
     * @param termID integer parameter
     */
    public Course(int courseID, String courseName, String courseStart, String courseEnd,
                  String status, String instructorName, String instructorPhone,
                  String instructorEmail, int termID) {
        this.courseID = courseID;  // assigns the parameter to the respective private variable
        this.courseName = courseName; // assigns the parameter to the respective private variable
        this.courseStart = courseStart; // assigns the parameter to the respective private variable
        this.courseEnd = courseEnd; // assigns the parameter to the respective private variable
        this.status = status; // assigns the parameter to the respective private variable
        // assigns the parameter to the respective private variable
        this.instructorName = instructorName;
        // assigns the parameter to the respective private variable
        this.instructorPhone = instructorPhone;
        // assigns the parameter to the respective private variable
        this.instructorEmail = instructorEmail;
        this.termID = termID; // assigns the parameter to the respective private variable
    }

    /**
     * This method overrides the toString method for output purposes
     * @return
     */
    @Override
    public String toString() {
        // returns courseName and status
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    /**
     *  This method is a getter for the courseID variable
     * @return the courseID integer
     */
    public int getCourseID() {
        return courseID;
    }

    /**
     * This method is a setter for the courseID variable
     * @param courseID the courseID to be set
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    /**
     *  This method is a getter for the courseName variable
     * @return the courseName String
     */
    public String getCourseName() {
        return courseName;
    }
    /**
     * This method is a setter for the courseName variable
     * @param courseName the courseName to be set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    /**
     *  This method is a getter for the courseStart variable
     * @return the courseStart String
     */
    public String getCourseStart() {
        return courseStart;
    }
    /**
     * This method is a setter for the courseStart variable
     * @param courseStart the courseStart to be set
     */
    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }
    /**
     *  This method is a getter for the courseEnd variable
     * @return the courseEnd String
     */
    public String getCourseEnd() {
        return courseEnd;
    }
    /**
     * This method is a setter for the courseEnd variable
     * @param courseEnd the courseEnd to be set
     */
    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }
    /**
     *  This method is a getter for the course Status variable
     * @return the course Status String
     */
    public String getStatus() {
        return status;
    }
    /**
     * This method is a setter for the course Status variable
     * @param status the course Status to be set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     *  This method is a getter for the instructorName variable
     * @return the instructorName String
     */
    public String getInstructorName() {
        return instructorName;
    }
    /**
     * This method is a setter for the instructorName variable
     * @param instructorName the instructorName to be set
     */
    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }
    /**
     *  This method is a getter for the instructorPhone variable
     * @return the instructorPhone String
     */
    public String getInstructorPhone() {
        return instructorPhone;
    }
    /**
     * This method is a setter for the instructorPhone variable
     * @param instructorPhone the instructorPhone to be set
     */
    public void setInstructorPhone(String instructorPhone) {
        this.instructorPhone = instructorPhone;
    }
    /**
     *  This method is a getter for the instructorEmail variable
     * @return the instructorEmail String
     */
    public String getInstructorEmail() {
        return instructorEmail;
    }
    /**
     * This method is a setter for the instructorEmail variable
     * @param instructorEmail the instructorEmail to be set
     */
    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }
    /**
     *  This method is a getter for the termID variable
     * @return the termID integer
     */
    public int getTermID() {
        return termID;
    }
    /**
     * This method is a setter for the termID variable
     * @param termID the termID to be set
     */
    public void setTermID(int termID) {
        this.termID = termID;
    }
}
