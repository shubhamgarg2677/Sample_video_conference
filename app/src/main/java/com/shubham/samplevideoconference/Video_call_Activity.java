package com.shubham.samplevideoconference;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.facebook.react.modules.core.PermissionListener;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class Video_call_Activity extends JitsiMeetActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initialize() {
        JitsiMeetConferenceOptions defaultOptions
                = new JitsiMeetConferenceOptions.Builder()
                .setWelcomePageEnabled(false)
                .setToken("1234")
                .setAudioMuted(false)
                .setVideoMuted(false)
                .setServerURL(buildURL("https://meet.jit.si"))
                //.setFeatureFlag("call-integration.enabled", false)
                .build();
        JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        super.initialize();
    }

    private URL buildURL(String urlStr) {
        try {
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    @Override
    public void onConferenceWillJoin(Map<String, Object> data) {
        Toast.makeText(this,data.values().toString(),Toast.LENGTH_LONG).show();
        super.onConferenceWillJoin(data);
    }

    @Override
    public void join(@Nullable String url) {
        Log.e("join_url",url);
        super.join(url);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {
        super.requestPermissions(permissions, requestCode, listener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
