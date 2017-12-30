package com.example.android.bestreceipe.Adapter;

/**
 * Created by sunilkumar on 28/12/17.
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bestreceipe.Model.Steps;
import com.example.android.bestreceipe.R;
import com.vstechlab.easyfonts.EasyFonts;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.InstructionsHolder> {

    private ArrayList<Steps> mStepsList;
    private Context mContext;
    private Callbacks mCallbacks;

    public StepsAdapter(ArrayList<Steps> stepsList, Context context, Callbacks callbacks) {
        mStepsList = stepsList;
        mContext = context;
        mCallbacks = callbacks;
    }

    @Override
    public InstructionsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_item, parent, false);
        return new InstructionsHolder(view);
    }

    @Override
    public void onBindViewHolder(final InstructionsHolder holder, int position) {
        final Steps steps = mStepsList.get(position);
        holder.mStepsIdTv.setText(String.valueOf(steps.getId()));
        holder.mShortDescTv.setText(steps.getShortDescription());
        holder.mSteps = steps;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbacks.playStepVideo(steps, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mStepsList != null) {
            return mStepsList.size();
        } else return 0;
    }

    public ArrayList<Steps> getSteps() {
        return mStepsList;
    }

    public interface Callbacks {
        void playStepVideo(Steps stepsModel, int position);
    }

    class InstructionsHolder extends RecyclerView.ViewHolder {

        LinearLayout mStepsLayout;
        TextView mStepsIdTv;
        TextView mShortDescTv;
        ImageView mPlayButton;
        Steps mSteps;

        InstructionsHolder(View itemView) {
            super(itemView);
            mStepsLayout = itemView.findViewById(R.id.steps_layout);
            mStepsIdTv = itemView.findViewById(R.id.steps_id_tv);
            mStepsIdTv.setTypeface(EasyFonts.droidSerifBold(mContext));
            mShortDescTv = itemView.findViewById(R.id.shortDesc_tv);
            mShortDescTv.setTypeface(EasyFonts.droidSerifBold(mContext));
            mPlayButton = itemView.findViewById(R.id.forward_arrow);
        }
    }
}