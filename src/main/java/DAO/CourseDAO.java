package DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Entities.CourseEntity;
/**
 *Course  DAO Interface. Holds SQL statements for database.
 */
@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseEntity course);

    @Delete
    void delete(CourseEntity course);

    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table ORDER BY courseID ASC")
    List<CourseEntity> getAllCourses();

    @Query ("SELECT * FROM course_table WHERE termID= :termID ORDER BY courseName ASC")
    LiveData<List<CourseEntity>> getAllAssociatedCourses(int termID);
}
