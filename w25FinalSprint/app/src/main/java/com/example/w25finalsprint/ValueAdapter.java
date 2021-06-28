package com.example.w25finalsprint;

import android.media.Image;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.BlockingDeque;

public class ValueAdapter extends RecyclerView.Adapter<ValueAdapter.ViewHolder>{

    private ArrayList<TranslatedBean> localBeans;

    private OnItemClickListener mListener;

    public TextView valueFirst, valueSecond, valueThird;
    public ImageView btnPlayOrg, btnPlayOther, btnDeleteItem;

    public interface OnItemClickListener {
        //void onItemClick(int position);
        void onPushPlayOrg(int position);
        void onPushPlayOther(int position);
        void onPushDeleteItem(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) { mListener = listener; }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);

            //valueFirst = itemView.findViewById(R.id.valueFirst);
            valueSecond = itemView.findViewById(R.id.valueSecond);
            valueThird = itemView.findViewById(R.id.valueThird);
            btnPlayOrg = itemView.findViewById(R.id.btn_listen);
            btnPlayOther = itemView.findViewById(R.id.btn_listen2);
            btnDeleteItem = itemView.findViewById(R.id.btn_remove);
            /*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onItemClick(pos);
                        }
                    }
                }
            });

             */

            btnPlayOrg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onPushPlayOrg(pos);
                        }
                    }
                }
            });
            btnPlayOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onPushPlayOther(pos);
                        }
                    }
                }
            });
            btnDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onPushDeleteItem(pos);
                        }
                    }
                }
            });
        }

        public TextView getValueSecond() {
            return valueSecond;
        }

        public TextView getValueThird() {
            return valueThird;
        }

    }

    public ValueAdapter(ArrayList<TranslatedBean> valueBeans) {
        // this runs when the adapter is called
        localBeans = valueBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dbvalue_layout, viewGroup, false);

        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ValueAdapter.ViewHolder holder, int position) {
        // this is where you can connect content and the list

        //holder.getValueFirst().setText(localBeans.get(position).getDate()); //Just store date, never show.
        holder.getValueSecond().setText(localBeans.get(position).getOriginText());
        holder.getValueThird().setText(localBeans.get(position).getTranslatedText());

    }

    @Override
    public int getItemCount() {
        return localBeans.size();
    }

}
