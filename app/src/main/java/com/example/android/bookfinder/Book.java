package com.example.android.bookfinder;

/**
 * An {@link Book} object contains information related to a single book.
 */

public class Book {

    /** Title of the book */
    private String title;

    /** Author of the book */
    private String author;

    /** Description of the book */
    private String description;

    /** Url of the image of the book */
    private String imageUrl;

    /** Url of the book */
    private String bookURL;

    /**
     * Constructs a new {@link Book} object.
     *
     * @param currentTitle is the title of the book
     * @param currentAuthor is the author of the book
     * @param currentDescription is the short book description
     * @param currentImageUrl is the URL to the current book image
     * @param currentBookUrl is the URL to the current book website
     */
    public Book(String currentTitle, String currentAuthor, String currentDescription,
                String currentImageUrl, String currentBookUrl) {
        title = currentTitle;
        author = currentAuthor;
        description = currentDescription;
        imageUrl = currentImageUrl;
        bookURL = currentBookUrl;
    }

    /**
     * Returns the title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of the book.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the description of the book.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the url of the image for the current book.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Returns the url for the current book.
     */
    public String getBookURL() {
        return bookURL;
    }
}
