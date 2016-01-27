package com.hathy.fblogin;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListView;

import com.hathy.fblogin.com.hathy.adapters.LikeListDataAdapter;
import com.hathy.fblogin.com.hathy.adapters.DividerItemDecoration;
import com.hathy.fblogin.com.hathy.adapters.LikeListItems;
import com.srv.fblogin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LikesMainPage extends AppCompatActivity {

    //private Toolbar toolbar;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<LikeListItems> LikePageList;
    private Boolean isFabOpen = false;
    private Animation fab_open, fab_close;
    private FloatingActionButton btnSelection, fab1, fab2;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes_main_page);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnSelection = (FloatingActionButton) findViewById(R.id.fabbtnShow);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        LikePageList = new ArrayList<LikeListItems>();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

/*        Log.e("clicked", "click");
        LikesMainPage.this.startActionMode(new ActionBarCallBack(
                getApplicationContext()));*/


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (dy > 0 && btnSelection.isShown())
                    btnSelection.hide();
                else if (dy < 0 && !btnSelection.isShown())
                    btnSelection.show();
            }
        });

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        /*Downloading data from below url*/
        final String url = "http://fb.x3n0n.com/php/get_pages.php?fb_token=" + token;
        new AsyncHttpTask().execute(url);
    }

    public void animateFAB() {

        if (isFabOpen) {

            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;

        } else {

            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;

        }
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;
            try {
                /* forming th java.net.URL object */
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                /* for Get request */
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();
                /* 200 represents HTTP OK */
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.e("TAG", e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            setProgressBarIndeterminateVisibility(false);
            /* Download complete. Lets update UI */
            if (result == 1) {
                mAdapter = new LikeListDataAdapter(LikePageList,LikesMainPage.this);
                mRecyclerView.setAdapter(mAdapter);
            } else {
                Log.e("TAG", "Failed to fetch data!");
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONObject likesJson = response.getJSONObject("likes");
            JSONArray dataArray = likesJson.getJSONArray("data");
            /*Initialize array if null*/
            if (null == LikePageList) {
                LikePageList = new ArrayList<LikeListItems>();
            }
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject post = dataArray.optJSONObject(i);

                LikeListItems item = new LikeListItems();
                item.setName(post.optString("name"));
                item.setNameID(post.optString("id"));
                //item.setThumbnail(post.optString("thumbnail"));
                LikePageList.add(item);
            }
            btnSelection.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String data = "";
                    List<LikeListItems> stList = ((LikeListDataAdapter) mAdapter)
                            .getLikeList();
                    ArrayList<String> nameID = new ArrayList<String>();
                    for (int i = 0; i < stList.size(); i++) {
                        LikeListItems single = stList.get(i);

                        if (single.isSelected() == true) {
                            data = data + "\n" + single.getNameID().toString();
                            nameID.add(single.getNameID());
                        }
                    }
                    if (data.length() != 0) {
                        Intent intent = new Intent(LikesMainPage.this, LikePagesList.class);
                        intent.putExtra("token", token);
                        intent.putStringArrayListExtra("nameID", nameID);
                        startActivity(intent);
                    } else {
                       // animateFAB();
                       Snackbar snackbar = Snackbar
                                .make(v, "SELECT AT LEAST ONE PAGE", Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }


                }
            });
            fab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LikeListItems item = new LikeListItems();
                    item.setCheckAll(true);
                }
            });
            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("fab2", "yes");
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
