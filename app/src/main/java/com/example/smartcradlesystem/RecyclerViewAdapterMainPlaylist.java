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

public class RecyclerViewAdapterMainPlaylist extends RecyclerView.Adapter<RecyclerViewAdapterMainPlaylist.MyViewHolder>{
    private final List<PlaylistData> playlistList;
    private ClickListenerMainPlaylist<PlaylistData> clickListener;

    RecyclerViewAdapterMainPlaylist(List<PlaylistData> playlistList){
        this.playlistList = playlistList;
        Log.i("RecViewAdapsounds", "RecyclerViewAdaptersounds");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.recyclerview_adapter_main_playlist,parent,false);
        Log.i("onCreateViewHolder", "onCreateViewHolder");
        return new MyViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Log.i("onBindViewHolder", "Scorro la lista degli impiegati");
        final PlaylistData playlist = playlistList.get(position);

        holder.id.setText("Song");
        holder.value.setText(playlist.getValue());
        holder.cardView.setOnClickListener((View v) -> clickListener.onItemClick(playlist));
    }

    @Override
    public int getItemCount() {
        Log.i("getItemCount", "getItemCount");
        return playlistList.size();
    }

    public void setOnItemClickListener(ClickListenerMainPlaylist<PlaylistData> playlistClickListener) {
        Log.i("setOnItemClickListener", "setOnItemClickListener");
        this.clickListener = playlistClickListener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView value;
        private final CardView cardView;
        public MyViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idSong);
            value = itemView.findViewById(R.id.nameSong);
            cardView = itemView.findViewById(R.id.cardViewPlaylist);
            Log.i("MyViewHolder", "MyViewHolder");
        }
    }
}

interface ClickListenerMainPlaylist<T> {
    void onItemClick(T data);
}
