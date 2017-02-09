package com.it.darkluke.karaokeapp.Activity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.it.darkluke.karaokeapp.Adapter.SongsAdapter;
import com.it.darkluke.karaokeapp.Adapter.SongsLikedAdapter;
import com.it.darkluke.karaokeapp.Model.Song;
import com.it.darkluke.karaokeapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView recyclerViewLiked;
    public static SongsAdapter adapter;
    public static SongsLikedAdapter adapterLiked;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManagerLiked;
    private ArrayList<Song> listData = new ArrayList<>();
    private ArrayList<Song> listDataLiked = new ArrayList<>();
    public static String DATABASE_NAME="Arirang.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static  SQLiteDatabase database = null;
    TabHost tabHost;
    TabWidget tabWidget;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateDataBase();
        showAllSong();
        showLikedSong();
        addControls();
        addEvents();


        /***/
    }



    private void addControls() {
        layoutManager = new LinearLayoutManager(this);
        layoutManagerLiked = new LinearLayoutManager(this);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_song);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SongsAdapter(listData);
        recyclerView.setAdapter(adapter);

        if (listDataLiked.isEmpty()){
            Log.e("result","sadlah");
        }
        recyclerViewLiked = (RecyclerView) findViewById(R.id.recyclerview_song_liked);
        recyclerViewLiked.setHasFixedSize(true);
        recyclerViewLiked.setLayoutManager(layoutManagerLiked);
        adapterLiked = new SongsLikedAdapter(listDataLiked);
        recyclerViewLiked.setAdapter(adapterLiked);


        // Setting the LayoutManager.

        tabWidget = (TabWidget) findViewById(android.R.id.tabs);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tabAllSong =  tabHost.newTabSpec("tabAllSong");
        tabAllSong.setContent(R.id.tab1);
        tabAllSong.setIndicator("",getResources().getDrawable(R.drawable.microphone));
        tabHost.addTab(tabAllSong);
        TabHost.TabSpec tabLikedSong =  tabHost.newTabSpec("tabLikedSong");
        tabLikedSong.setContent(R.id.tab2);
        tabLikedSong.setIndicator("",getResources().getDrawable(R.drawable.like));
        tabHost.addTab(tabLikedSong);
    }

    private void showAllSong(){
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("ArirangSongList",null,null,null,null,null,null);
        listData.clear();
        while (cursor.moveToNext()){

            Song song = new Song();
            song.setKey(cursor.getString(0));
            song.setName(cursor.getString(1));
            song.setAuthor(cursor.getString(3));
            if (cursor.getInt(5) == 0){
                song.setLiked(false);
            }else{
                song.setLiked(true);
            }
            listData.add(song);
        }
        cursor.close();

    }
    private void showLikedSong(){
        Cursor cursor = database.query("ArirangSongList",null,"YEUTHICH=1",null,null,null,null);
        listDataLiked.clear();
        while (cursor.moveToNext()){
            Song song = new Song();
            song.setKey(cursor.getString(0));
            song.setName(cursor.getString(1));
            song.setAuthor(cursor.getString(3));
            if (cursor.getInt(5) == 0){
                song.setLiked(false);
            }else{
                song.setLiked(true);
            }
            listDataLiked.add(song);
        }
        cursor.close();

    }

    private void addEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (s.equalsIgnoreCase("tabLikedSong")){
                    Log.e("result","tabLikedSong");
                    showLikedSong();
                    adapterLiked.notifyDataSetChanged();
                }else if (s.equalsIgnoreCase("tabAllSong")){
                    Log.e("result","tabAllSong");
                    showAllSong();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
    public void CreateDataBase(){


        Log.e("result", getDatabasePath());
        File dbFile = getFileStreamPath("Arirang.db");
        File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null,null);
        SharedPreferences preferences = getSharedPreferences("CreatededDataBase",MODE_PRIVATE);

        if (preferences.getInt("time",0) == 0) {
            Log.e("result","database rỗng");
            try{

                CopyDataBaseFromAsset();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("time",1);
                editor.commit();



            }catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        else{
            Log.e("result","database có dữ liệu");
        }

    }
    private void CopyDataBaseFromAsset() {
        try{
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()){
                f.mkdir();
            }

            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;

            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams

            myOutput.flush();
            myOutput.close();
            myInput.close();
            Log.e("result",getDatabasePath());
            File dbFile = getFileStreamPath("Arirang.sqlite");
            if (dbFile.exists()){
                Log.e("result","Thành Công");
            }else{
                Log.e("result","Thôi Thua");
            }
        }catch (Exception e){
            Log.e("Exception",e.toString());
        }

    }
    public void CreateSong(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search,menu);
        MenuItem menuSearch = menu.findItem(R.id.menu_search);

        SearchView searchView = (SearchView) menuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                adapterLiked.filter(s);
                tabWidget.setEnabled(false);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tabWidget.setEnabled(true);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

}
