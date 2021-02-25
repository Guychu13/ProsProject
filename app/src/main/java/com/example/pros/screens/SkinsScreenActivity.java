package com.example.pros.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.example.pros.R;
import com.example.pros.model.Skin;
import com.example.pros.model.User;

import java.util.ArrayList;

public class SkinsScreenActivity extends AppCompatActivity implements SkinsRecyclerAdapter.ItemClickListener {

    private ArrayList<Skin> allSkins;
    private RecyclerView recyclerView;
    private float xStart, yStart, xEnd, yEnd;
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
