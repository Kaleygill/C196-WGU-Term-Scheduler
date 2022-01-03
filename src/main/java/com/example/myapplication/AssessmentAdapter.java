package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Entities.AssessmentEntity;

/**
 * Assessment Adapter. Sets the RecyclerView. Provides access to data.
 */
public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.termTextView);
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final AssessmentEntity current = mAssessment.get(position);
                    Intent intent = new Intent(context, AssessmentDetailActivity.class);
                    intent.putExtra("assessmentName", current.getAssessmentName());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("assessmentDate", current.getAssessmentDate());
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<AssessmentEntity> mAssessment;

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     * onCreate Method to start Recycler View
     */
    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    /**
     * onBindViewHolder. Gets Current assessment.
     */
    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        if ( mAssessment != null) {
            final AssessmentEntity current = mAssessment.get(position);
            holder.assessmentItemView.setText(current.getAssessmentName());
        } else {
            holder.assessmentItemView.setText("No Word");
        }

    }

    /**
     * Get Item Count Method. Determines Size of assessments.
     */
    @Override public int getItemCount() {
        if (mAssessment != null)
            return mAssessment.size();
        else return 0;
    }

    public void setWords(List<AssessmentEntity> words) {
        mAssessment = words;
        notifyDataSetChanged();
    }
}
