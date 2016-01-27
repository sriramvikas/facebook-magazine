package com.hathy.fblogin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.srv.fblogin.R;

public class ViewImage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        ImageView imgView = (ImageView) findViewById(R.id.thumbnail);
        Intent intent = getIntent();
        Picasso.with(getApplicationContext()).load(intent.getStringExtra("imgname"))
                .error(R.drawable.placeholder)
                .fit()
                .placeholder(R.drawable.placeholder)
                .into(imgView);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}
