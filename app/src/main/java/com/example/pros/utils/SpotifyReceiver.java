package com.example.pros.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.pros.AppMusicService;
import com.example.pros.screens.SettingsScreenActivity;

public class SpotifyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.getAction().equals(BroadcastTypes.PLAYBACK_STATE_CHANGED)) {
            boolean playing = intent.getBooleanExtra("playing",false);
            if(playing && !SettingsScreenActivity.musicMuted){
                context.stopService(new Intent(context, AppMusicService.class));
            }
            if(!playing && !SettingsScreenActivity.musicMuted){
                context.startService(new Intent(context, AppMusicService.class));
            }
        }
    }

    static final class BroadcastTypes {
        static final String SPOTIFY_PACKAGE = "com.spotify.music";
        static final String PLAYBACK_STATE_CHANGED = SPOTIFY_PACKAGE + ".playbackstatechanged";
        static final String QUEUE_CHANGED = SPOTIFY_PACKAGE + ".queuechanged";
        static final String METADATA_CHANGED = SPOTIFY_PACKAGE + ".metadatachanged";
    }
}