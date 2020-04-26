//CoursesActivity

package com.example.term_tracker_wgu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.term_tracker_wgu.Adapters.CourseAdapter;
import com.example.term_tracker_wgu.Model.Course;
//import com.example.term_tracker_wgu.ViewModel.AddCourseActivity;
import com.example.term_tracker_wgu.ViewModel.AddCourseActivity;
import com.example.term_tracker_wgu.ViewModel.DetailCourseActivity;
import com.example.term_tracker_wgu.ViewModel.CourseViewModel;
import com.example.term_tracker_wgu.ViewModel.DetailTermActivity;
import com.example.term_tracker_wgu.ViewModel.EditCourseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class CoursesActivity extends AppCompatActivity {

//    private Button mainButton;

    public static final int ADD_COURSE_REQUEST = 1;
    public static final int DETAIL_COURSE_REQUEST = 2;
    public static final int EDIT_COURSE_REQUEST = 3;

    public CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);







        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courses) {
                adapter.setCourses(courses);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(CoursesActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(CoursesActivity.this, DetailCourseActivity.class);
                int id = course.getCourseId();
                String strId = Integer.toString(id);
//                intent.putExtra(DetailCourseActivity.EXTRA_ID, course.getCourseId());
                intent.putExtra(DetailCourseActivity.EXTRA_COURSE_ID, strId);
                intent.putExtra(DetailCourseActivity.EXTRA_TITLE, course.getCourseTitle());
                intent.putExtra(DetailCourseActivity.EXTRA_DATE_START, course.getCourseDateStart());
                intent.putExtra(DetailCourseActivity.EXTRA_DATE_END, course.getCourseDateEnd());
                intent.putExtra(DetailCourseActivity.EXTRA_NOTE, course.getCourseNote());
                intent.putExtra(DetailCourseActivity.EXTRA_STATUS_COURSE, course.getCourseStatus());
                intent.putExtra(DetailCourseActivity.EXTRA_MENTOR, course.getCourseMentor());
                intent.putExtra(DetailCourseActivity.EXTRA_MENTOR_PHONE, course.getCourseMentorEmail());
                intent.putExtra(DetailCourseActivity.EXTRA_MENTOR_EMAIL, course.getCourseMentorPhone());

                startActivityForResult(intent, DETAIL_COURSE_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {
            String termId = data.getStringExtra(DetailTermActivity.EXTRA_ID);
            String title = data.getStringExtra(DetailCourseActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(DetailCourseActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(DetailCourseActivity.EXTRA_DATE_END);
            String notes = data.getStringExtra(DetailCourseActivity.EXTRA_NOTE);
            String status_course = data.getStringExtra(DetailCourseActivity.EXTRA_STATUS_COURSE);
            String mentor = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR);
            String mentor_phone = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR_PHONE);
            String mentor_email = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR_EMAIL);
            int termIdInt = Integer.parseInt(termId);

            Course course = new Course(termIdInt, title, date_start, date_end, notes, status_course, mentor, mentor_phone, mentor_email);
            courseViewModel.insert(course);

            Toast.makeText(this, "Course saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == DETAIL_COURSE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(DetailCourseActivity.EXTRA_COURSE_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String termId = data.getStringExtra(DetailTermActivity.EXTRA_ID);
            String title = data.getStringExtra(DetailCourseActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(DetailCourseActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(DetailCourseActivity.EXTRA_DATE_END);
            String notes = data.getStringExtra(DetailCourseActivity.EXTRA_NOTE);
            String status_course = data.getStringExtra(DetailCourseActivity.EXTRA_STATUS_COURSE);
            String mentor = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR);
            String mentor_phone = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR_PHONE);
            String mentor_email = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR_EMAIL);
            int termIdInt = Integer.parseInt(termId);

            Course course = new Course(termIdInt, title, date_start, date_end, notes, status_course, mentor, mentor_phone, mentor_email);
            course.setCourseId(id);
            courseViewModel.update(course);

            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_courses:
                courseViewModel.deleteAllCourses();
                Toast.makeText(this, "All courses deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}


