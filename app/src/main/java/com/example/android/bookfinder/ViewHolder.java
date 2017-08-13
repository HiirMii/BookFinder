package com.example.android.bookfinder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by HiirMii on 2017-06-24.
 */

public class ViewHolder {

    TextView titleTextView;
    TextView authorTextView;
    TextView descriptionTextView;
    ImageView imageView;

    public ViewHolder(@NonNull View view) {
        this.titleTextView = (TextView) view.findViewById(R.id.book_title);
        this.authorTextView = (TextView) view.findViewById(R.id.book_author);
        this.descriptionTextView = (TextView) view.findViewById(R.id.book_description);
        this.imageView = (ImageView) view.findViewById(R.id.book_image);
    }
}
