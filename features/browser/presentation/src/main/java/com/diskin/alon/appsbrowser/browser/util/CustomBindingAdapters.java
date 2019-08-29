package com.diskin.alon.appsbrowser.browser.util;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.diskin.alon.appsbrowser.browser.model.QueriedApp;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.squareup.picasso.Picasso;

public abstract class CustomBindingAdapters {
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.get().load(url).into(view);
    }

    @BindingAdapter({"appName"})
    public static void setAppName(TextView tv, UserApp app) {
        if (app instanceof QueriedApp) {
            tv.setText(((QueriedApp) app).getHighlightedName());

        } else {
            tv.setText(app.getName());

        }
    }
}
