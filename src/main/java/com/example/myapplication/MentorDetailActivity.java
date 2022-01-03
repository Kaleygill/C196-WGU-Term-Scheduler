package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Database.ScheduleManagementRepository;
import Entities.MentorEntity;

/**
 * Course Instructor Detail Activity. Displays selected Course Instructors and all data.
 */
public class MentorDetailActivity extends AppCompatActivity {

    private ScheduleManagementRepository scheduleManagementRepository;

    static int id3;

    //Course Instructors Variables
    int mentorID;
    String mentorName;
    String mentorEmail;
    String mentorPhone;
    int courseID;

    //EditText Variables
    EditText editMentorName;
    EditText editMentorEmail;
    EditText editMentorPhone;

    MentorEntity currentMentor;
    //public static int numMentors;


    /**
     * onCreate Method. Starts the Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_detail);

        mentorID = getIntent().getIntExtra("mentorID", -1);
        scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
        List<MentorEntity> allMentors = scheduleManagementRepository.getAllMentors();

        /*mentorName = getIntent().getStringExtra("mentorName");
        mentorEmail = getIntent().getStringExtra("mentorEmail");
        mentorPhone = getIntent().getStringExtra("mentorPhone");*/

        //courseID = getIntent().getIntExtra("courseID", -1);


        //id3=courseID;
        //if ( mentorID == -1) mentorID = CourseDetailActivity.id3;

        //Get Current Course Instructor
        for(MentorEntity m:allMentors) {
            if(m.getMentorID() == mentorID) {
                currentMentor = m;
                mentorName = currentMentor.getMentorName();
                mentorEmail = currentMentor.getMentorEmail();
                mentorPhone = currentMentor.getMentorPhone();
                courseID = currentMentor.getCourseID();
            }
        }

        //Sets Edit Text to IDs
        editMentorName = findViewById(R.id.mName);
        editMentorEmail = findViewById(R.id.mEmail);
        editMentorPhone = findViewById(R.id.mPhone);

        //Gets current Course Instructor
        if(currentMentor != null) {
            mentorName = getIntent().getStringExtra("mentorName");
            mentorEmail = getIntent().getStringExtra("mentorEmail");
            mentorPhone = getIntent().getStringExtra("mentorPhone");
        }

        //Sets Edit Text to Course Instructors Variables.
        if (mentorID != -1) {
            editMentorName.setText(mentorName);
            editMentorEmail.setText(mentorEmail);
            editMentorPhone.setText(mentorPhone);
        }

    }

    /**
     * Save Course Instructor button. Saves Courses details to DB.
     */
    public void saveMentor(View view) {
        MentorEntity m;

        if(mentorID!=-1)m= new MentorEntity(mentorID,editMentorName.getText().toString(),editMentorEmail.getText().toString(), editMentorPhone.getText().toString(), courseID);
        else{
            List<MentorEntity> allMentors =scheduleManagementRepository.getAllMentors();
            mentorID=allMentors.get(allMentors.size()-1).getMentorID();
            m= new MentorEntity(++mentorID,editMentorName.getText().toString(),editMentorEmail.getText().toString(), editMentorPhone.getText().toString(), courseID);
        }
        scheduleManagementRepository.insert(m);
        Intent intent = new Intent(MentorDetailActivity.this, CourseDetailActivity.class);
        intent.putExtra("courseID", courseID);
        startActivity(intent);
    }

    /**
     * onCreateOptionsMenu. Inflates menu-delete.
     */
      public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deletementor, menu);
        return true;
    }

    /**
     * onOptionsItemSelected method. Inflates the delete course instructor.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int mentorID = item.getItemId();
        if (mentorID == R.id.deleteMentor) {
            scheduleManagementRepository.delete(currentMentor);
            Intent intent = new Intent(getApplicationContext(), CourseDetailActivity.class);
            intent.putExtra("courseID", courseID);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
