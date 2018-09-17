package com.example.dglozano.escale.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dglozano.escale.MainActivity;
import com.example.dglozano.escale.R;
import com.example.dglozano.escale.model.Measurement;

import java.util.List;

public class MeasurementListAdapter extends RecyclerView.Adapter<MeasurementListAdapter.MeasurementViewHolder> {

    private List<Measurement> mMeasurements;
    private Context mContext;

    public static class MeasurementViewHolder extends RecyclerView.ViewHolder {

        public TextView mMeasurementTypeTextView;
        public TextView mMeasurementValueTextView;
        public ImageView mIconImageView;

        public MeasurementViewHolder(View v) {
            super(v);
            mMeasurementTypeTextView = v.findViewById(R.id.measurement_item_text);
            mMeasurementValueTextView = v.findViewById(R.id.measurement_item_number);
            mIconImageView = v.findViewById(R.id.measurement_item_icon);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MeasurementListAdapter(List<Measurement> measurementList, Context context) {
        mMeasurements = measurementList;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MeasurementViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.measurement_row_item, parent, false);

        return new MeasurementViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MeasurementViewHolder holder, int position) {
        final Measurement measurement = mMeasurements.get(position);
        holder.mMeasurementTypeTextView.setText(measurement.getType());
        //TODO
        holder.mMeasurementValueTextView.setText(measurement.getValue() + measurement.getUnit());
        //FIXME
        switch (measurement.getIconResource()){
            case 1:
                holder.mIconImageView.setImageDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.home_ic_scale_colored ));
                break;
            case 2:
                holder.mIconImageView.setImageDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.home_ic_drop_colored ));
                break;
            case 3:
                holder.mIconImageView.setImageDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.home_ic_pizza_slice_colored ));
                break;
            case 4:
                holder.mIconImageView.setImageDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.home_ic_bone_colored ));
                break;
            case 5:
                holder.mIconImageView.setImageDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.home_ic_bmi_colored ));
                break;
            case 6:
                holder.mIconImageView.setImageDrawable(
                        ContextCompat.getDrawable(mContext, R.drawable.home_ic_muscle_colored ));
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMeasurements.size();
    }
}