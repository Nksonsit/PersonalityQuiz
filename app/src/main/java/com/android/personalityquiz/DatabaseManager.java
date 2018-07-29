package com.android.personalityquiz;

import android.database.sqlite.SQLiteDatabase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by raghavthakkar on 10-10-2016.
 */
public class DatabaseManager {

    private static AtomicInteger openCount = new AtomicInteger();
    private static DatabaseManager instance;
    private static DBOpenHelper openHelper;
    private static SQLiteDatabase database;

    public static synchronized DatabaseManager getInstance() {
        if (null == instance) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName()
                    + " is not initialized, call initialize(..) method first.");
        }
        return instance;
    }

    public static synchronized void initialize(DBOpenHelper helper) {
        if (null == instance) {
            instance = new DatabaseManager();
        }
        openHelper = helper;
    }

    public synchronized SQLiteDatabase openDatabase() {
        if (openCount.incrementAndGet() == 1) {
            database = openHelper.getWritableDatabase();
        }
        return database;
    }

    public synchronized void closeDatabase() {
        if (openCount.decrementAndGet() == 0) {
            database.close();
        }
    }
}
