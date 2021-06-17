package com.example.w24weatherappservice;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.ViewHolder> {

    ArrayList<WeatherBean> localBeans;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView valueDate, valueLocation, valueTemp, valueDesc;

        public ViewHolder(View itemView) {
            super(itemView);

            valueDate = itemView.findViewById(R.id.valueDate);
            valueLocation = itemView.findViewById(R.id.valueLocation);
            valueTemp = itemView.findViewById(R.id.valueTemp);
            valueDesc = itemView.findViewById(R.id.valueDesc);
        }

        public TextView getValueDate() {
            return valueDate;
        }

        public TextView getValueLocation() {
            return valueLocation;
        }

        public TextView getValueTemp() {
            return valueTemp;
        }

        public TextView getValueDesc() {
            return valueDesc;
        }
    }

    public ValueAdapter(ArrayList<WeatherBean> valueBeans) {
        // this runs when the adaper is called
        Log.d("ADA", "ValueAdapter: ");

        localBeans = valueBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dbvalue_layout, viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ValueAdapter.ViewHolder holder, int position) {
        // this is where you can connect content and the list

        //Log.d("ADA", "onBindViewHolder: " + localBeans.get(position).getDesc());

        holder.getValueDate().setText(localBeans.get(position).getDate());
        holder.getValueLocation().setText(localBeans.get(position).getLocation());
        holder.getValueTemp().setText(localBeans.get(position).getTemp());
        holder.getValueDesc().setText(localBeans.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        //return testTitle.length;
        return localBeans.size();
    }


}

