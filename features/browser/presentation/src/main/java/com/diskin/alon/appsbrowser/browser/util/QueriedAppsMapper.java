package com.diskin.alon.appsbrowser.browser.util;

import android.app.Application;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;

import com.diskin.alon.appsbrowser.browser.R;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.SearchResults;
import com.diskin.alon.appsbrowser.browser.applicationservices.model.UserAppDto;
import com.diskin.alon.appsbrowser.browser.model.QueriedApp;
import com.diskin.alon.appsbrowser.browser.model.UserApp;
import com.diskin.alon.appsbrowser.common.applicationservices.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Mapper that converts {@link UserAppDto}s emitted by their observable to {@link UserApp}s
 * of {@link QueriedApp} type.
 */
public class QueriedAppsMapper implements Mapper<Observable<SearchResults>,Observable<List<UserApp>>> {
    @NonNull
    private final Application app;

    @Inject
    public QueriedAppsMapper(@NonNull Application app) {
        this.app = app;
    }

    @Override
    public Observable<List<UserApp>> map(Observable<SearchResults> source) {
        return source.map(this::convertDtosToQueriedApps);
    }

    private List<UserApp> convertDtosToQueriedApps(@NonNull SearchResults results) {
        List<UserApp> userApps = new ArrayList<>(results.getApps().size());

        for (UserAppDto userAppDto : results.getApps()) {
            UserApp userApp = new QueriedApp(
                    userAppDto.getId(),
                    userAppDto.getName(),
                    String.format(Locale.getDefault(),"%.1f", userAppDto.getSize()) + " MB",
                    userAppDto.getIconUri(),
                    createSpannableName(userAppDto.getName(),results.getQuery()),
                    results.getQuery());

            userApps.add(userApp);
        }

        return userApps;
    }

    private SpannableString createSpannableName(@NonNull String appName, @NonNull String query) {
        SpannableString spannableString = new SpannableString(appName);
        int startIndex = appName.toLowerCase().indexOf(query.toLowerCase());
        int endIndex = startIndex + query.length();

        spannableString.setSpan(new ForegroundColorSpan(
                app.getResources().getColor(R.color.colorQuery)), startIndex, endIndex, 0);

        return spannableString;
    }
}
