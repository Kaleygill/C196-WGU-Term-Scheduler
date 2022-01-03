package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 *Main Activity. Main screen that allows user to navigate to all terms.
 * @own.png sourced from WGU website. All credits goes to the WGU university.
 */
public class MainActivity extends AppCompatActivity {

    /**
     *onCreate method. Starts Activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *Navigate to Terms Activity
     */
    public void termScreen(View view) {
        Intent intent = new Intent(MainActivity.this, TermActivity.class);
        startActivity(intent);
    }

/*    *//**
     *Navigate to Courses Activity
     *//*
    public void courseScreen(View view) {
        Intent intent = new Intent(MainActivity.this, CourseActivity.class);
        startActivity(intent);
    }

    *//**
     *Navigate to Assessment Activity
     *//*
    public void assessmentScreen(View view) {
        Intent intent = new Intent(MainActivity.this, AssessmentsActivity.class);
        startActivity(intent);
    }

    *//**
     *Navigate to Mentor Activity
     *//*
    public void mentorScreen(View view) {
        Intent intent = new Intent(MainActivity.this, MentorActivity.class);
        startActivity(intent);
    }*/

}