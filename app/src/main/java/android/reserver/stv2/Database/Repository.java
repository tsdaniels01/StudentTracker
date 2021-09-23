package android.reserver.stv2.Database;

import android.app.Application;
import android.reserver.stv2.DAO.AssessmentDAO;
import android.reserver.stv2.DAO.CourseDAO;
import android.reserver.stv2.DAO.NoteDAO;
import android.reserver.stv2.Entities.Assessment;
import android.reserver.stv2.Entities.Course;
import android.reserver.stv2.Entities.Note;
import android.reserver.stv2.Entities.Term;
import android.reserver.stv2.DAO.TermDAO;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
 * This class is used as a repository to manage the database. It retrieves the database tables and
 * stores them in lists to allow POJO methods for managing data in the application.
 */
public class Repository {

    private TermDAO mTermDAO; // creates a private instance of the TermDAO
    private CourseDAO mCourseDAO; // creates a private instance of the CourseDAO
    private AssessmentDAO mAssessmentDAO; // creates a private instance of the AssessmentDAO
    private NoteDAO mNoteDAO; // creates a private instance of the NoteDAO

    private List<Term> mAllTerms; // creates a list to store the term table from the database
    private List<Course> mAllCourses; // creates a list to store the course table from the database
    // creates a list to store the assessment table from the database
    private List<Assessment> mAllAssessments;
    private List<Note>  mAllNotes; // creates a list to store the note table from the database

    private static int NUMBER_OF_THREADS = 4; // creates a variable to hold the number of threads
    // creates an interface to execute threads
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /**
     * This method is used to assign the database tables to their respective DAO class variables
     * @param application the stated application
     */
    public Repository(Application application) {
        // Creates a variable of the database class to retrieve the database
        DatabaseBuilder db = DatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO(); // Assigns the database termDAO to the mTermDAO variable
        mCourseDAO = db.courseDAO(); // Assigns the database courseDAO to the mCourseDAO variable
        // Assigns the database assessmentDAO to the mAssessmentDAO variable
        mAssessmentDAO = db.assessmentDAO();
        // Assigns the database noteDAO to the mNoteDAO variable
        mNoteDAO = db.noteDAO();

    }

    /**
     * This method is used to retrieve all the terms from the database and stores them in a list
     * @return allTerms (mAllTerms)
     */
    public List<Term> getAllTerms() {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mAllTerms = mTermDAO.getAllTerms(); // assigns all terms to variable
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
        return mAllTerms; // list of terms returned
    }
    /**
     * This method is used to retrieve all the courses from the database and stores them in a list
     * @return allCourses (mAllCourses)
     */
    public List<Course> getAllCourses() {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mAllCourses = mCourseDAO.getAllCourses(); // assigns allCourses to variable
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
        return mAllCourses; // list of courses returned
    }

    /**
     * This method is used to retrieve all the assessments from the database and stores them in a
     * list
     * @return allAssessments (mAllAssessments)
     */
    public List<Assessment> getAllAssessments() {
        databaseExecutor.execute(() -> { // calls an execution on the database
            // assigns allAssessments to variable
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
        return mAllAssessments; // list of assessments returned
    }
    /**
     * This method is used to retrieve all the notes from the database and stores them in a list
     * @return allNotes (mAllNotes)
     */
    public List<Note> getAllNotes() {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mAllNotes = mNoteDAO.getAllNotes(); // assigns allNotes to variable
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
        return mAllNotes; // list of notes returned
    }

    /**
     * This method is used to insert a new term into the database
     * @param term the term to be inserted
     */
    public void insert(Term term) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mTermDAO.insert(term); // inserts term
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

    /**
     * This method is used to insert a new course into the database
     * @param course the course to be inserted
     */
    public void insert(Course course) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mCourseDAO.insert(course); // inserts course
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

    /**
     * This method is used to insert a new assessment into the database
     * @param assessment the assessment to be inserted
     */
    public void insert(Assessment assessment) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mAssessmentDAO.insert(assessment); // inserts assessment
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

    /**
     * This method is used to insert a new note into the database
     * @param note the note to be inserted
     */
    public void insert(Note note) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mNoteDAO.insert(note); // inserts note
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

    /**
     * This method is used to update a preexisting term in the database
     * @param term the term to be updated
     */
    public void update(Term term) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mTermDAO.update(term); //updates term
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

    /**
     * This method is used to update a preexisting course in the database
     * @param course the course to be updated
     */
    public void update(Course course) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mCourseDAO.update(course); // updates course
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

    /**
     * This method is used to update a preexisting assessment in the database
     * @param assessment the assessment to be updated
     */
    public void update(Assessment assessment) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mAssessmentDAO.update(assessment); // updates assessment
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

    /**
     * This method is used to update a preexisting note in the database
     * @param note the note to be updated
     */
    public void update(Note note) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mNoteDAO.update(note); // updates note
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

    /**
     * This method is used to delete a term from the database
     * @param term the term to be deleted
     */
    public void delete(Term term) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mTermDAO.delete(term); // deletes term
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

    /**
     * This method is used to delete a a course from the database
     * @param course the course to be deleted
     */
    public void delete(Course course) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mCourseDAO.delete(course); // deletes course
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

    /**
     * This method is used to delete an assessment from the database
     * @param assessment the assessment to be deleted
     */
    public void delete(Assessment assessment) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mAssessmentDAO.delete(assessment); // deletes assessment
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

    /**
     * This method is used to delete a note from the database
     * @param note the note to be deleted
     */
    public void delete(Note note) {
        databaseExecutor.execute(() -> { // calls an execution on the database
            mNoteDAO.delete(note); // deletes note
        });
        try { //possible exception
            Thread.sleep(1000); // pauses current thread
        } catch (InterruptedException e) { // catches possible exception
            e.printStackTrace(); // prints exception to stack trace
        }
    }

}
