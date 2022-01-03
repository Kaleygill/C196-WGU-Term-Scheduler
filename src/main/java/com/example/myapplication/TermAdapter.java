package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Entities.TermEntity;

/**
 * Term Adapter. Sets the RecyclerView. Provides access to data.
 */
public class TermAdapter  extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.termTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final TermEntity current = mTerm.get(position);
                    Intent intent = new Intent(context, TermDetailActivity.class);
                    intent.putExtra("termName", current.getTermName());
                    intent.putExtra("termStart", current.getTermStart());
                    intent.putExtra("termEnd", current.getTermEnd());
                    //name:"termId" was causing conflict on Term Detail Activity causing Text fields not to populate.
                    // Making the adjustment in TermDetailActivity fixed the issue.
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<TermEntity> mTerm;

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     * onCreate Method to start Recycler View
     */
    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new TermViewHolder(itemView);
    }


    /**
     * onBindViewHolder. Gets Current Course.
     */
    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {
        if (mTerm != null) {
            final TermEntity current = mTerm.get(position);
            holder.termItemView.setText(current.getTermName());
        } else {
            holder.termItemView.setText("No Word");
        }

    }

    /**
     * Get Item Count Method. Determines Size of Terms.
     */
    @Override public int getItemCount() {
        if (mTerm != null)
            return mTerm.size();
        else return 0;
    }

    public void setWords(List<TermEntity> words) {
        mTerm = words;
        notifyDataSetChanged();
    }
}
