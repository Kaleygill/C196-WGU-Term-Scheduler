package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Database.ScheduleManagementRepository;

public class CourseActivity extends AppCompatActivity {

/*  private ScheduleManagementRepository scheduleManagementRepository;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
      //addCourseActivity.id2=-1;
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_courses);
      scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
      scheduleManagementRepository .getAllCourses();
      RecyclerView recyclerView = findViewById(R.id.associated_mentors);

      final CourseAdapter adapter = new CourseAdapter(this);
      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
      adapter.setWords(scheduleManagementRepository.getAllCourses());
  }

  public void addCourse (View view) {
      Intent intent = new Intent(CourseActivity.this, addCourseActivity.class);
      startActivity(intent);
  }*/


}
