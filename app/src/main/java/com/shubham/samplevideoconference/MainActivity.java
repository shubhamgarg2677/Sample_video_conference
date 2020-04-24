package com.shubham.samplevideoconference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;
import org.jitsi.meet.sdk.log.JitsiMeetLogger;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
Button share_call;
private FirebaseAuth mAuth;
GoogleSignInClient mGoogleSignInClient;
CardView signInButton;
int RC_SIGN_IN=21;
User_Model user;
TextInputLayout text_input_email,text_input_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        share_call=findViewById(R.id.share_call);
        signInButton = findViewById(R.id.sign_in_button);
        text_input_name=findViewById(R.id.text_input_name);
        text_input_email=findViewById(R.id.text_input_email);

        mAuth = FirebaseAuth.getInstance();
        user=Appcontroller.getInstance().getUser_model();

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().hide();
        }

        if(user!=null)
        {
            redirect_to_inside();
        }

        click_google_login();


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        share_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text_input_name.getEditText().length()>0 &&
                        text_input_email.getEditText().length()>0)
                {
                    User_Model user_model=new User_Model();
                    user_model.setUser_name(text_input_name.getEditText().toString());
                    user_model.setUser_email(text_input_email.getEditText().toString());
                    user_model.setUser_image("");
                    update_user_detail(user_model);
                }
                else {
                    Toast.makeText(MainActivity.this,"Something Wrong",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void redirect_to_inside()
    {

//        //userInfo.setAvatar();

//        JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        Uri uri = this.getIntent().getData();
        if(uri!=null)
        {
            JitsiMeetConferenceOptions defaultOptions
                    = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(buildURL("https://meet.jit.si"))
                    .setWelcomePageEnabled(false)
                    .build();
            Toast.makeText(this,"Uri open",Toast.LENGTH_LONG).show();
            JitsiMeetActivity.launch(this,uri.toString());
        }
        else {
            Intent intent=new Intent(this,Product_search_activity.class);
            startActivity(intent);
        }
    }


    public void click_google_login()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("google_login", "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        User_Model user_model=new User_Model();
        user_model.setUser_email(account.getEmail());
        user_model.setUser_name(account.getDisplayName());
        user_model.setUser_image(account.getPhotoUrl().toString());
        update_user_detail(user_model);
    }

    public void update_user_detail(User_Model user_model)
    {
        Log.e("update_user_detal",user_model.getUser_name());
        Appcontroller.mInstance.setUser_model(user_model);
        redirect_to_inside();
    }



    private URL buildURL(String urlStr) {
        try {
            return new URL(urlStr);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
