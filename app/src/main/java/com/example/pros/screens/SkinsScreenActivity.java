package com.example.pros.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.pros.utils.AppMusicService;
import com.example.pros.R;
import com.example.pros.model.Skin;
import com.example.pros.model.User;
import com.example.pros.utils.SpotifyReceiver;

import java.util.ArrayList;

/**
 * 	מחלקה זו מייצגת את מסך בחירת הסקינים באפליקציה.
 */
public class SkinsScreenActivity extends AppCompatActivity implements SkinsRecyclerAdapter.ItemClickListener {
    /**
     * רשימה המכילה את כל הסקינים במשחק, המכילה עצמים מטיפוס Skin.
     */
    private ArrayList<Skin> allSkins;
    private RecyclerView recyclerView;
    private float xStart, yStart, xEnd, yEnd;
    private SpotifyReceiver spotifyBroadcastReciever;
    private IntentFilter filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skins_screen);

        allSkins = User.getInstance().getAllSkins();
        recyclerView = findViewById(R.id.recyclerView_skinsScreen);
        SkinsRecyclerAdapter recyclerAdapter = new SkinsRecyclerAdapter(this, User.getInstance().getAllSkins(), this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 	פעולה זו מבצעת את השינוי בנתוני המשתמש לאחר שנבחר סקין אחר.
     * @param position
     */
    @Override
    public void onClick(int position) {
        User.getInstance().setChosenSkinImageId(allSkins.get(position).getImage());
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!SettingsScreenActivity.musicMuted){
            startService(new Intent(getApplicationContext(), AppMusicService.class));
        }
        spotifyBroadcastReciever = new SpotifyReceiver();
        filter = new IntentFilter();
        filter.addAction("com.spotify.music.playbackstatechanged");
        filter.addAction("com.spotify.music.metadatachanged");
        filter.addAction("com.spotify.music.queuechanged");
        registerReceiver(spotifyBroadcastReciever, filter);
    }

    @Override
    protected void onPause() {//אולי יש צורך להעתיק את זה לכל מסך, צריך לבדוק את זה
        super.onPause();
        stopService(new Intent(getApplicationContext(), AppMusicService.class));
        unregisterReceiver(spotifyBroadcastReciever);
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                xStart = touchEvent.getX();
                yStart = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                xEnd = touchEvent.getX();
                yEnd = touchEvent.getY();
                if(xStart < xEnd && xEnd - xStart > 30){//Right Swipe
//                    Intent intent = new Intent(SkinsScreenActivity.this, MainScreenActivity.class);
//                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
        }
        return false;
    }
}
