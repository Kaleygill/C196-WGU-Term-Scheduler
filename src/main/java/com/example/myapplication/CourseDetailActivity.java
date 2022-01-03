package com.example.myapplication;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Database.ScheduleManagementRepository;
import Entities.AssessmentEntity;
import Entities.CourseEntity;
import Entities.MentorEntity;

/**
 * Course Detail Activity. Displays selected Course and all data.
 */
public class CourseDetailActivity extends AppCompatActivity {

    private ScheduleManagementRepository scheduleManagementRepository;

    //Course Variables
    int courseID;
    String courseName;
    String courseStart;
    String courseEnd;
    String courseStatus;
    String courseNotes;
    int termID;

    public static int numAlert;
    //Edit Text Variables
    EditText editCourseName;
    EditText editCourseStart;
    EditText editCourseEnd;
    EditText editCourseStatus;
    EditText editCourseNotes;

    //Start Date
    Calendar myCalendar=Calendar.getInstance();
    //End Date
    Calendar myCalendar2=Calendar.getInstance();

    //Start Date
    DatePickerDialog.OnDateSetListener  myDate;
    //End Date
    DatePickerDialog.OnDateSetListener  myDate2;

    long date;

    CourseEntity currentCourse;
    public static int numMentors;
    public static int numAssessments;

    /**
     * onCreate Method. Start the Assessment Detail Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        courseID = getIntent().getIntExtra("courseID", -1);
        if (courseID == -1) courseID = AddAssessmentActivity.id2;
        if (courseID == -1) courseID = AddMentorActivity.id3;
        scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
        List<CourseEntity> allCourses = scheduleManagementRepository.getAllCourses();

        //createNotificationChannel();


        //Gets all Courses
        for (CourseEntity c : allCourses) {
            if (c.getCourseID() == courseID) {
                currentCourse = c;
                courseName = currentCourse.getCourseName();
                courseStart = currentCourse.getCourseStart();
                courseEnd = currentCourse.getCourseEnd();
                courseStatus = currentCourse.getCourseStatus();
                courseNotes = currentCourse.getCourseNotes();
                termID = currentCourse.getTermID();
            }
        }

        //Sets EditText to IDs
        editCourseName = findViewById(R.id.courseName);
        editCourseStart = findViewById(R.id.courseStart);
        editCourseEnd = findViewById(R.id.courseEnd);
        editCourseStatus = findViewById(R.id.courseStatus);
        editCourseNotes = findViewById(R.id.courseNotes);


        //Sets EditText to Course Variables
        if (courseID != -1) {
            editCourseName.setText(courseName);
            editCourseStart.setText(courseStart);
            editCourseEnd.setText(courseEnd);
            editCourseStatus.setText(courseStatus);
            editCourseNotes.setText(courseNotes);
        }

        //Start
        myDate = (DatePickerDialog.OnDateSetListener) (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            updateLabel();
        };

        //End
        myDate2 = (view, year, monthOfYear, dayOfMonth) -> {
            String s1 = editCourseStart.getText().toString();
            // TODO Auto-generated method stub
            myCalendar2.set(Calendar.YEAR, year, Integer.parseInt(s1));
            myCalendar2.set(Calendar.MONTH, monthOfYear, Integer.parseInt(s1));
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth, Integer.parseInt(s1));
            myCalendar2.setTimeInMillis((System.currentTimeMillis()));

            String myFormat = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            updateLabel2();
        };


        //Course Start on click to show Calendar.
        editCourseStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(CourseDetailActivity.this, myDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //Course End on click to show Calendar.
        editCourseEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(CourseDetailActivity.this, myDate2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Mentors Recycler view
        scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.associated_mentors);
        final MentorAdapter adapter = new MentorAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<MentorEntity> filteredMentors = new ArrayList<>();
        for (MentorEntity c : scheduleManagementRepository.getAllMentors()) {
            if (c.getCourseID() == courseID) filteredMentors.add(c);
        }
        numMentors = filteredMentors.size();
        adapter.setWords(filteredMentors);

        //Assessments Recycler View
        scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
        RecyclerView recyclerView1 = findViewById(R.id.associated_assessments);
        final AssessmentAdapter adapter1 = new AssessmentAdapter(this);
        recyclerView1.setAdapter(adapter1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        List<AssessmentEntity> filteredAssessments = new ArrayList<>();
        for (AssessmentEntity a : scheduleManagementRepository.getAllAssessments()) {
            if (a.getCourseID() == courseID) filteredAssessments.add(a);
        }
        numAssessments = filteredAssessments.size();
        adapter1.setWords(filteredAssessments);
    }

    //Update Label to format Start Edit Text.
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editCourseStart.setText(sdf.format(myCalendar.getTime()));
        //editEnd.setText(sdf.format(myCalendar.getTime()));
    }

    //Update Label to format End Edit Text
    private void updateLabel2() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //editStart.setText(sdf.format(myCalendar.getTime()));
        editCourseEnd.setText(sdf.format(myCalendar2.getTime()));
    }

    /**
     *Add Course Button - Navigates to activity_add_mentor.
     */
    public void addMentor (View view) {
        Intent intent = new Intent(CourseDetailActivity.this, AddMentorActivity.class);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }

