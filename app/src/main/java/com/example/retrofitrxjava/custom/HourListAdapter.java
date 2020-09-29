package com.example.retrofitrxjava.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitrxjava.R;

import java.util.ArrayList;

public class HourListAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<String> hourList;

    public HourListAdapter(Context context, ArrayList<String> hourList) {
        this.context = context;
        this.hourList = hourList;
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_hour;

        public DateViewHolder(View itemView) {
            super(itemView);
            tv_hour = (TextView) itemView.findViewById(R.id.tv_hour);
        }

        public void setHours(String strHour) {

            tv_hour.setText(strHour);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_hour, parent, false);
        return new DateViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DateViewHolder dateViewHolder = (DateViewHolder) holder;
        dateViewHolder.setHours(hourList.get(position));

    }

    @Override
    public int getItemCount() {
        return hourList.size();
    }
}