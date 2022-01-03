package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import Database.ScheduleManagementRepository;
import Entities.CourseEntity;
import Entities.TermEntity;

/**Term Detail Activity.Shows the user the details of selected term and associated courses.
 * Get Item Count Method. Determines Size of assessments.
 */
public class TermDetailActivity extends AppCompatActivity {

    private ScheduleManagementRepository scheduleManagementRepository;

    //Term variables
    int termID;
    String termName;
    String termStart;
    String termEnd;

    //Edit text variables
    EditText editName;
    EditText editStart;
    EditText editEnd;

    //Current Term
    TermEntity currentTerm;
    public static int numCourses;

    //Start Date
    Calendar myCalendar=Calendar.getInstance();
    //End Date
    Calendar myCalendar2=Calendar.getInstance();

    //Start Date
    DatePickerDialog.OnDateSetListener  myDate;
    //End Date
    DatePickerDialog.OnDateSetListener  myDate2;

    Long date;

    //OnCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        //name:"termId was set to termID which was incorrect. Fixing this issue allowed the text fields to populate.
        termID = getIntent().getIntExtra("termID", -1);
        if (termID == -1)termID = addCourseActivity.id2;
        scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
        List<TermEntity> allTerms = scheduleManagementRepository.getAllTerms();

        //Returning to term detail from Add course would not populate textfields.
        for(TermEntity t:allTerms) {
            if(t.getTermID()==termID) {
                currentTerm = t;
                termName = currentTerm.getTermName();
                termStart = currentTerm.getTermStart();
                termEnd = currentTerm.getTermEnd();
            }
        }

        //Setting up Edit Text IDs
        editName = findViewById(R.id.termName);
        editStart = findViewById(R.id.termStart);
        editEnd = findViewById(R.id.termEnd);

        if(termID!=-1){
            editName.setText(termName);
            editStart.setText(termStart);
            editEnd.setText(termEnd);
        }

        //Start Term Date
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

        //End Term Date
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

        /**
         * Term Start on click to show Calendar.
         */
        editStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(TermDetailActivity.this, myDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /**
         * Term End on click to show Calendar.
         */
        editEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(TermDetailActivity.this, myDate2, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        /**
         * Associated courses Recycler view. Will populate recycler view is courses that have the selected Term ID.
         */
        scheduleManagementRepository= new ScheduleManagementRepository(getApplication());
        RecyclerView recyclerView = findViewById(R.id.associated_mentors);
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CourseEntity> filteredCourses=new ArrayList<>();
        for(CourseEntity t:scheduleManagementRepository.getAllCourses()){
            if(t.getTermID()==termID)filteredCourses.add(t);
        }
        numCourses=filteredCourses.size();
        adapter.setWords(filteredCourses);
    }

    /**
     *  Format for EditStart Date
     */
    private void updateLabel() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editStart.setText(sdf.format(myCalendar.getTime()));
    }

    /**
     * Format for EditEnd Date
     */
    private void updateLabel2() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editEnd.setText(sdf.format(myCalendar2.getTime()));
    }

    /**
     *  AddCourse button. Will redirect user to add course screen.
     */
    public void addCourse(View view) {
        Intent intent=new Intent(TermDetailActivity.this,addCourseActivity.class);
        intent.putExtra("termID",termID);
        startActivity(intent);
    }

    /**
     * Save button. Saves any changes made on the Term Detail Screen.
     */
    public void saveTerm(View view) {
        TermEntity t;

        if(termID!=-1)t= new TermEntity(termID,editName.getText().toString(),editStart.getText().toString(), editEnd.getText().toString());
        else{
            List<TermEntity> allTerms =scheduleManagementRepository.getAllTerms();
            termID=allTerms.get(allTerms.size()-1).getTermID();
            t= new TermEntity(++termID,editName.getText().toString(),editStart.getText().toString(), editEnd.getText().toString());
        }
        scheduleManagementRepository.insert(t);
        Intent intent=new Intent(TermDetailActivity.this,TermActivity.class);
        startActivity(intent);
    }

    /**
     * Created option menu containing the delete option
     */
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    /**
     * Executes the delete method.Prevents user from deleting a Term with courses
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int termID = item.getItemId();

        if (termID == R.id.delete) {
            if(numCourses==0) {
                scheduleManagementRepository.delete(currentTerm);
                Intent intent=new Intent(getApplicationContext(),TermActivity.class);
                intent.putExtra("termID", termID);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"Can't delete a Term with Courses, Please remove courses from Term before deleting.",Toast.LENGTH_LONG).show();// make another toast
            }
        }


        return super.onOptionsItemSelected(item);
    }

}

