package com.example.android.bookfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by HiirMii on 2017-06-24.
 */

/**
 * An {@link BookAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link Book} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * Constructs a new {@link BookAdapter}.
     *
     * @param context of the app
     * @param books   is the list of earthquakes, which is the data source of the adapter
     */
    public BookAdapter(Context context, List<Book> books) {
        super(context, 0, books);
    }

    /**
     * Returns a list item view that displays information about the book at the given position
     * in the list of books.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        /**
         * ViewHolder for the Book.
         */
        ViewHolder viewHolder;

        // Check if there is an existing convertView that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout using viewHolder.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Find the book at the given position in the list of books
        Book currentBook = getItem(position);

        // Display the title of the current book in that TextView using viewHolder
        viewHolder.titleTextView.setText(currentBook.getTitle());
        // Display the author of the current book in that TextView using viewHolder
        viewHolder.authorTextView.setText(currentBook.getAuthor());
        // Display the description of the current book in that TextView using viewHolder
        viewHolder.descriptionTextView.setText(currentBook.getDescription());

        // Display the image of the current book in that TextView using viewHolder and Picasso class
        Picasso.with(getContext()).setLoggingEnabled(true);

        Picasso.with(getContext())
                .load(currentBook.getImageUrl())
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_error)
                .into(viewHolder.imageView);

        return convertView;
    }
}
