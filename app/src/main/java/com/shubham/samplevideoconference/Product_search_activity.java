package com.shubham.samplevideoconference;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;

public class Product_search_activity extends AppCompatActivity {
    Button start_call;
    User_Model user_model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search_api);

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }
        start_call=findViewById(R.id.start_call);
        user_model=Appcontroller.getInstance().getUser_model();
        start_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initialize_video();
            }
        });
    }

    public void initialize_video()
    {
        JitsiMeetUserInfo userInfo=new JitsiMeetUserInfo();
        userInfo.setDisplayName(user_model.getUser_name());
        userInfo.setEmail(user_model.getUser_email());
        // one we set earlier and this one when joining.
        JitsiMeetConferenceOptions options
                = new JitsiMeetConferenceOptions.Builder()
                .setRoom("test_123")
                .setUserInfo(userInfo)
                .setServerURL(buildURL("https://meet.jit.si"))
                .setWelcomePageEnabled(false)
                .build();
        // Launch the new activity with the given options. The launch() method takes care
        // of creating the required Intent and passing the options.
        JitsiMeetActivity.launch(this, options);
    }

    private URL buildURL(String urlStr) {
        try {
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            return null;
        }
    }

}
