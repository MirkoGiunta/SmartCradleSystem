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

public class RecyclerViewAdapterPlaylist extends RecyclerView.Adapter<RecyclerViewAdapterPlaylist.MyViewHolder>{
    private final List<PlaylistData> playlistList;
    private ClickListenerPlaylist<PlaylistData> clickListener;

    RecyclerViewAdapterPlaylist(List<PlaylistData> playlistList){
        this.playlistList = playlistList;
        Log.i("RecViewAdapPlaylist", "RecyclerViewAdapterPlaylist");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recyclerview_adapter_playlist,parent,false);
        Log.i("onCreateViewHolder", "onCreateViewHolder");
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Log.i("onBindViewHolder", "Scorro la lista degli impiegati");
        final PlaylistData playlist = playlistList.get(position);

        //holder.id.setText("id: " + playlist.getId());
        holder.value.setText("Title:   " + playlist.getValue());
        //holder.date.setText(playlist.getDate());

        holder.cardView.setOnClickListener((View v) -> clickListener.onItemClick(playlist));
    }

    @Override
    public int getItemCount() {
        Log.i("getItemCount", "getItemCount");
        return playlistList.size();
    }

    public void setOnItemClickListener(ClickListenerPlaylist<PlaylistData> playlistClickListener) {
        Log.i("setOnItemClickListener", "setOnItemClickListener");
        this.clickListener = playlistClickListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //private final TextView id;
        private final TextView value;
        //private final TextView date;
        private final CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            //id = itemView.findViewById(R.id.idSong);
            //date = itemView.findViewById(R.id.dateSong);
            value = itemView.findViewById(R.id.valueSong);
            cardView = itemView.findViewById(R.id.cardViewPlaylist);
            Log.i("MyViewHolder", "MyViewHolder");
        }
    }
}

interface ClickListenerPlaylist<T> {
    void onItemClick(T data);
}
