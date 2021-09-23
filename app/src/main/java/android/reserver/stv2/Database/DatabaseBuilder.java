package android.reserver.stv2.Database;

import android.content.Context;
import android.reserver.stv2.DAO.AssessmentDAO;
import android.reserver.stv2.DAO.CourseDAO;
import android.reserver.stv2.DAO.NoteDAO;
import android.reserver.stv2.DAO.TermDAO;
import android.reserver.stv2.Entities.Assessment;
import android.reserver.stv2.Entities.Course;
import android.reserver.stv2.Entities.Note;
import android.reserver.stv2.Entities.Term;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


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
 * This class is abstract and is a database builder for the RoomDatabase. It declares the entities
 * used in the database and sets up the abstract DAOs used to access it.
 */
@Database(entities = {Term.class, Course.class, Assessment.class, Note.class}, version = 1,
        exportSchema = false) // Declares entities
public abstract class DatabaseBuilder extends RoomDatabase {

        public abstract TermDAO termDAO(); // creates abstract termDAO
        public abstract CourseDAO courseDAO(); // creates abstract courseDAO
        public abstract AssessmentDAO assessmentDAO(); // creates abstract assessmentDAO
        public abstract NoteDAO noteDAO(); //  creates abstract noteDAO

        private static volatile DatabaseBuilder INSTANCE; // creates database builder

    /**
     * This method is used to get the database.
     * @param context the context is a handle to the system used to access the database
     * @return the database builder
     */
        static DatabaseBuilder getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (DatabaseBuilder.class){
                    if(INSTANCE == null)    {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                DatabaseBuilder.class, "My Database.db")
                                .fallbackToDestructiveMigration().build();
                    }
                }
            }
            return INSTANCE; // return the instance of the database builder
        }
}

