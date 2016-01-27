package com.hathy.fblogin.com.hathy.adapters;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hathy.fblogin.ViewImage;
import com.squareup.picasso.Picasso;
import com.srv.fblogin.R;

import android.net.Uri;
import android.view.animation.Transformation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRecyclerAdapter extends RecyclerView.Adapter<FeedListRowHolder> {


    private List<FeedItem> feedItemList;

    private Context mContext;

    public MyRecyclerAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        FeedListRowHolder mh = new FeedListRowHolder(v);

        return mh;
    }
    private static final int MAX_WIDTH = 1920;
    private static final int MAX_HEIGHT = 1080;
    @Override
    public void onBindViewHolder(FeedListRowHolder feedListRowHolder, int i) {

        int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

        final FeedItem feedItem = feedItemList.get(i);
        if (!feedItem.getThumbnail().isEmpty()) {
            Picasso.with(mContext).load(feedItem.getThumbnail())
                    .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                    .error(R.drawable.placeholder)
                    .fit()
                    .placeholder(R.drawable.placeholder)
                    .into(feedListRowHolder.thumbnail);
            if (!feedItem.getLink().isEmpty()) {
                feedListRowHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (feedItem.getLink().contains("youtu"))
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(feedItem.getLink())));
                        else {
                            Intent intent = new Intent(mContext, ViewImage.class);
                            intent.putExtra("imgname", "" + feedItem.getThumbnail());
                            mContext.startActivity(intent);
                        }
                    }

                });
            }

        }


        feedListRowHolder.title.setText(Html.fromHtml(feedItem.getTitle()));
        feedListRowHolder.message.setText(Html.fromHtml(feedItem.getMessage()));
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }


}
