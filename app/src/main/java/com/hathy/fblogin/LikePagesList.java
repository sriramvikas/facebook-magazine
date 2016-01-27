package com.hathy.fblogin;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.hathy.fblogin.com.hathy.adapters.FeedItem;
import com.hathy.fblogin.com.hathy.adapters.LikeListItems;
import com.hathy.fblogin.com.hathy.adapters.MyRecyclerAdapter;
import com.srv.fblogin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LikePagesList extends AppCompatActivity {

    private static final String TAG = "RecyclerViewExample";

    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();


    private RecyclerView mRecyclerView;

    private MyRecyclerAdapter adapter;
    ArrayList<String> nameIDAry = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Allow activity to show indeterminate progressbar */
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_like_pages_list);
                /* Initialize recyclerview */
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        for (int i = 0; i < intent.getStringArrayListExtra("nameID").size(); i++) {
            nameIDAry.add(intent.getStringArrayListExtra("nameID").get(i));
        }
        /*Downloading data from below url*/
        final String url = "http://fb.x3n0n.com/php/get_posts.php?fb_token=" + token;
        new AsyncHttpTask().execute(url);

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
            String BASE_URL = params[0];
            try {
                /* forming th java.net.URL object */

                StringBuilder sb = new StringBuilder();
                for (String s : nameIDAry) {
                    sb.append("&pages[]=" + s);
                }
               // Log.e("pages",""+nameIDAry);
                String parameters = sb.toString();
                String finalURl = BASE_URL.concat(parameters);
                /* forming th java.net.URL object */
                URL url = new URL(finalURl);
                urlConnection = (HttpURLConnection) url.openConnection();
                /* for Get request */
                urlConnection.setRequestMethod("POST");
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
                Log.e(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

            setProgressBarIndeterminateVisibility(false);

            /* Download complete. Lets update UI */
            if (result == 1) {
                adapter = new MyRecyclerAdapter(LikePagesList.this, feedItemList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Log.e(TAG, "Failed to fetch data!");
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONArray pageJsonArray = new JSONArray(result);
                        /*Initialize array if null*/
            if (null == feedItemList) {
                feedItemList = new ArrayList<FeedItem>();
            }
            for (int i = 0; i < pageJsonArray.length(); i++) {
                JSONObject arryObjects = pageJsonArray.optJSONObject(i);
                //textView.setText("viki");
                FeedItem items = new FeedItem();
                //********** new jsonobject here ****************
                JSONObject getNameJSON = arryObjects.getJSONObject("from");
                items.setTitle(getNameJSON.optString("name"));
                items.setMessage(arryObjects.optString("message"));
                items.setThumbnail(arryObjects.optString("full_picture"));
                items.setLink(arryObjects.optString("link"));
                feedItemList.add(items);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
