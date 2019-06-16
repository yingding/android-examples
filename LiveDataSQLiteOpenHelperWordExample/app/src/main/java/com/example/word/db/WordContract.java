package com.example.word.db;

public final class WordContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private WordContract() {}

    /* Inner class that defines the table contents */
    public static class WordEntry {
        public static final String TABLE_NAME = "word_table";
        public static final String COLUMN_NAME_WORD = "word";
    }

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WordEntry.TABLE_NAME + " (" +
            WordEntry.COLUMN_NAME_WORD + " TEXT PRIMARY KEY)";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WordEntry.TABLE_NAME;
}
