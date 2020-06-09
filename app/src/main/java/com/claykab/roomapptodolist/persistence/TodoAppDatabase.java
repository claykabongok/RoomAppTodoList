package com.claykab.roomapptodolist.persistence;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

//AppDatabase
@Database(entities  ={Todo.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TodoAppDatabase extends RoomDatabase {
    public  abstract TodoDao todoDao();
    /**
     *  singleton pattern
     *  to run single instance of the db in JVM
     *
     */



    private static volatile  TodoAppDatabase todoAppDatabaseInstance;

    public static TodoAppDatabase  getDatabase(final Context context){
        if(todoAppDatabaseInstance == null){
            synchronized (TodoAppDatabase.class){
                if(todoAppDatabaseInstance==null){
                    todoAppDatabaseInstance= Room.databaseBuilder(context.getApplicationContext(),
                            TodoAppDatabase.class, "Todo_app_database")
                            .build();

                }
            }
        }
        return todoAppDatabaseInstance;
    }

}
