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
import Entities.TermEntity;

/**
 * Add Term Method. When save button is clicked, course text fields are save to DB.
 */
public class AddTermActivity extends AppCompatActivity {

    private ScheduleManagementRepository scheduleManagementRepository;

    //Term Variables
    int termID;
    String termName;
    String termStart;
    String termEnd;

    //Edit Term Details
    EditText editName;
    EditText editStart;
    EditText editEnd;

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
     * onCreate Method. Starts the Add Term Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
        List<TermEntity> allTerms = scheduleManagementRepository.getAllTerms();

        editName = findViewById(R.id.termName);
        editStart = findViewById(R.id.termStart);
        editEnd = findViewById(R.id.termEnd);

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

                new DatePickerDialog(AddTermActivity.this, myDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //Course End on click to show Calendar.
        editEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddTermActivity.this, myDate2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    //Update Label to format Start Date EditText
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStart.setText(sdf.format(myCalendar.getTime()));
        //editEnd.setText(sdf.format(myCalendar.getTime()));
    }

    //Update Label to format Start Date EditText
    private void updateLabel2() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        //editStart.setText(sdf.format(myCalendar.getTime()));
        editEnd.setText(sdf.format(myCalendar2.getTime()));
    }

    /**
     * Term Save Button. When save button is clicked, course text fields are save to DB.
     */
    public void saveTerm(View view) {
        TermEntity t;

        if(termID!=-1)t= new TermEntity(termID,editName.getText().toString(),editStart.getText().toString(),editEnd.getText().toString());
        else{
            List<TermEntity> allTerms=scheduleManagementRepository.getAllTerms();
            termID=allTerms.get(allTerms.size()-1).getTermID();
            t= new TermEntity(++termID,editName.getText().toString(),editStart.getText().toString(),editEnd.getText().toString());
        }
        scheduleManagementRepository.insert(t);
        Intent intent = new Intent(AddTermActivity.this,TermActivity.class);
        startActivity(intent);
    }


}