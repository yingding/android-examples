package com.example.yingding.fragmentnavihostexamples.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Using MutableLiveData for setting the ActionBar title
 * https://stackoverflow.com/a/50734424
 *
 * @Author: Yingding Wang
 *
 */
public class MainViewModel extends ViewModel {

    public static MutableLiveData<String> titleLiveData = new MutableLiveData<String>();

    public MainViewModel() {

    }

    public void setTitle(String title) {
        /*
         * called from main thread
         * use the postValue() method on a background thread
         *
         */
        titleLiveData.setValue(title);
    }


}
