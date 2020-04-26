package com.example.term_tracker_wgu.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.term_tracker_wgu.DataAccessObjects.AssessmentDao;
import com.example.term_tracker_wgu.DataAccessObjects.CourseDao;
import com.example.term_tracker_wgu.DataAccessObjects.TermDao;
import com.example.term_tracker_wgu.Model.Assessment;
import com.example.term_tracker_wgu.Model.Course;

import com.example.term_tracker_wgu.Model.Term;

@androidx.room.Database(entities = {Term.class, Course.class, Assessment.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();

    public static synchronized Database getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private TermDao termDao;
        private CourseDao courseDao;
        private AssessmentDao assessmentDao;

        private PopulateDbAsyncTask(Database db) {
            termDao = db.termDao();
            courseDao = db.courseDao();
            assessmentDao = db.assessmentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
