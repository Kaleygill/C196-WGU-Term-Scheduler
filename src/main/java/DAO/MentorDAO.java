package DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import Entities.MentorEntity;
/**
 *Mentor DAO Interface. Holds SQL statements for database.
 */
@Dao
public interface MentorDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MentorEntity mentor);

    @Delete
    void delete(MentorEntity mentor);

    @Query("DELETE FROM mentor_table")
    void deleteAllMentors();

    @Query("SELECT * FROM mentor_table ORDER BY mentorID ASC")
    List<MentorEntity> getAllMentors();

    @Query ("SELECT * FROM mentor_table WHERE courseID= :courseID ORDER BY mentorName ASC")
    LiveData<List<MentorEntity>> getAllAssociatedMentors(int courseID);
}
