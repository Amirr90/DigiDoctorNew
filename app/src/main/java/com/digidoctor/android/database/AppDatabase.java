package com.digidoctor.android.database;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.digidoctor.android.model.MedicineModel;

@Database(entities = MedicineModel.MedicineDetailModel.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao getAppDao();

    static AppDatabase appDatabase;

    public static synchronized AppDatabase getDatabase(Context application) {
        if (appDatabase == null)
            appDatabase = Room.databaseBuilder(application, AppDatabase.class, "medicine_details_table")
                    .fallbackToDestructiveMigration()
                    //.addCallback(callback)
                    .allowMainThreadQueries()
                    .build();

        return appDatabase;
    }

    //call only once when the App is installed !!
    public static RoomDatabase.Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new AddTaskToDatabase(appDatabase).execute();
        }
    };

    public static class AddTaskToDatabase extends AsyncTask<Void, Void, Void> {

        public AppDatabase userDatabase;

        public AddTaskToDatabase(AppDatabase userDatabase) {
            this.userDatabase = userDatabase;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*userDatabase.getAppDao().addUser(new User("Aamirr Khan", 20));
            userDatabase.userDao().addUser(new User("Sameer", 14));
            userDatabase.userDao().addUser(new User("AamirrKing ", 36));
            userDatabase.userDao().addUser(new User("Aamirr", 25));
            userDatabase.userDao().addUser(new User("Khan", 54));*/
            return null;
        }
    }
}


