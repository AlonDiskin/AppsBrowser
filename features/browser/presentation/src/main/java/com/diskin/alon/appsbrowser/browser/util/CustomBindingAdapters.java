package com.diskin.alon.appsbrowser.browser.util;

import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.diskin.alon.appsbrowser.browser.model.QueriedApp;
import com.diskin.alon.appsbrowser.browser.model.UserApp;

/**
 * Layout data binding custom adapter implementations.
 */
public abstract class CustomBindingAdapters {
    /**
     * Loads an image into imageView.
     *
     * @param view the image view to be loaded with image.
     * @param url the image url.
     */
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(Uri.parse(url))
                .into(view);
    }

    /**
     * Sets the name of existing user app into textView.If app is instance of {@link QueriedApp},
     * then app title with highlight the search query as well.
     *
     * @param tv the textView to be set with name.
     * @param app the {@link UserApp} instance containing app name
     */
    @BindingAdapter({"appName"})
    public static void setAppName(TextView tv, UserApp app) {
        if (app instanceof QueriedApp) {
            tv.setText(((QueriedApp) app).getHighlightedName());

        } else {
            tv.setText(app.getName());

        }
    }
}
