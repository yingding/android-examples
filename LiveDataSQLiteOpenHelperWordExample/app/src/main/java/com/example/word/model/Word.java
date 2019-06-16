package com.example.word.model;

import androidx.annotation.NonNull;

public class Word {
    private String mWord;

    public Word(String word) {
        this.mWord = word;
    }

    /**
     * Every field that's stored in the database needs to be either public
     * or have a "getter" method. This sample provides a getWord() method.
     * @return
     */
    public String getWord() {
        return this.mWord;
    }
}
