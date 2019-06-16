package com.example.word.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.word.model.Word;
import com.example.word.model.WordDao;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {
    public abstract WordDao wordDao();

    // Make the WordRoomDatabase a singleton to prevent having
    // multiple instances of the database opened at the
    // same time.
    private static volatile WordRoomDatabase INSTANCE;

    static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                // another call can created an INSTANCE
                // from the if check before the last
                // synchronized guard
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), // always using the application context
                            WordRoomDatabase.class,
                            "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }

            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final WordDao mDao;

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }


        @Override
        protected Void doInBackground(Void... params) {
            if (mDao.getWordsCount() == 0) {
                mDao.deleteAll();
                Word word = new Word("Hello");
                mDao.insert(word);
                word = new Word("World");
                mDao.insert(word);
            }
            return null;
        }
    }
}
