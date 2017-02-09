package com.it.darkluke.karaokeapp.Adapter;

import android.content.ContentValues;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.it.darkluke.karaokeapp.Model.Song;
import com.it.darkluke.karaokeapp.R;

import java.util.ArrayList;

import static com.it.darkluke.karaokeapp.Activity.MainActivity.database;

/**
 * Created by DELL on 09/02/2017.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongRecyclerViewHolder>  {

    ArrayList<Song> listData = new ArrayList<>();
    ArrayList<Song> listDataCopy = new ArrayList<>();





    public SongsAdapter(ArrayList<Song> listData) {
        this.listData = listData;
        listDataCopy.addAll(listData);
    }


    @Override
    public SongRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_song, parent, false);
        return new SongRecyclerViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(SongRecyclerViewHolder holder, int position) {
        holder.txtKey.setText(String.valueOf(listData.get(position).getKey()));
        holder.txtSongName.setText(listData.get(position).getName());
        holder.txtAuthor.setText(listData.get(position).getAuthor());
        if (listData.get(position).isLiked()){
            holder.imgBtnLike.setImageResource(R.drawable.like);
        }else{
            holder.imgBtnLike.setImageResource(R.drawable.dislike);
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
    public void updateList(ArrayList<Song> data) {
        listData = data;
        notifyDataSetChanged();
    }
    public void addItem(int position, Song data) {
        listData.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        listData.remove(position);
        notifyItemRemoved(position);
    }
    public void filter(String text) {

        if (!listDataCopy.isEmpty()) {
            listData.clear();

            if (text.isEmpty()) {
                listData.addAll(listDataCopy);
            } else {
                text = text.toLowerCase();
                for (Song item : listDataCopy) {
                    if (item.getName().toLowerCase().contains(text)) {
                        listData.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    public class SongRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView txtKey;
        TextView txtSongName;
        TextView txtAuthor;
        ImageButton imgBtnLike;
        public SongRecyclerViewHolder(View itemView) {
            super(itemView);
            txtKey      = (TextView) itemView.findViewById(R.id.txt_key);
            txtSongName = (TextView) itemView.findViewById(R.id.txt_song_name);
            txtAuthor   = (TextView) itemView.findViewById(R.id.txt_song_author);
            imgBtnLike  = (ImageButton) itemView.findViewById(R.id.imgbtn_like);
            imgBtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("result",String.valueOf(getAdapterPosition()));
                    ContentValues row = new ContentValues();
                    if (listData.get(getAdapterPosition()).isLiked()){
                        Log.e("result","like rồi");
                        row.put("YEUTHICH",0);
                        imgBtnLike.setImageResource(R.drawable.dislike);
                        listData.get(getAdapterPosition()).setLiked(false);
                    }else  if (!listData.get(getAdapterPosition()).isLiked()){
                        Log.e("result","chưa like");
                        row.put("YEUTHICH",1);
                        imgBtnLike.setImageResource(R.drawable.like);
                        listData.get(getAdapterPosition()).setLiked(true);
                    }
                    database.update("ArirangSongList", row ,"MABH=?",new String[]{listData.get(getAdapterPosition()).getKey()});
                    notifyDataSetChanged();
                }
            });
        }
    }
}
