package Database;

import android.app.Application;

import java.util.List;

import DAO.AssessmentDAO;
import DAO.CourseDAO;
import DAO.MentorDAO;
import DAO.TermDao;
import Entities.AssessmentEntity;
import Entities.CourseEntity;
import Entities.MentorEntity;
import Entities.TermEntity;

/**
 *Schedule Management Repository. creates, deletes, edits objects.
 */
public class ScheduleManagementRepository {
    private AssessmentDAO mAssessmentDAO;
    private CourseDAO mCourseDAO;
    private MentorDAO mMentorDao;
    private TermDao mTermDAO;

    private List<AssessmentEntity> mAllAssessments;
    //private LiveData<List<AssessmentEntity>> mAssociatedAssessments;
    private List<CourseEntity> mAllCourses;
    //private LiveData<List<CourseEntity>> mAssociatedCourses;
    private List<MentorEntity> mAllMentors;
    //private LiveData<List<MentorEntity>> mAssociatedMentors;
    private List<TermEntity> mAllTerms;
    private int termID;
    private int courseID;


    /**
     *Gets DBand sets to DOA Variables.
     */
    public ScheduleManagementRepository(Application application) {
        ScheduleManagementDatabase db = ScheduleManagementDatabase.getDatabase(application);
        mAssessmentDAO = db.assessmentDAO();
        mCourseDAO = db.courseDAO();
        mMentorDao = db.mentorDAO();
        mTermDAO = db.termDAO();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *Get all Assessments
     */
    public List<AssessmentEntity> getAllAssessments() {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(() ->{
            mAllAssessments= mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
    } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }

    /**
     *Get all courses
     */
    public List<CourseEntity> getAllCourses() {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(() ->{
            mAllCourses= mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }

    /**
     *Get all Course Instructors
     */
    public List<MentorEntity> getAllMentors() {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(() ->{
            mAllMentors= mMentorDao.getAllMentors();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllMentors;
    }

    /**
     *Get all terms
     */
    public List<TermEntity> getAllTerms() {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(() ->{
            mAllTerms= mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }

    /**
     *Insert Assessment in DB.
     */
    //Insert Assessment
    public void insert (AssessmentEntity assessmentEntity) {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(()->{
            mAssessmentDAO.insert((assessmentEntity));
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *Insert Course in DB.
     */
    public void insert (CourseEntity courseEntity) {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(()->{
            mCourseDAO.insert((courseEntity));
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *Insert Course Instructor in DB.
     */
    public void insert (MentorEntity mentorEntity) {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(()->{
            mMentorDao.insert((mentorEntity));
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *Insert Term in DB.
     */
    public void insert (TermEntity termEntity) {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(()->{
            mTermDAO.insert((termEntity));
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *Delete Assessment in DB.
     */
    public void delete (AssessmentEntity assessmentEntity) {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(()->{
            mAssessmentDAO.delete((assessmentEntity));
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *Delete Course in DB.
     */
    public void delete (CourseEntity courseEntity) {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(()->{
            mCourseDAO.delete((courseEntity));
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *Delete Course Instructor in DB.
     */
    public void delete (MentorEntity mentorEntity) {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(()->{
            mMentorDao.delete((mentorEntity));
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *Delete Term in DB.
     */
    public void delete (TermEntity termEntity) {
        ScheduleManagementDatabase.databaseWriteExecutor.execute(()->{
            mTermDAO.delete((termEntity));
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}