package com.hathy.fblogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hathy.fblogin.database.DBHelper;
import com.srv.fblogin.R;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends Activity {

    private CallbackManager callbackManager;
    private TextView info;
    private LoginButton loginButton;
    String token = null;
    DBHelper myDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.main_activity);
        //info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        myDatabase = new DBHelper(MainActivity.this);


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
/*                info.setText("User ID:  " +
                        loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken());*/
               // token = loginResult.getAccessToken().getToken();
                myDatabase.addToken(loginResult.getAccessToken().getToken());

                Intent intent = new Intent(MainActivity.this, LikesMainPage.class);
                intent.putExtra("token", "" + loginResult.getAccessToken().getToken());
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                //info.setText("Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {

                // info.setText("Login attempt failed." + e.getLocalizedMessage());
            }
        });
        try {

            JSONArray data = myDatabase.getToken();
            for (int i = 0; i < data.length(); i++) {
                token = data.getJSONObject(i).getString("token");
            }
            if(!token.isEmpty()){
                Intent intent = new Intent(MainActivity.this, LikesMainPage.class);
                intent.putExtra("token", "" + token);
                startActivity(intent);
            }



        } catch (JSONException e) {
            Log.e("exp in get", "" + e.getLocalizedMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
