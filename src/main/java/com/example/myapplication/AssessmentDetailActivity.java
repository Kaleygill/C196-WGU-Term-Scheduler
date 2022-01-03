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
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Database.ScheduleManagementRepository;
import Entities.AssessmentEntity;

/**
 * Assessment Detail Activity. Displays selected Assessment and all data.
 */
public class AssessmentDetailActivity extends AppCompatActivity {

    private ScheduleManagementRepository scheduleManagementRepository;

    //Assessment Variables
    int assessmentID;
    String assessmentName;
    String assessmentType;
    String assessmentDate;
    int courseID;

    //Edit Text Variables
    EditText editAssessmentName;
    EditText editAssessmentType;
    EditText editAssessmentDate;

    //Get Current Assessment
    AssessmentEntity currentAssessment;
    public static int numAssessments;

    Long date;
    public static int numAlert;

    //Date
    Calendar myCalendar=Calendar.getInstance();

    //Date
    DatePickerDialog.OnDateSetListener  myDate;

    Long Date;

    /**
     * onCreate Method. Start the Assessment Detail Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        //Gets selected Assessments ID to display in Activity.
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        //if(assessmentID == -1) assessmentID = addCourseActivity.id2;
        scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
        List<AssessmentEntity> allAssessments = scheduleManagementRepository.getAllAssessments();

        //Sets the selected Activity and loads text fields.
        for(AssessmentEntity a:allAssessments) {
            if(a.getAssessmentID() == assessmentID) {
                currentAssessment = a;
                assessmentName = currentAssessment.getAssessmentName();
                assessmentType = currentAssessment.getAssessmentType();
                assessmentDate = currentAssessment.getAssessmentDate();
                courseID = currentAssessment.getCourseID();
            }
        }

        //Set Edit Text Fields to IDS
        editAssessmentName = findViewById(R.id.assessmentName);
        editAssessmentType = findViewById(R.id.assessmentType);
        editAssessmentDate = findViewById(R.id.assessmentDate);

        if (currentAssessment != null) {
            assessmentName =getIntent().getStringExtra("assessmentName");
            assessmentType = getIntent().getStringExtra("assessmentType");
            assessmentDate = getIntent().getStringExtra("assessmentDate");
    }
        if (assessmentID != -1) {
            editAssessmentName.setText(assessmentName);
            editAssessmentType.setText(assessmentType);
            editAssessmentDate.setText(assessmentDate);
        }

        //Date
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

        //Date on click to show Calendar.
        editAssessmentDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(AssessmentDetailActivity.this, myDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    /**
     * Assessment Save Button. Saves Assessment changes to DB.
     */
    public void saveAssessment(View view) {
        AssessmentEntity a;

        if(assessmentID!=-1)a= new AssessmentEntity(assessmentID,editAssessmentName.getText().toString(),editAssessmentType.getText().toString(), editAssessmentDate.getText().toString(), courseID);
        else{
            List<AssessmentEntity> allAssessments =scheduleManagementRepository.getAllAssessments();
            assessmentID=allAssessments.get(allAssessments.size()-1).getAssessmentID();
            a= new AssessmentEntity(++assessmentID,editAssessmentName.getText().toString(),editAssessmentType.getText().toString(), editAssessmentDate.getText().toString(), courseID);
        }
        scheduleManagementRepository.insert(a);
        Intent intent = new Intent(AssessmentDetailActivity.this, CourseDetailActivity.class);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }

    //Update Label set Format for Edit Text
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editAssessmentDate.setText(sdf.format(myCalendar.getTime()));
    }

    //onCreateOptionMenu. Inflates menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assessment, menu);
        return true;
    }


    /**
     * onOptionItemSelected Method. Sets up the delete, sharing, and notification options.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int assessmentID = item.getItemId();
        if (assessmentID == R.id.delete) {
            scheduleManagementRepository.delete(currentAssessment);
            Intent intent = new Intent(getApplicationContext(), CourseDetailActivity.class);
            intent.putExtra("courseID", courseID);
            startActivity(intent);
        }

        if (assessmentID == R.id.sharing) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "I passed my assessment!!");
            sendIntent.putExtra(Intent.EXTRA_TITLE, "My Assessment");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }
        if (assessmentID == R.id.assessmentNotification) {
            Intent intent=new Intent(AssessmentDetailActivity.this,MyReceiver.class);
            intent.putExtra("key","Assessment Reminder for: " + assessmentName + "Type: " + assessmentType + " at " + assessmentDate);
            PendingIntent sender= PendingIntent.getBroadcast(AssessmentDetailActivity.this,++numAlert,intent,0);
            AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            String start = editAssessmentDate.getText().toString();
            Date assessmentDate = null;
            try {
                assessmentDate = sdf.parse(start);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long trigger = assessmentDate.getTime();
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
