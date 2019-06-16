package com.example.word.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.word.model.Word;
import com.example.word.model.WordDao;

import java.util.List;

/**
 * A Repository is a class that abstracts access to multiple data sources.
 * The Repository is not part of the Architecture Components libraries,
 * but is a suggested best practice for code separation and architecture.
 *
 * A Repository class handles data operations. It provides a clean API
 * to the rest of the app for app data.
 *
 * A Repository manages query threads and allows you to use multiple backends.
 * In the most common example, the Repository implements the logic
 * for deciding whether to fetch data from a network
 * or use results cached in a local database.
 */
public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    /**
     * Add a wrapper for getAllWords(). Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     * @return
     */
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    /**
     * Add a wrapper for the insert() method.
     * You must call this on a non-UI thread
     * or your app will crash. Room ensures that you don't
     * do any long-running operations on the main thread,
     * blocking the UI.
     * @param word
     */
    public void insert (Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    /**
     * this is a synchronized call
     * @return
     */
    int getWordsCount() {
        return mWordDao.getWordsCount();
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        /**
         * execute(param) call hands over a param array and the
         * param word is the first element int he param array for
         * doInBackground([word])
         *
         * @param words
         * @return
         */
        @Override
        protected Void doInBackground(final Word... words) {
            mAsyncTaskDao.insert(words[0]);
            return null;
        }
    }
}
