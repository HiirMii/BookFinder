package com.example.android.bookfinder;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity
        implements LoaderCallbacks<List<Book>> {

    /**
     * Tag for log messages
     */
    public static final String LOG_TAG = BookActivity.class.getName();

    /**
     * URL for book data from Google Books API
     */
    private static final String BOOK_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes?maxResults=10&orderBy=newest&q=";

    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    /**
     * Adapter for the list of books
     */
    private BookAdapter adapter;

    /**
     * SearchView that takes the query
     */
    private SearchView searchView;

    /**
     * List of books
     */
    private ListView booksListView;

    /**
     * Value for search query
     */
    private String searchQuery;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView emptyStateTextView;

    /**
     * ProgressBar that is displayed when the data is loaded
     */
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        // Find a reference to the {@link SearchView} in the layout
        searchView = (SearchView) findViewById(R.id.search_view);

        // Find a reference to the {@link ListView} in the layout
        booksListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of books as input
        adapter = new BookAdapter(this, new ArrayList<Book>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        booksListView.setAdapter(adapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Find the current book that was clicked on
                Book currentBook = adapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentBook.getBookURL());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Find the reference to the progress bar in a layout
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        // Find the reference to the empty text view in a layout and set empty view
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        booksListView.setEmptyView(emptyStateTextView);

        if (isConnected()) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyStateTextView.setText(R.string.no_internet);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (isConnected()) {
                    booksListView.setVisibility(View.INVISIBLE);
                    emptyStateTextView.setVisibility(View.GONE);
                    loadingIndicator.setVisibility(View.VISIBLE);
                    searchQuery = searchView.getQuery().toString();
                    searchQuery = searchQuery.replace(" ", "+");
                    Log.v(LOG_TAG, searchQuery);
                    getLoaderManager().restartLoader(BOOK_LOADER_ID, null, BookActivity.this);
                    searchView.clearFocus();
                } else {
                    booksListView.setVisibility(View.INVISIBLE);
                    loadingIndicator.setVisibility(View.GONE);
                    emptyStateTextView.setVisibility(View.VISIBLE);
                    emptyStateTextView.setText(R.string.no_internet);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    // Helper method to check network connection
    public boolean isConnected() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // Return network connection info as boolean value
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        String requestUrl = "";
        if (searchQuery != null && searchQuery != "") {
            requestUrl = BOOK_REQUEST_URL + searchQuery;
        } else {
            String defaultQuery = "android";
            requestUrl = BOOK_REQUEST_URL + defaultQuery;
        }
        return new BookLoader(this, requestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        emptyStateTextView.setText(R.string.no_books);
        loadingIndicator.setVisibility(View.GONE);
        adapter.clear();

        if (books != null && !books.isEmpty()) {
            adapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        adapter.clear();
    }
}
