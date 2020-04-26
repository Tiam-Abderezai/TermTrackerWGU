
package com.example.term_tracker_wgu.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.term_tracker_wgu.DataAccessObjects.CourseDao;
import com.example.term_tracker_wgu.DataBase.Database;
import com.example.term_tracker_wgu.Model.Course;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CourseRepository {
    private CourseDao courseDao;
    private LiveData<List<Course>> allCourses;
    private int courseNumInTerm;

    public CourseRepository(Application application) {
        Database database = Database.getInstance(application);
        courseDao = database.courseDao();
        allCourses = courseDao.getAllCourses();
//        courseNumInTerm = courseDao.getNumCoursesInTerTerm(); // GIVES ERROR: FIX TO ENABLE DELETE-PREVENTION VALIDATION
    }

    public void insert(Course course) {
        new InsertCourseAsyncTask(courseDao).execute(course);
    }

    public void update(Course course) {
        new UpdateCourseAsyncTask(courseDao).execute(course);
    }

    public void delete(Course course) {
        new DeleteCourseAsyncTask(courseDao).execute(course);
    }

    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(courseDao).execute();
    }

    public int getNumCoursesInTerm(int termId) throws ExecutionException, InterruptedException {
//        new
//        DeleteAllCoursesAsyncTask(courseDao).execute();
        AsyncTask<Integer, Integer, Integer> x = new GetNumCoursesInTermAsyncTask(courseDao).execute(termId);
        return x.get().intValue();
    }

    public LiveData<List<Course>> getAllCourses() {
        return allCourses;
    }

//    public int getNumCoursesInTerm(int termId) {
//        return courseDao.getNumCoursesInTerm(termId);
//    }


    private static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        private InsertCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.insert(courses[0]);
            return null;
        }
    }



    private static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        private UpdateCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.update(courses[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void> {
        private CourseDao courseDao;

        private DeleteCourseAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.delete(courses[0]);
            return null;
        }
    }

    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private CourseDao courseDao;

        private DeleteAllCoursesAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            courseDao.deleteAllCourses();
            return null;
        }
    }

    private static class GetNumCoursesInTermAsyncTask extends AsyncTask<Integer, Integer, Integer> {
        private CourseDao courseDao;

        private GetNumCoursesInTermAsyncTask(CourseDao courseDao) {
            this.courseDao = courseDao;
        }

        @Override
        protected Integer doInBackground(Integer... ints) {
            return courseDao.getNumCoursesInTerm(ints[0].intValue());

        }
    }

}
