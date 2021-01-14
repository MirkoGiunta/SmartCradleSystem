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

public class RecyclerViewAdapterMainTempRoom extends RecyclerView.Adapter<RecyclerViewAdapterMainTempRoom.MyViewHolder>{
    private final List<TemperaturesRoomData> temperaturesRoomList;
    private ClickListenerMainTempRoom<TemperaturesRoomData> clickListener;

    RecyclerViewAdapterMainTempRoom(List<TemperaturesRoomData> temperaturesRoomList){
        this.temperaturesRoomList = temperaturesRoomList;
        Log.i("RecViewAdapsounds", "RecyclerViewAdaptersounds");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recyclerview_adapter_main_temperature_room,parent,false);
        Log.i("onCreateViewHolder", "onCreateViewHolder");
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Log.i("onBindViewHolder", "Scorro la lista degli impiegati");
        final TemperaturesRoomData temperaturesRoom = temperaturesRoomList.get(position);

        holder.id.setText("Room Temperature");

        long seconds = Long.parseLong(temperaturesRoom.getDate());
        long millis = seconds * 1000;

        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy hh:mm zzz", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);
        //System.out.println(formattedDate); // Tuesday,November 1,2011 12:00,AM

        holder.date.setText(formattedDate);

        holder.value.setText(temperaturesRoom.getValue());
        holder.cardView.setOnClickListener((View v) -> clickListener.onItemClick(temperaturesRoom));
    }

    @Override
    public int getItemCount() {
        Log.i("getItemCount", "getItemCount");
        return temperaturesRoomList.size();
    }

    public void setOnItemClickListener(ClickListenerMainTempRoom<TemperaturesRoomData> temperaturesRoomClickListener) {
        Log.i("setOnItemClickListener", "setOnItemClickListener");
        this.clickListener = temperaturesRoomClickListener;
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

interface ClickListenerMainTempRoom<T> {
    void onItemClick(T data);
}
