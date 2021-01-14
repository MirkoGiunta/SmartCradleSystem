package com.example.smartcradlesystem;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class RecyclerViewAdapterSounds extends RecyclerView.Adapter<RecyclerViewAdapterSounds.MyViewHolder>{
    private final List<SoundsData> soundsList;
    private ClickListenerSounds<SoundsData> clickListener;

    RecyclerViewAdapterSounds(List<SoundsData> soundsList){
        this.soundsList = soundsList;
        Log.i("RecViewAdapsounds", "RecyclerViewAdaptersounds");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recyclerview_adapter_sounds,parent,false);
        Log.i("onCreateViewHolder", "onCreateViewHolder");
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Log.i("onBindViewHolder", "Scorro la lista degli impiegati");
        final SoundsData sounds = soundsList.get(position);

        holder.id.setText("id: " + sounds.getId());

        long seconds = Long.parseLong(sounds.getDate());
        long millis = seconds * 1000;

        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm zzz", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);
        //System.out.println(formattedDate); // Tuesday,November 1,2011 12:00,AM

        holder.date.setText(formattedDate);

        holder.value.setText("value: " + sounds.getValue());
        holder.cardView.setOnClickListener((View v) -> clickListener.onItemClick(sounds));
    }

    @Override
    public int getItemCount() {
        Log.i("getItemCount", "getItemCount");
        return soundsList.size();
    }

    public void setOnItemClickListener(ClickListenerSounds<SoundsData> soundsClickListener) {
        Log.i("setOnItemClickListener", "setOnItemClickListener");
        this.clickListener = soundsClickListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView date;
        private final TextView value;
        private final CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idSound);
            date = itemView.findViewById(R.id.dateSound);
            value = itemView.findViewById(R.id.valueSound);
            cardView = itemView.findViewById(R.id.cardViewSounds);
            Log.i("MyViewHolder", "MyViewHolder");
        }
    }
}

interface ClickListenerSounds<T> {
    void onItemClick(T data);
}
