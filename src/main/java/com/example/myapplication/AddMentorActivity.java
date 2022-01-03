package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Database.ScheduleManagementRepository;
import Entities.MentorEntity;

/**
 * Add Course Instructor Activity. Allows User to add a Course instructor to an existing Course.
 */
public class AddMentorActivity extends AppCompatActivity {

    //Course and EditText Variables.
    private ScheduleManagementRepository scheduleManagementRepository;
    public static int numAlert;
    static int id3;
    int mentorID;
    String mentorName;
    String mentorEmail;
    String mentorPhone;
    int courseID;

    EditText editName;
    EditText editEmail;
    EditText editPhone;

    /**
     * onCreate Method. Starts the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mentor);
        mentorID = getIntent().getIntExtra("mentorID",-1);
        mentorName = getIntent().getStringExtra("mentorName");
        mentorPhone = getIntent().getStringExtra("mentorPhone");
        mentorEmail = getIntent().getStringExtra("mentorEmail");
        courseID = getIntent().getIntExtra("courseID", -1);
        id3 = courseID;

        //Sets the EditText to their IDs
        editName = findViewById(R.id.mentorName);
        editEmail = findViewById(R.id.mentorEmail);
        editPhone = findViewById(R.id.mentorPhone);

        //Sets the EditText to the Course Variables
        if(mentorID != -1) {
            editName.setText(mentorName);
            editEmail.setText(mentorEmail);
            editPhone.setText(mentorPhone);
        }

       scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
    }
    /**
     * Add Course Instructor To Course Method. When save button is clicked, course instructors text fields are save to DB.
     */
    public void addMentor(View view ) {
        MentorEntity m;
        if (mentorID != -1) m = new MentorEntity(mentorID, editName.getText().toString(), editEmail.getText().toString(), editPhone.getText().toString(), courseID);
        else {
            List<MentorEntity> allMentors = scheduleManagementRepository.getAllMentors();
            mentorID = allMentors.get(allMentors.size()-1).getMentorID();
            m = new MentorEntity(++mentorID,editName.getText().toString(), editEmail.getText().toString(), editPhone.getText().toString(), courseID);
        }
        scheduleManagementRepository.insert(m);
        Intent intent = new Intent(AddMentorActivity.this,CourseDetailActivity.class);
        startActivity(intent);
    }
}
