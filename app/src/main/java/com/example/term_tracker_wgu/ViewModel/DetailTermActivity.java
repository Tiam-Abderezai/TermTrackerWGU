package com.example.term_tracker_wgu.ViewModel;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.term_tracker_wgu.Adapters.CourseAdapter;
//import com.example.term_tracker_wgu.CoursesActivity;
import com.example.term_tracker_wgu.Model.Course;
import com.example.term_tracker_wgu.Model.Term;
import com.example.term_tracker_wgu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.term_tracker_wgu.TermsActivity.DETAIL_TERM_REQUEST;

public class DetailTermActivity extends AppCompatActivity {

    // Variable to represent Add_Course_Request
    public static final int ADD_COURSE_REQUEST = 1;
    public static final int DETAIL_COURSE_REQUEST = 2;
    public static final int EDIT_TERM_REQUEST = 3;


    public CourseViewModel courseViewModel;


    public static final String EXTRA_ID =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_ID";

//    public static final String EXTRA_TERM_ID =
//            "com.example.term_tracker_wgu.ViewModel.EXTRA_TERM_ID";

    public static final String EXTRA_TITLE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_TITLE";

    public static final String EXTRA_DATE_START =
            "com.example.term_tracker_wgu.ViewModel.DATE_START";

    public static final String EXTRA_DATE_END =
            "com.example.term_tracker_wgu.ViewModel.DATE_END";

    public static final String EXTRA_STATUS_TERM =
            "com.example.term_tracker_wgu.ViewModel.STATUS_TERM";
    public static final String EXTRA_NOTE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_NOTE";
    public static final String EXTRA_STATUS =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_STATUS";



    // Variables to represent "TextView" layout
//    private TextView detailTextId;
    private TextView detailTextTitle;
    private TextView detailTextStartDate;
    private TextView detailTextEndDate;
    private TextView detailTextStatus;


    // Variables to represent Term object data
//    private String termId;
    String termId;



    // onCreate method used to load "activity_detail_term" layout,
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_term);
//        Calendar calendar = Calendar.getInstance();
//        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        // Variables that represent detail term text data
//        detailTextId = findViewById(R.id.detail_text_id_term);

        detailTextTitle = findViewById(R.id.detail_text_title_term);




        detailTextStartDate = findViewById(R.id.detail_text_date_start_term);


        //        detailTextStartDate.setText(currentDate);
        detailTextEndDate = findViewById(R.id.detail_text_date_end_term);
//        detailTextEndDate.setText(currentDate);

        detailTextStatus = findViewById(R.id.detail_text_status_term);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        // Loads intent using "getIntent()" method
        Intent intent = getIntent();


        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Term Detail");

            //Strings in the form mm/dd/yyyy

            termId = intent.getStringExtra(DetailTermActivity.EXTRA_ID);
            detailTextTitle.setText(intent.getStringExtra(DetailTermActivity.EXTRA_TITLE));
//            detailTextStartDate.setText(ldtStart.toString());
//            detailTextEndDate.setText(ldtEnd.toString());
            detailTextStartDate.setText(intent.getStringExtra(DetailTermActivity.EXTRA_DATE_START));
            detailTextEndDate.setText(intent.getStringExtra(DetailTermActivity.EXTRA_DATE_END));
            detailTextStatus.setText(intent.getStringExtra(DetailTermActivity.EXTRA_STATUS));


        } else {
            setTitle("Term Detail");
        }

        setTitle("Term Detail");

        FloatingActionButton buttonAddCourse = findViewById(R.id.button_add_course);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailTermActivity.this, AddCourseActivity.class);
                intent.putExtra(DetailTermActivity.EXTRA_ID, termId);
                startActivityForResult(intent, ADD_COURSE_REQUEST);
            }

        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courses) {

                List<Course> coursesInTerm = new ArrayList<>(); //create a new list
                int intId = Integer.parseInt(termId); //convert the class-level String variable id to an int so I can compare later on
                for (Course c : courses) { //each course in courses will have a turn to be c
                    if (c.getTermId() == intId) { //if the term id in the course matches the class-level id variable..
                        coursesInTerm.add(c); //add the course to coursesInTerm
                    }
                }
                adapter.setCourses(coursesInTerm);//set the courses in the adapter (this line used to be adapter.setCourses(courses)
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
                Toast.makeText(DetailTermActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(DetailTermActivity.this, DetailCourseActivity.class);
                int courseIntId = course.getCourseId();
                String strId = Integer.toString(courseIntId);
                intent.putExtra(DetailTermActivity.EXTRA_ID, termId);
                intent.putExtra(DetailCourseActivity.EXTRA_COURSE_ID, strId);
                intent.putExtra(DetailCourseActivity.EXTRA_TITLE, course.getCourseTitle());
                intent.putExtra(DetailCourseActivity.EXTRA_DATE_START, course.getCourseDateStart());
                intent.putExtra(DetailCourseActivity.EXTRA_DATE_END, course.getCourseDateEnd());
                intent.putExtra(DetailCourseActivity.EXTRA_NOTE, course.getCourseNote());
                intent.putExtra(DetailCourseActivity.EXTRA_STATUS_COURSE, course.getCourseStatus());
                intent.putExtra(DetailCourseActivity.EXTRA_MENTOR, course.getCourseMentor());
                intent.putExtra(DetailCourseActivity.EXTRA_MENTOR_PHONE, course.getCourseMentorPhone());
                intent.putExtra(DetailCourseActivity.EXTRA_MENTOR_EMAIL, course.getCourseMentorEmail());
                startActivityForResult(intent, DETAIL_COURSE_REQUEST);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_term_menu, menu);
        return true;
    }

    //
