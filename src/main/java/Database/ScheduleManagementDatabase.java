package Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import DAO.AssessmentDAO;
import DAO.CourseDAO;
import DAO.MentorDAO;
import DAO.TermDao;
import Entities.AssessmentEntity;
import Entities.CourseEntity;
import Entities.MentorEntity;
import Entities.TermEntity;

/**
 *WGU Student Scheduler DB. Sets pre-determined Terms,courses, assessments, and course instructors.
 */
@Database(entities = {AssessmentEntity.class, CourseEntity.class, MentorEntity.class, TermEntity.class}, version = 1)
public abstract class ScheduleManagementDatabase extends RoomDatabase {
    public abstract AssessmentDAO assessmentDAO();
    public abstract CourseDAO courseDAO();
    public abstract MentorDAO mentorDAO();
    public abstract TermDao termDAO();
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static volatile ScheduleManagementDatabase INSTANCE;

    static ScheduleManagementDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScheduleManagementDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ScheduleManagementDatabase.class, "schedule_management_database.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }return INSTANCE;
    }

    /**
     *Sets room DB. Pulls SQL statements from associated DOA. Loads preset data into activities.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                AssessmentDAO mAssessmentDao = INSTANCE.assessmentDAO();
                CourseDAO mCourseDao = INSTANCE.courseDAO();
                MentorDAO mMentorDao = INSTANCE.mentorDAO();
                TermDao mTermDao = INSTANCE.termDAO();

                //Deletes current DB to start fresh upon restart.
                /*mAssessmentDao.deleteAllAssessments();
                mCourseDao.deleteAllCourses();
                mMentorDao.deleteAllMentors();;
                mTermDao.deleteAllTerms();*/

                /**
                 *Assessment Data for activity
                 */
                AssessmentEntity assessment = new AssessmentEntity(1, "assessment1", "10/25/2021", "Project", 1);
                mAssessmentDao.insert(assessment);
                assessment = new AssessmentEntity(2, "assessment2", "10/25/2021", "Project", 2);
                mAssessmentDao.insert(assessment);
                assessment = new AssessmentEntity(3, "assessment3", "10/25/2021", "Assessment", 3);
                mAssessmentDao.insert(assessment);
                assessment = new AssessmentEntity(4, "assessment4", "10/25/2021", "Assessment", 4);
                mAssessmentDao.insert(assessment);

                /**
                 *Course Data for activity
                 */
                //CourseEntity course = new CourseEntity(1, "C196", "1/1/2021", "2/28/2021", "Active", "This Class is so Difficult like C195", 1);
                //mCourseDao.insert(course);
                CourseEntity course = new CourseEntity(2, "C195", "01/01/2021", "02/28/2021", "Active", "This Class is so Difficult :(", 1);
                mCourseDao.insert(course);
                course = new CourseEntity(3, "C949", "06/01/2021", "09/28/2021", "Inactive", "Excited to start this class :)", 2);
                mCourseDao.insert(course);
                course = new CourseEntity(4, "D191", "06/01/2021", "09/28/2021", "Inactive", "Excited to start this class :)", 2);
                mCourseDao.insert(course);

                /**
                 *Course Instructor data for activity
                 */
                MentorEntity mentor = new MentorEntity(1, "Course Instructor Name", "Instructor@wgu.edu", "801-111-1111", 4);
                mMentorDao.insert(mentor);
                mentor = new MentorEntity(2, "Course Instructor Name", "Instructor@wgu.edu", "801-111-1111", 2);
                mMentorDao.insert(mentor);
                mentor = new MentorEntity(3, "Course Instructor Name", "Instructor@wgu.edu", "801-111-1111", 3);
                mMentorDao.insert(mentor);

                /**
                 *Term Data for activity
                 */
                TermEntity term = new TermEntity(1, "Term 1", "01/01/2021", "06/30/2021");
                mTermDao.insert(term);
                term = new TermEntity(2, "Term 2", "07/01/2021", "12/31/2021");
                mTermDao.insert(term);

            });
        }
    };
}
