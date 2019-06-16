package com.example.word.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.word.model.Word;

import java.util.ArrayList;
import java.util.List;

import static com.example.word.db.WordContract.SQL_CREATE_ENTRIES;
import static com.example.word.db.WordContract.SQL_DELETE_ENTRIES;

public class WordDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "word_database.db";

    // Make the WordRoomDatabase a singleton to prevent having
    // multiple instances of the database opened at the
    // same time.
    private static volatile WordDbHelper INSTANCE;

    private WordDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static WordDbHelper getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (WordDbHelper.class){
                // another call can created an INSTANCE
                // from the if check before the last
                // synchronized guard
                if(INSTANCE==null){
                    // Create database here
                    INSTANCE=new WordDbHelper(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insert(Word word) {
        // Gets the data repository in write mode
        SQLiteDatabase db = INSTANCE.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(WordContract.WordEntry.COLUMN_NAME_WORD, word.getWord());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(WordContract.WordEntry.TABLE_NAME, null, values);

        // use open and close pattern
        db.close();
    }

    public List<Word> getAllWords() {
        // Gets the data repository in readonly mode
        SQLiteDatabase db = INSTANCE.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                WordContract.WordEntry.COLUMN_NAME_WORD
        };

        String sortOrder = WordContract.WordEntry.COLUMN_NAME_WORD + " ASC";

        Cursor cursor = db.query(
                WordContract.WordEntry.TABLE_NAME, // The table to query
                projection,                        // The array of columns to return (pass null to get all)
                null,                     // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                      // don't group the rows
                null,                       // don't filter by row groups
                sortOrder                           // The sort order
        );

        List<Word> words = new ArrayList<>();
        while(cursor.moveToNext()) {
            words.add(new Word(
                    cursor.getString(
                            cursor.getColumnIndex(WordContract.WordEntry.COLUMN_NAME_WORD)
                    )
            ));
        }
        cursor.close();
        db.close();
        return words;
    }

}