    /**
     *Add Assessment Button = Navigates to activity_add_mentor
     */
    public void addAssessment (View view) {
        Intent intent = new Intent(CourseDetailActivity.this, AddAssessmentActivity.class);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }


    /**
     *Save Course Button. Saves fields to Database.
     */
    public void saveCourse(View view) {
        CourseEntity c;

        if(courseID!=-1)c= new CourseEntity(courseID,editCourseName.getText().toString(),editCourseStart.getText().toString(), editCourseEnd.getText().toString(), editCourseStatus.getText().toString(),  editCourseNotes.getText().toString(), termID);
        else{
            List<CourseEntity> allCourses =scheduleManagementRepository.getAllCourses();
            courseID=allCourses.get(allCourses.size()-1).getCourseID();
            c= new CourseEntity(++courseID,editCourseName.getText().toString(),editCourseStart.getText().toString(), editCourseEnd.getText().toString(),  editCourseStatus.getText().toString(),  editCourseNotes.getText().toString(), termID);
        }
        scheduleManagementRepository.insert(c);
        Intent intent=new Intent(CourseDetailActivity.this,TermDetailActivity.class);
        intent.putExtra("termID", termID);
        startActivity(intent);
    }

    /**
     *onCreateOptionsMenu Method. Inflated Course detail menu.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coursedetail, menu);
        return true;
    }

    /**
     *onOptionItemSelected. Sets up MenuITem.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int courseID = item.getItemId();

        //Delete a course
        if (courseID == R.id.delete) {
            scheduleManagementRepository.delete(currentCourse);
            Intent intent = new Intent(getApplicationContext(), TermActivity.class);
            intent.putExtra("courseID", courseID);
            startActivity(intent);
        }

        //Share Course Notes
        if (courseID == R.id.sharing) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, courseNotes);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "My Course Notes");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        //Set Start Notifications
        if (courseID == R.id.startNotifications) {
            Intent intent=new Intent(CourseDetailActivity.this,MyReceiver.class);
            intent.putExtra("key","Course Reminder for: " + courseName + " at " + courseStart);
            PendingIntent sender= PendingIntent.getBroadcast(CourseDetailActivity.this,++numAlert,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            String start = editCourseStart.getText().toString();
            Date startDate = null;
            try {
                startDate = sdf.parse(start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long trigger = startDate.getTime();
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }

        //Set End Date Notifications
        if (courseID == R.id.endNotifications) {
            Intent intent=new Intent(CourseDetailActivity.this,MyReceiver.class);
            intent.putExtra("key","Course Reminder for: " + courseName + " at " + courseEnd);
            PendingIntent sender= PendingIntent.getBroadcast(CourseDetailActivity.this,++numAlert,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            String start = editCourseEnd.getText().toString();
            Date endDate = null;
            try {
                endDate = sdf.parse(start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long trigger = endDate.getTime();
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
