package com.example.android.bookfinder;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by HiirMii on 2017-06-24.
 */

/**
 * Loads a list of books by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class BookLoader extends AsyncTaskLoader<List<Book>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = BookLoader.class.getName();

    /**
     * Query URL
     */
    private String url;

    /**
     * Constructs a new {@link BookLoader}.
     *
     * @param context    of the activity
     * @param currentUrl to load data from
     */
    public BookLoader(Context context, String currentUrl) {
        super(context);
        url = currentUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        if (url == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        List<Book> books = Utils.fetchBookData(url);
        return books;
    }
}
