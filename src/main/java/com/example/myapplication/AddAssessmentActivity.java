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
import Entities.AssessmentEntity;

/**
 * Add Assessment Activity. Allows the user to add an Assessment to an existing Course.
 *
 * @author Koala
 */
public class AddAssessmentActivity extends AppCompatActivity {

    //Assessment Variables
    private ScheduleManagementRepository scheduleManagementRepository;
    public static int numAlert;
    static int id2;
    int courseID;
    int assessmentID;
    String assessmentName;
    String assessmentType;
    String assessmentDate;
    long date;

    //Edit Text Variables
    EditText editName;
    EditText editType;
    EditText editDate;
    Calendar myCalendar = Calendar.getInstance() ;
    DatePickerDialog.OnDateSetListener myDate;

    /**
     * onCreate Method. Starts to Add Assessment Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        assessmentID = getIntent().getIntExtra("assessmentID",-1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        assessmentType = getIntent().getStringExtra("assessmentType");
        assessmentDate = getIntent().getStringExtra("assessmentDate");
        courseID = getIntent().getIntExtra("courseID", -1);
        id2 = courseID;

        //Setting EditText IDs to Edit Text variables
        editName = findViewById(R.id.assessmentName);
        editType = findViewById(R.id.assessmentType);
        editDate = findViewById(R.id.assessmentDate);

        //Set Text to Edit text to Assessment Variables
        if(assessmentID != -1) {
            editName.setText(assessmentName);
            editType.setText(assessmentDate);
            editDate.setText(assessmentDate);
        }

        scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
        editDate = findViewById(R.id.assessmentDate);

        //Setting up calendar object for Edit Assessment Date Field
        myDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "MM/dd/yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                    updateLabel();
                }
            };

        //Setting editDate onClick Listener
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                new DatePickerDialog(AddAssessmentActivity.this, myDate, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        }

        //Update Label for formatting Text.
        private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editDate.setText(sdf.format(myCalendar.getTime()));
    }

    /**
     * Add Assessment Save Method. When Save button is clicked, Fields are saved to the DB.
     */
    public void addAssessmentToCourse(View view ) {
        AssessmentEntity a;
        if (assessmentID != -1) a = new AssessmentEntity(assessmentID, editName.getText().toString(), editType.getText().toString(), editDate.getText().toString(), courseID);
        else {
            List<AssessmentEntity> allAssessments = scheduleManagementRepository.getAllAssessments();
            assessmentID = allAssessments.get(allAssessments.size()-1).getAssessmentID();
            a = new AssessmentEntity(++assessmentID,editName.getText().toString(), editType.getText().toString(), editDate.getText().toString(), courseID);
        }
        scheduleManagementRepository.insert(a);
        Intent intent = new Intent(AddAssessmentActivity.this,CourseDetailActivity.class);
        startActivity(intent);
    }

}
