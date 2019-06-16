package com.example.word.db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.word.db.WordRepository;
import com.example.word.model.Word;

import java.util.List;

/**
 * The ViewModel's role is to provide data to the UI and survive configuration changes.
 * A ViewModel acts as a communication center between the Repository and the UI.
 * A ViewModel can also be used to share data between fragments.
 *
 * A ViewModel holds the app's UI data in a lifecycle-conscious way
 * that survives configuration changes. Separating your app's UI data
 * from your Activity and Fragment classes lets you better follow
 * the single responsibility principle: Your activities and fragments
 * are responsible for drawing data to the screen, while your ViewModel
 * can take care of holding and processing all the data needed for the UI.
 *
 */
public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;
    public WordViewModel(@NonNull Application application) {
        super(application);
        // WordRepository and getAllWords are all package private
        // the access of the method are restricted by the package
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }

    /**
     * Warning: Never pass context into ViewModel instances.
     * Do not store Activity, Fragment, or View instances or their Context in the ViewModel.
     *
     * For example, an Activity can be destroyed and created many times during the lifecycle
     * of a ViewModel as the device is rotated. If you store a reference to the Activity
     * in the ViewModel, you end up with references that point to the destroyed Activity.
     * This is a memory leak.
     *
     * If you need the application context, use AndroidViewModel, as shown in this example.
     *
     * Important: ViewModel is not a replacement for the onSaveInstanceState() method,
     * because the ViewModel does not survive a process shutdown. Learn more about
     * process shutdown from
     * https://medium.com/androiddevelopers/viewmodels-persistence-onsaveinstancestate-restoring-ui-state-and-loaders-fc7cc4a6c090
     */
}
