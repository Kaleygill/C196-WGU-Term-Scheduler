package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Database.ScheduleManagementRepository;
import Entities.CourseEntity;

/**
 * Add Course Activity. Allows user to add a course to an existing term.
 */
public class addCourseActivity extends AppCompatActivity {

    private ScheduleManagementRepository scheduleManagementRepository;
    public static int numAlert;

    //id2 -> Term ID
    static int id2;

//Course and EditText Variables
    int courseID;
    String courseName;
    String courseStart;
    String courseEnd;
    String courseStatus;
    String courseNotes;
    int termID;
    EditText editName;
    EditText editStart;
    EditText editEnd;
    EditText editStatus;
    EditText editNotes;
    EditText editDate;

    //Start Date
    Calendar myCalendar=Calendar.getInstance();
    //End Date
    Calendar myCalendar2=Calendar.getInstance();

    //Start Date
    DatePickerDialog.OnDateSetListener  myDate;
    //End Date
    DatePickerDialog.OnDateSetListener  myDate2;

    Long date;

    /**
     * onCreate Method. Starts the Add Course Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        courseID=getIntent().getIntExtra("courseID",-1);
        courseName=getIntent().getStringExtra("courseName");
        courseStart=getIntent().getStringExtra("courseStart");
        courseEnd=getIntent().getStringExtra("courseEnd");
        courseStatus=getIntent().getStringExtra("courseStatus");
        courseNotes=getIntent().getStringExtra("courseNotes");
        termID=getIntent().getIntExtra("termID", -1);
        id2=termID;

        //Setting Edit Text to ID.
        editName=findViewById(R.id.courseName);
        editStart=findViewById(R.id.courseStart);
        editEnd = findViewById(R.id.courseEnd);
        editStatus=findViewById(R.id.courseStatus);
        editNotes = findViewById(R.id.courseNotes);

        //Setting Course variables to editText.
        if(courseID!=-1){
            editName.setText(courseName);
            editStart.setText(courseStart);
            editEnd.setText(courseEnd);
            editStatus.setText(courseStatus);
            editNotes.setText(courseNotes);

        }
        scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
        editStart = findViewById(R.id.courseStart);
        editEnd = findViewById(R.id.courseEnd);

        //Start
        myDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabel();
            }

        };

        //End
        myDate2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabel2();
            }

        };
    //Course Start on click to show Calendar.
        editStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(addCourseActivity.this, myDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //Course End on click to show Calendar.
        editEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(addCourseActivity.this, myDate2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    //Update Labels to set formatting for Start Date EditText field
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStart.setText(sdf.format(myCalendar.getTime()));
        //editEnd.setText(sdf.format(myCalendar.getTime()));
    }

    //Update Labels to set formatting for End Date EditText field
    private void updateLabel2() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //editStart.setText(sdf.format(myCalendar.getTime()));
        editEnd.setText(sdf.format(myCalendar2.getTime()));
    }

    /**
     * Add Course To Term Method. When save button is clicked, course text fields are save to DB.
     */
    public void addCourseToTerm(View view) {
        CourseEntity c;

        if(courseID!=-1)c= new CourseEntity(courseID,editName.getText().toString(),editStart.getText().toString(),editEnd.getText().toString(),editStatus.getText().toString(),editNotes.getText().toString(),termID);
        else{
            List<CourseEntity> allCourses=scheduleManagementRepository.getAllCourses();
            courseID=allCourses.get(allCourses.size()-1).getCourseID();
            c= new CourseEntity(++courseID,editName.getText().toString(),editStart.getText().toString(),editEnd.getText().toString(),editStatus.getText().toString(),editNotes.getText().toString(),termID);
        }
        scheduleManagementRepository.insert(c);
        Intent intent = new Intent(addCourseActivity.this, TermDetailActivity.class);
        startActivity(intent);
    }
}
