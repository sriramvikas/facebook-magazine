package com.hathy.fblogin.com.hathy.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by SriramVikas on 8/19/2015.
 */
public class RegularSansTextView extends TextView {

    public RegularSansTextView(Context context) {
        super(context);
    }

    public RegularSansTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyAttributes(context, attrs);
    }

    public RegularSansTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyAttributes(context, attrs);
    }

    private void applyAttributes(Context context, AttributeSet attrs) {

                    try {
                        Typeface font = Typeface.createFromAsset(getResources().getAssets(), "fonts/opensans/OpenSans-Regular.ttf");
                        if (font != null) {
                            this.setTypeface(font);
                        }
                    } catch (RuntimeException e) {

                    }
            }
}
