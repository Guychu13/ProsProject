package com.example.pros.screens;

import com.example.pros.model.User;
import com.example.pros.utils.Observer;

public class SkinsScreenPresenter implements Observer {

    private SkinsScreenActivity activity;

    public SkinsScreenPresenter(SkinsScreenActivity activity) {
        this.activity = activity;
        User.getInstance().register(this);
    }

    @Override
    public void update() {

    }
}
