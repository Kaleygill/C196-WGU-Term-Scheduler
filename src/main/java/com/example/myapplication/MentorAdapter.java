package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Entities.MentorEntity;

/**
 * Course Instructor Adapter. Sets the RecyclerView. Provides access to data.
 */
public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {
    class MentorViewHolder extends RecyclerView.ViewHolder {
        private final TextView mentorItemView;

        private MentorViewHolder(View itemView) {
            super(itemView);
            mentorItemView = itemView.findViewById(R.id.termTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final MentorEntity current = mMentor.get(position);
                    Intent intent = new Intent(context, MentorDetailActivity.class);
                    intent.putExtra("mentorName", current.getMentorName());
                    intent.putExtra("mentorEmail", current.getMentorEmail());
                    intent.putExtra("mentorPhone", current.getMentorPhone());
                    intent.putExtra("mentorID", current.getMentorID());
                    //intent.putExtra("mentor", current.getCourseID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<MentorEntity> mMentor;

    public MentorAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     * onCreate Method to start Recycler View
     */
    @Override
    public MentorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new MentorViewHolder(itemView);
    }

    /**
     * onBindViewHolder. Gets Current assessment.
     */
    @Override
    public void onBindViewHolder(MentorViewHolder holder, int position) {
        if(mMentor != null) {
            final MentorEntity current = mMentor.get(position);
            holder.mentorItemView.setText(current.getMentorName());
        } else {
            holder.mentorItemView.setText("No Word");
        }
    }

    /**
     * Get Item Count Method. Determines Size of assessments.
     */
    @Override
    public int getItemCount() {
        if(mMentor != null)
            return mMentor.size();
        else return 0;
    }

    public void setWords(List<MentorEntity> words) {
        mMentor = words;
        notifyDataSetChanged();
    }
}
