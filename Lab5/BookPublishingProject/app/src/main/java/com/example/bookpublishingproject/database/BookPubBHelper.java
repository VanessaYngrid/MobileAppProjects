package com.example.bookpublishingproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bookpublishingproject.model.Book;
import com.example.bookpublishingproject.model.Chapter;
import com.example.bookpublishingproject.model.Publisher;

import java.util.ArrayList;

public class BookPubBHelper extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String DATABASE_NAME="publishingBase.db";

    public BookPubBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Enable foreign key constraints
        db.execSQL("PRAGMA foreign_keys = ON;");

        db.execSQL("create table " + BookPubDbSchema.PublisherTable.NAME + "(" +
                BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ID + " INTEGER PRIMARY KEY, " +
                BookPubDbSchema.PublisherTable.Cols.PUBLISHER_NAME + " TEXT, " +
                BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ADDRESS + " TEXT " + ")"
        );

        // Create BookTable with a foreign key
        db.execSQL("CREATE TABLE " + BookPubDbSchema.BookTable.NAME + " (" +
                BookPubDbSchema.BookTable.Cols.BOOK_ID + " INTEGER PRIMARY KEY, " +
                BookPubDbSchema.BookTable.Cols.BOOK_AUTHOR + " TEXT, " +
                BookPubDbSchema.BookTable.Cols.BOOK_TITLE + " TEXT, " +
                BookPubDbSchema.BookTable.Cols.BOOK_ISBN + " TEXT, " +
                BookPubDbSchema.BookTable.Cols.BOOK_TYPE + " TEXT, " +
                BookPubDbSchema.BookTable.Cols.BOOK_PRICE + " REAL, " +
                BookPubDbSchema.BookTable.Cols.PUBLISHER_ID + " INTEGER, " +
                "FOREIGN KEY(" + BookPubDbSchema.BookTable.Cols.PUBLISHER_ID + ") REFERENCES " +
                BookPubDbSchema.PublisherTable.NAME + "(" + BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ID + ")" +
                ")"
        );

        db.execSQL("CREATE TABLE " + BookPubDbSchema.ChapterTable.NAME + " (" +
                BookPubDbSchema.ChapterTable.Cols.BOOK_ID + " INTEGER, " +
                BookPubDbSchema.ChapterTable.Cols.CHAPTER_NO + " INTEGER , " +
                BookPubDbSchema.ChapterTable.Cols.CHAPTER_TITLE + " TEXT, " +
                BookPubDbSchema.ChapterTable.Cols.CHAPTER_PRICE + " REAL, " +
                "PRIMARY KEY (" + BookPubDbSchema.ChapterTable.Cols.BOOK_ID + ", " +
                BookPubDbSchema.ChapterTable.Cols.CHAPTER_NO + "), " +
                "FOREIGN KEY(" + BookPubDbSchema.ChapterTable.Cols.BOOK_ID + ") REFERENCES " +
                BookPubDbSchema.BookTable.NAME + "(" + BookPubDbSchema.BookTable.Cols.BOOK_ID + ")" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private ContentValues getContentValues(Publisher publisher) {
        ContentValues values = new ContentValues();

        values.put(BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ID, publisher.getP_id());
        values.put(BookPubDbSchema.PublisherTable.Cols.PUBLISHER_NAME, publisher.getP_name());
        values.put(BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ADDRESS, publisher.getP_address());

        return values;
    }

    public void addNewPublisher(Publisher publisher) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues
        ContentValues values = getContentValues(publisher);

        // Insert values into the table
        db.insert(BookPubDbSchema.PublisherTable.NAME, null, values);

        // Close the database
        db.close();
     }

    public ArrayList<Publisher> readPublishers() {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the database
        Cursor cursorPublisher = db.rawQuery("SELECT * FROM " + BookPubDbSchema.PublisherTable.NAME, null);

        // Create an ArrayList
        ArrayList<Publisher> publisherArrayList = new ArrayList<>();

        // Move the cursor to the first position
        if (cursorPublisher.moveToFirst()) {
            do {
                publisherArrayList.add(new Publisher(
                        cursorPublisher.getInt(cursorPublisher.getColumnIndexOrThrow(BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ID)),
                        cursorPublisher.getString(cursorPublisher.getColumnIndexOrThrow(BookPubDbSchema.PublisherTable.Cols.PUBLISHER_NAME)),
                        cursorPublisher.getString(cursorPublisher.getColumnIndexOrThrow(BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ADDRESS))
                ));
            } while (cursorPublisher.moveToNext());
        }

        // Close the cursor and return the list
        cursorPublisher.close();
        return publisherArrayList;
    }


    public void updatePublisher(Publisher publisher) {
        // Get the publisher ID
        String publisherIdString = String.valueOf(publisher.getP_id());

        // Create ContentValues
        ContentValues values = getContentValues(publisher);

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Update the table
        db.update(BookPubDbSchema.PublisherTable.NAME, values,
                BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ID + "=?", new String[]{publisherIdString});

        // Close the database
        db.close();
    }

    public void deletePublisher(Publisher publisher) {
        // Get the publisher ID
        String publisherIdString = String.valueOf(publisher.getP_id());

        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete the table
        db.delete(BookPubDbSchema.PublisherTable.NAME,
                BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ID + "=?", new String[]{publisherIdString});

        // Close the database
        db.close();
    }

    public Publisher searchPublisher(Publisher publisher) {
        // Get a readable database instance
        SQLiteDatabase db = this.getReadableDatabase();

        // Get the publisher ID from the Publisher object
        String publisherIdString = String.valueOf(publisher.getP_id());

        // Query the database for a Publisher with the given publisher name
        Cursor cursorPublisher = db.query(
                BookPubDbSchema.PublisherTable.NAME, null,
                BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ID + "=?",
                new String[]{publisherIdString}, null, null, null
        );

        // Check if a result was found
        if (cursorPublisher != null && cursorPublisher.moveToFirst()) {
            // Create a new Publisher object from the cursor data
            Publisher foundPublisher = new Publisher(
                    cursorPublisher.getInt(cursorPublisher.getColumnIndexOrThrow(BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ID)),
                    cursorPublisher.getString(cursorPublisher.getColumnIndexOrThrow(BookPubDbSchema.PublisherTable.Cols.PUBLISHER_NAME)),
                    cursorPublisher.getString(cursorPublisher.getColumnIndexOrThrow(BookPubDbSchema.PublisherTable.Cols.PUBLISHER_ADDRESS))
            );

            // Close the cursor
            cursorPublisher.close();
            // Return the found publisher
            return foundPublisher;
        } else {
            return null;
        }
    }

    //BOOK
    private ContentValues getContentValues(Book book) {
        ContentValues values = new ContentValues();

        values.put(BookPubDbSchema.BookTable.Cols.BOOK_ID, book.getB_id());
        values.put(BookPubDbSchema.BookTable.Cols.BOOK_AUTHOR, book.getB_author());
        values.put(BookPubDbSchema.BookTable.Cols.BOOK_TITLE, book.getB_title());
        values.put(BookPubDbSchema.BookTable.Cols.BOOK_ISBN, book.getB_isbn());
        values.put(BookPubDbSchema.BookTable.Cols.BOOK_TYPE, book.getB_type());
        values.put(BookPubDbSchema.BookTable.Cols.BOOK_PRICE, book.getB_price());
        values.put(BookPubDbSchema.BookTable.Cols.PUBLISHER_ID, book.getP_id());

        return values;
    }

    public void addNewBook(Book book) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Create ContentValues
        ContentValues values = getContentValues(book);

        // Insert values into the table
        db.insert(BookPubDbSchema.BookTable.NAME, null, values);

        // Close the database
        db.close();
    }

    public ArrayList<Book> readBooks() {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the database
        Cursor cursorBook = db.rawQuery("SELECT * FROM " + BookPubDbSchema.BookTable.NAME, null);

        // Create an ArrayList
        ArrayList<Book> bookArrayList = new ArrayList<>();

        // Move the cursor to the first position
        if (cursorBook.moveToFirst()) {
            do {
                bookArrayList.add(new Book(
                        cursorBook.getInt(cursorBook.getColumnIndexOrThrow(BookPubDbSchema.BookTable.Cols.BOOK_ID)),
                        cursorBook.getString(cursorBook.getColumnIndexOrThrow(BookPubDbSchema.BookTable.Cols.BOOK_AUTHOR)),
                        cursorBook.getString(cursorBook.getColumnIndexOrThrow(BookPubDbSchema.BookTable.Cols.BOOK_TITLE)),
                        cursorBook.getString(cursorBook.getColumnIndexOrThrow(BookPubDbSchema.BookTable.Cols.BOOK_ISBN)),
                        cursorBook.getString(cursorBook.getColumnIndexOrThrow(BookPubDbSchema.BookTable.Cols.BOOK_TYPE)),
                        cursorBook.getDouble(cursorBook.getColumnIndexOrThrow(BookPubDbSchema.BookTable.Cols.BOOK_PRICE)),
                        cursorBook.getInt(cursorBook.getColumnIndexOrThrow(BookPubDbSchema.BookTable.Cols.PUBLISHER_ID))
                ));
            } while (cursorBook.moveToNext());
        }

        // Close the cursor and return the list
        cursorBook.close();
        return bookArrayList;
    }

    //CHAPTER
    private ContentValues getContentValues(Chapter chapter) {
        ContentValues values = new ContentValues();

        values.put(BookPubDbSchema.ChapterTable.Cols.BOOK_ID, chapter.getB_id());
        values.put(BookPubDbSchema.ChapterTable.Cols.CHAPTER_NO, chapter.getC_no());
        values.put(BookPubDbSchema.ChapterTable.Cols.CHAPTER_TITLE, chapter.getC_title());
        values.put(BookPubDbSchema.ChapterTable.Cols.CHAPTER_PRICE, chapter.getC_price());

        return values;
    }

    public void addNewChapter(Chapter chapter) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        // Create ContentValues
        ContentValues values = getContentValues(chapter);

        // Insert values into the table
        db.insert(BookPubDbSchema.ChapterTable.NAME, null, values);

        // Close the database
        db.close();
    }

    public ArrayList<Chapter> readChapters() {
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the database
        Cursor cursorChapter = db.rawQuery("SELECT * FROM " + BookPubDbSchema.ChapterTable.NAME, null);

        // Create an ArrayList
        ArrayList<Chapter> chapterArrayList = new ArrayList<>();

        // Move the cursor to the first position
        if (cursorChapter.moveToFirst()) {
            do {
                chapterArrayList.add(new Chapter(
                        cursorChapter.getInt(cursorChapter.getColumnIndexOrThrow(BookPubDbSchema.ChapterTable.Cols.BOOK_ID)),
                        cursorChapter.getInt(cursorChapter.getColumnIndexOrThrow(BookPubDbSchema.ChapterTable.Cols.CHAPTER_NO)),
                        cursorChapter.getString(cursorChapter.getColumnIndexOrThrow(BookPubDbSchema.ChapterTable.Cols.CHAPTER_TITLE)),
                        cursorChapter.getDouble(cursorChapter.getColumnIndexOrThrow(BookPubDbSchema.ChapterTable.Cols.CHAPTER_PRICE))
                ));
            } while (cursorChapter.moveToNext());
        }

        // Close the cursor and return the list
        cursorChapter.close();
        return chapterArrayList;
    }

}
