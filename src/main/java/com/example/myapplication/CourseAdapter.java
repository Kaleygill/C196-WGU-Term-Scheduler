package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Entities.CourseEntity;

/**
 * Course Adapter. Sets the RecyclerView. Provides access to data.
 */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.termTextView);
                itemView.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    final CourseEntity current = mCourse.get(position);
                    Intent intent = new Intent(context, CourseDetailActivity.class);
                    intent.putExtra("courseName", current.getCourseName());
                    intent.putExtra("courseStart", current.getCourseStart());
                    intent.putExtra("courseEnd", current.getCourseEnd());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("courseNotes", current.getCourseNotes());
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<CourseEntity> mCourse;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     * onCreate Method to start Recycler View
     */
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    /**
     * onBindViewHolder. Gets Current Course.
     */
    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if(mCourse != null) {
            final CourseEntity current = mCourse.get(position);
            holder.courseItemView.setText(current.getCourseName());
        } else {
            holder.courseItemView.setText("No Word");
        }
    }

    /**
     * Get Item Count Method. Determines Size of courses.
     */
    @Override public int getItemCount() {
        if(mCourse != null)
            return mCourse.size();
        else return 0;
    }

    public void setWords(List<CourseEntity> words) {
        mCourse = words;
        notifyDataSetChanged();
    }
}
