package android.reserver.stv2.DAO;

import android.reserver.stv2.Entities.Course;

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
 * This is an Data Access Object interface for the course entity model. It allows for CRUD
 * operations to be performed on the database.
 */

@Dao
public interface CourseDAO {

    /**
     * This method is an interface insert statement to insert a course into the database
     * @param course the course to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (Course course); // Insert method

    /**
     * This method is an interface update statement to update a course into the database
     * @param course the course to be updated
     */
    @Update
    void update(Course course); // Update method

    /**
     * This method is an interface delete statement to delete a course into the database
     * @param course the course to be deleted
     */
    @Delete
    void delete(Course course); // Delete method

    /**
     *  This method is an interface query statement to execute a query on the database
     * @return the query results
     */
    @Query("SELECT * FROM course_table ORDER BY courseID ASC") // Optional Query method
    List<Course> getAllCourses();

}
