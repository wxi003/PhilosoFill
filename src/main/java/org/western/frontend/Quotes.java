package org.western.frontend;

import javafx.beans.property.SimpleStringProperty;

/**
 * This class is used to create a Quotes object that contains the quote, author, and source of the quote.
 *
 * @author Xi Wang
 */
public class Quotes {
    private final SimpleStringProperty quote;
    private final SimpleStringProperty author ;
    private final SimpleStringProperty source;

    /**
     * Constructor for Quotes object.
     * @param quote the quote string
     * @param author the author of the quote
     * @param source the source of the quote
     */
    public Quotes(String quote, String author, String source) {
        this.quote = new SimpleStringProperty(quote);
        this.author = new SimpleStringProperty(author);
        this.source = new SimpleStringProperty(source);
    }
    /**
     * Get the string representation of the Quotes object.
     * @return the string representation of the Quotes object
     */
    @Override
    public String toString() {
            return "\"" + getQuote() + "\" \n\n " + getAuthor() + ", " + getSource();
    }

    /**
     * Get the quote.
     * @return the quote
     */
    public String getQuote() { return quote.get(); }

    /**
     * Get the author.
     * @return the author
     */
    public String getAuthor() { return author.get(); }

    /**
     * Get the source.
     * @return the source
     */
    public String getSource() { return source.get(); }
}
