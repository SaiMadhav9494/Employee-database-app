package com.example.jagad.assignment6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jagad on 4/26/2017.
 */

public class SqlClass extends SQLiteOpenHelper {

    int DB_VERSION = 1;
    String DB_NAME = "employees.db";
    Context context;

    public SqlClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        this.DB_VERSION = version;
        this.DB_NAME = name;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE employees ("           //CREATING TABLE NAMED employees
                + "name TEXT NOT NULL, "
                + "salary INTEGER NOT NULL, "
                + "gender TEXT NOT NULL, "
                + "age INTEGER NOT NULL DEFAULT '0', "
                + "favorite_color TEXT NOT NULL, "
                + "diet TEXT  NOT NULL, "
                + "favorite_food_1 TEXT NOT NULL, "
                + "favorite_food_2 TEXT NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


