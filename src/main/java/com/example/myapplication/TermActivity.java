package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import Database.ScheduleManagementRepository;

/**
 * Term Activity.Main Term screen. Contains list of all Terms.
 */
public class TermActivity extends AppCompatActivity {

    private ScheduleManagementRepository scheduleManagementRepository;

    /**
     * onCreate Method. Starts the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        addCourseActivity.id2 = -1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        scheduleManagementRepository = new ScheduleManagementRepository(getApplication());
        scheduleManagementRepository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.terms);

        final TermAdapter adapter = new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setWords(scheduleManagementRepository.getAllTerms());
    }

    /**
     * Add Term button. Navigates user to add Term Activity.
     */
    public void nextScreen(View view) {
        Intent intent = new Intent(TermActivity.this, AddTermActivity.class);
        startActivity(intent);
    }
}