//        @Override
    public void onOptionsItemSelected(Term term) {
        Intent intent = new Intent(DetailTermActivity.this, EditTermActivity.class);
        intent.putExtra(DetailTermActivity.EXTRA_ID, termId);
        intent.putExtra(DetailTermActivity.EXTRA_TITLE, term.getTermTitle());
        intent.putExtra(DetailTermActivity.EXTRA_DATE_START, term.getTermDateStart());
        intent.putExtra(DetailTermActivity.EXTRA_DATE_END, term.getTermDateEnd());
        intent.putExtra(DetailTermActivity.EXTRA_STATUS_TERM, term.getTermStatus());
        startActivityForResult(intent, EDIT_TERM_REQUEST);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_term:
                Intent intent = new Intent(this, EditTermActivity.class);
                intent.putExtra(DetailTermActivity.EXTRA_ID, termId);
                intent.putExtra(DetailTermActivity.EXTRA_TITLE, detailTextTitle.getText().toString());
                intent.putExtra(DetailTermActivity.EXTRA_DATE_START, detailTextStartDate.getText().toString());
                intent.putExtra(DetailTermActivity.EXTRA_DATE_END, detailTextEndDate.getText().toString());
                intent.putExtra(DetailTermActivity.EXTRA_STATUS_TERM, detailTextStatus.getText().toString());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {

            String termId = data.getStringExtra(DetailTermActivity.EXTRA_ID);
            String courseTitle = data.getStringExtra(DetailCourseActivity.EXTRA_TITLE);
            String courseDateStart = data.getStringExtra(DetailCourseActivity.EXTRA_DATE_START);
            String courseDateEnd = data.getStringExtra(DetailCourseActivity.EXTRA_DATE_END);
            String courseNote = data.getStringExtra(DetailCourseActivity.EXTRA_NOTE);
            String courseStatus = data.getStringExtra(DetailCourseActivity.EXTRA_STATUS_COURSE);
            String courseMentor = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR);
            String courseMentorPhone = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR_PHONE);
            String courseMentorEmail = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR_EMAIL);
//String courseTitle, String courseDateStart, String courseDateEnd, String courseNote, String courseStatus
            int termIdInt = Integer.parseInt(termId);
            Course course = new Course(termIdInt, courseTitle, courseDateStart, courseDateEnd, courseNote, courseStatus,courseMentor,courseMentorPhone,courseMentorEmail);
            courseViewModel.insert(course);

            Toast.makeText(this, "Course saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == DETAIL_COURSE_REQUEST && resultCode == RESULT_OK) {
            int courseId = data.getIntExtra(DetailCourseActivity.EXTRA_COURSE_ID, -1);
            if (courseId == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String termId = data.getStringExtra(DetailTermActivity.EXTRA_ID);
            String courseTitle = data.getStringExtra(DetailCourseActivity.EXTRA_TITLE);
            String courseDateStart = data.getStringExtra(DetailCourseActivity.EXTRA_DATE_START);
            String courseDateEnd = data.getStringExtra(DetailCourseActivity.EXTRA_DATE_END);
            String courseNote = data.getStringExtra(DetailCourseActivity.EXTRA_NOTE);
            String courseStatus = data.getStringExtra(DetailCourseActivity.EXTRA_STATUS_COURSE);
            String courseMentor = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR);
            String courseMentorPhone = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR_PHONE);
            String courseMentorEmail = data.getStringExtra(DetailCourseActivity.EXTRA_MENTOR_EMAIL);

            int termIdInt = Integer.parseInt(termId);
            Course course = new Course(termIdInt, courseTitle, courseDateStart, courseDateEnd, courseNote, courseStatus, courseMentor, courseMentorPhone, courseMentorEmail);
            course.setCourseId(courseId);
            courseViewModel.update(course);

            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();


        }
    }

}
