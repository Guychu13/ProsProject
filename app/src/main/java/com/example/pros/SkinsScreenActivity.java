package com.example.pros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class SkinsScreenActivity extends AppCompatActivity {

    private ArrayList<Skin> allSkins;
    private int images[] = {R.drawable.choose_username_button, R.drawable.choose_username_button, R.drawable.choose_username_button,
            R.drawable.choose_username_button, R.drawable.choose_username_button, R.drawable.choose_username_button, R.drawable.choose_username_button, R.drawable.choose_username_button};
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skins_screen);



        Skin basic = new Skin("Basic", "Your Default Skin.", true, true, R.drawable.skin_basic, 0);
        Skin peace = new Skin("Peace", "Play 3 matches.", false, false, R.drawable.skin_peace, 0);
        Skin pizza = new Skin("Pizza?", "I don't know yet", false, false, R.drawable.skin_pizza, 0);
        Skin musical = new Skin("Musical", "Win 5 matches.", false, false, R.drawable.skin_musical, 0);
        Skin hamburger = new Skin("Burger", "I don't know yet", false, false, R.drawable.skin_hamburger, 0);
        Skin smile = new Skin("Smile", "Win in Under 1 minute \nand 30 seconds.", false, false, R.drawable.skin_smile, 0);
        Skin ocean = new Skin("Ocean", "I don't know yet", false, false, R.drawable.skin_ocean, 0);
        Skin telescope = new Skin("Telescope", "Win Without Taking Any Goals.", false, false, R.drawable.skin_telescope, 0);
        Skin playin = new Skin("Playin'", "Win 10 matches.", false, false, R.drawable.skin_playin, 0);
        Skin crystal = new Skin("Crystal", "I don't know yet", false, false, R.drawable.skin_crystal, 0);
        Skin master = new Skin("Master", "Win in Under 30 seconds.", false, false, R.drawable.skin_master, 0);


        allSkins = new ArrayList<>();
        allSkins.add(basic);
        allSkins.add(peace);
        allSkins.add(pizza);
        allSkins.add(musical);
        allSkins.add(hamburger);
        allSkins.add(smile);
        allSkins.add(ocean);
        allSkins.add(telescope);
        allSkins.add(playin);
        allSkins.add(crystal);
        allSkins.add(master);

        recyclerView = findViewById(R.id.recyclerView_skinsScreen);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, allSkins);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
