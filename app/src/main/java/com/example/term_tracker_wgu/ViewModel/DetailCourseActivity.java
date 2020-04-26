//AddCourseActivity

package com.example.term_tracker_wgu.ViewModel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.term_tracker_wgu.Adapters.AssessmentAdapter;
//import com.example.term_tracker_wgu.Adapters.MentorAdapter;
import com.example.term_tracker_wgu.Model.Assessment;
import com.example.term_tracker_wgu.Model.Course;
//import com.example.term_tracker_wgu.Model.Mentor;
import com.example.term_tracker_wgu.Notifications.AlertReceiver;
import com.example.term_tracker_wgu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DetailCourseActivity extends AppCompatActivity {


    public static final int ADD_ASSESSMENT_REQUEST = 1;
    public static final int DETAIL_ASSESSMENT_REQUEST = 2;
    public static final int EDIT_COURSE_REQUEST = 3;

    public AssessmentViewModel assessmentViewModel;

    public static final String EXTRA_COURSE_ID =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_COURSE_ID";
    //    public static final String EXTRA_ID =
//            "com.example.term_tracker_wgu.ViewModel.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_TITLE";
    public static final String EXTRA_DATE_START =
            "com.example.term_tracker_wgu.ViewModel.DATE_START";
    public static final String EXTRA_DATE_END =
            "com.example.term_tracker_wgu.ViewModel.DATE_END";
    public static final String EXTRA_NOTE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_NOTE";
    public static final String EXTRA_STATUS_COURSE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_STATUS_COURSE";
    public static final String EXTRA_MENTOR =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_MENTOR";
    public static final String EXTRA_MENTOR_PHONE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_MENTOR_PHONE";
    public static final String EXTRA_MENTOR_EMAIL =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_MENTOR_EMAIL";


    private TextView detailTextTitle;
    private TextView detailTextStartDate;
    public TextView detailTextEndDate;
    private TextView detailTextNote;
    private TextView detailTextStatus;
    private TextView detailTextMentor;
    private TextView detailTextMentorPhone;
    private TextView detailTextMentorEmail;

    private Button viewNotesButton;

//    final Button viewNotesButton = findViewById(R.id.viewNotes);

    String termId;
    String courseId;
//    String courseTitle;
//    String courseDateStart;
//    String courseDateEnd;
//    String courseNote;
//    String courseStatus;


//    String assessmentTitle;
//    String assessmentDueDate;
//    String assessmentStatus;

    public long milisecs = System.currentTimeMillis();
    public static final int REQUEST_START_DATE_CODE = 101;
    public static final int REQUEST_END_DATE_CODE = 201;
    final String CHANNEL_ID = "Channel id";
    NotificationCompat.Builder builder;
    AlarmManager alarmManager;
    PendingIntent pendingIntentStart;
    PendingIntent pendingIntentEnd;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_course);


        detailTextTitle = findViewById(R.id.detail_text_title_course);
        detailTextStartDate = findViewById(R.id.detail_text_date_start_course);
        detailTextEndDate = findViewById(R.id.detail_text_date_end_course);
        detailTextNote = findViewById(R.id.detail_text_note_course);
        detailTextStatus = findViewById(R.id.detail_text_status_course);
        detailTextMentor = findViewById(R.id.detail_text_mentor);
        detailTextMentorPhone = findViewById(R.id.detail_text_mentor_phone);
        detailTextMentorEmail = findViewById(R.id.detail_text_mentor_email);


        viewNotesButton = (Button) findViewById(R.id.viewNotes);

        viewNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = detailTextNote.getText().toString();
                Intent intent = new Intent(DetailCourseActivity.this, NotesActivity.class);
                intent.putExtra("message", str);
                startActivity(intent);
            }
        });


//        ActionBar fooActionBar =
//                getSupportActionBar();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_COURSE_ID)) {
            setTitle("Course Detail");
            termId = intent.getStringExtra(DetailTermActivity.EXTRA_ID);
            courseId = intent.getStringExtra(DetailCourseActivity.EXTRA_COURSE_ID);
            detailTextTitle.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_TITLE));
            detailTextStartDate.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_DATE_START));
            detailTextEndDate.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_DATE_END));
            detailTextNote.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_NOTE));
            detailTextStatus.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_STATUS_COURSE));
            detailTextMentor.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_MENTOR));
            detailTextMentorPhone.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_MENTOR_PHONE));
            detailTextMentorEmail.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_MENTOR_EMAIL));


        } else {
            setTitle("Course Detail");
        }

        setTitle("Course Detail");


        FloatingActionButton buttonAddAssessment = findViewById(R.id.button_add_assessment);
        buttonAddAssessment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailCourseActivity.this, AddAssessmentActivity.class);
                intent.putExtra(DetailCourseActivity.EXTRA_COURSE_ID, courseId);
                startActivityForResult(intent, ADD_ASSESSMENT_REQUEST);
            }

        });
//
//
        RecyclerView recyclerView = findViewById(R.id.recycler_view_assessment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
//
        final AssessmentAdapter adapter = new AssessmentAdapter();
        recyclerView.setAdapter(adapter);
//
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(@Nullable List<Assessment> assessments) {

                List<Assessment> assessmentsInCourse = new ArrayList<>(); //create a new list
                int intId = Integer.parseInt(courseId); //convert the class-level String variable id to an int so I can compare later on
                for (Assessment a : assessments) { //each assessment in assessments will have a turn to be m
                    if (a.getCourseId() == intId) { //if the course id in the assessment matches the class-level id variable..
                        assessmentsInCourse.add(a); //add the assessment to assessmentsInCourses
                    }
                }
                adapter.setAssessments(assessmentsInCourse);//set the assessments in the adapter (this line used to be adapter.setAssessments(assessments)
            }
        });
//
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            //
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                assessmentViewModel.delete(adapter.getAssessmentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(DetailCourseActivity.this, "Assessment deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Assessment assessment) {

                Intent intent = new Intent(DetailCourseActivity.this, DetailAssessmentActivity.class);
                int assessmentIntId = assessment.getAssessmentId();
                String strId = Integer.toString(assessmentIntId);
                intent.putExtra(DetailCourseActivity.EXTRA_COURSE_ID, courseId);
                intent.putExtra(DetailAssessmentActivity.EXTRA_ASSESSMENT_ID, strId);
                intent.putExtra(DetailAssessmentActivity.EXTRA_TYPE, assessment.getAssessmentType());
                intent.putExtra(DetailAssessmentActivity.EXTRA_DUE_DATE, assessment.getAssessmentDueDate());
                intent.putExtra(DetailAssessmentActivity.EXTRA_STATUS_ASSESSMENT, assessment.getAssessmentStatus());


//            intent.putExtra(DetailMentorActivity.EXTRA_PHONE, mentor.getMentorPhone());

                startActivityForResult(intent, DETAIL_ASSESSMENT_REQUEST);
            }
        });


        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        Intent alarmIntent = new Intent(this, AlertReceiver.class);
        pendingIntentStart = PendingIntent.getBroadcast(this, REQUEST_START_DATE_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        // Current Time Variables
        long currentTimeStartDate = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date resultDate = new Date(currentTimeStartDate);
        System.out.println("Current Date" + sdf.format(resultDate));


        String stringStartDate = detailTextStartDate.getText().toString().replace(" ", "").replace("-", "");
        System.out.println("Start Date" + stringStartDate);


        String stringEndDate = detailTextEndDate.getText().toString();

        System.out.println("@@@");
        System.out.println("Start Date" + stringStartDate);
        System.out.println("Result Date" + sdf.format(resultDate));
//        long longEndDate = Long.parseLong(stringEndDate);
//        } catch (NumberFormatException nfe){
//            System.out.println("NumberFormatException: " + nfe.getMessage());
//        }

        Intent endDateIntent = new Intent(this, AlertReceiver.class);
        intent.putExtra(DetailCourseActivity.EXTRA_DATE_END, stringEndDate);
        pendingIntentEnd = PendingIntent.getBroadcast(this, REQUEST_END_DATE_CODE, endDateIntent, PendingIntent.FLAG_UPDATE_CURRENT);


//        String sformatter = formatter.format(stringStartDate);
//        System.out.println(stringStartDate);

        Toast.makeText(this, "Course ", Toast.LENGTH_LONG).show();
        if (sdf.format(resultDate).equals(stringStartDate)) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntentStart);
//            alarmManager.set(AlarmManager.RTC_WAKEUP, longEndDate, pendingIntentEnd);
            if (stringEndDate != null) {
                Toast.makeText(this, "Course ENDs on " + stringEndDate, Toast.LENGTH_LONG).show();
            }

        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_course_menu, menu);
        return true;
    }


    public void onOptionsItemSelected(Course course) {
        Intent intent = new Intent(DetailCourseActivity.this, EditCourseActivity.class);
        intent.putExtra(DetailTermActivity.EXTRA_ID, termId);
        intent.putExtra(DetailCourseActivity.EXTRA_COURSE_ID, courseId);
        intent.putExtra(DetailCourseActivity.EXTRA_TITLE, course.getCourseTitle());
        intent.putExtra(DetailCourseActivity.EXTRA_DATE_START, course.getCourseDateStart());
        intent.putExtra(DetailCourseActivity.EXTRA_DATE_END, course.getCourseDateEnd());
        intent.putExtra(DetailCourseActivity.EXTRA_NOTE, course.getCourseNote());
        intent.putExtra(DetailCourseActivity.EXTRA_STATUS_COURSE, course.getCourseStatus());
        intent.putExtra(DetailCourseActivity.EXTRA_MENTOR, course.getCourseMentor());
        intent.putExtra(DetailCourseActivity.EXTRA_MENTOR_PHONE, course.getCourseMentorPhone());
        intent.putExtra(DetailCourseActivity.EXTRA_MENTOR_EMAIL, course.getCourseMentorEmail());


        startActivityForResult(intent, EDIT_COURSE_REQUEST);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_course:
                Intent intent = new Intent(this, EditCourseActivity.class);
                intent.putExtra(DetailTermActivity.EXTRA_ID, termId);
                intent.putExtra(DetailCourseActivity.EXTRA_COURSE_ID, courseId);
                intent.putExtra(DetailCourseActivity.EXTRA_TITLE, detailTextTitle.getText().toString());
                intent.putExtra(DetailCourseActivity.EXTRA_DATE_START, detailTextStartDate.getText().toString());
                intent.putExtra(DetailCourseActivity.EXTRA_DATE_END, detailTextEndDate.getText().toString());
                intent.putExtra(DetailCourseActivity.EXTRA_NOTE, detailTextNote.getText().toString());
                intent.putExtra(DetailCourseActivity.EXTRA_STATUS_COURSE, detailTextStatus.getText().toString());
                intent.putExtra(DetailCourseActivity.EXTRA_MENTOR, detailTextMentor.getText().toString());
                intent.putExtra(DetailCourseActivity.EXTRA_MENTOR_PHONE, detailTextMentorPhone.getText().toString());
                intent.putExtra(DetailCourseActivity.EXTRA_MENTOR_EMAIL, detailTextMentorEmail.getText().toString());

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {

            String courseId = data.getStringExtra(DetailCourseActivity.EXTRA_COURSE_ID);
            String assessmentType = data.getStringExtra(DetailAssessmentActivity.EXTRA_TYPE);
            String assessmentDueDate = data.getStringExtra(DetailAssessmentActivity.EXTRA_DUE_DATE);
            String assessmentStatus = data.getStringExtra(DetailAssessmentActivity.EXTRA_STATUS_ASSESSMENT);
//            String mentorPhone  = data.getStringExtra(DetailCourseActivity.EXTRA_NOTE);
//String courseTitle, String courseDateStart, String courseDateEnd, String courseNote, String courseStatus
            int courseIdInt = Integer.parseInt(courseId);
            Assessment assessment = new Assessment(courseIdInt, assessmentType, assessmentDueDate, assessmentStatus);
            assessmentViewModel.insert(assessment);

            Toast.makeText(this, "Assessment saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == DETAIL_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
            int assessmentId = data.getIntExtra(DetailAssessmentActivity.EXTRA_ASSESSMENT_ID, -1);
            if (assessmentId == -1) {
                Toast.makeText(this, "Assessment can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String courseId = data.getStringExtra(DetailCourseActivity.EXTRA_COURSE_ID);
            String assessmentType = data.getStringExtra(DetailAssessmentActivity.EXTRA_TYPE);
            String assessmentDueDate = data.getStringExtra(DetailAssessmentActivity.EXTRA_DUE_DATE);
            String assessmentStatus = data.getStringExtra(DetailAssessmentActivity.EXTRA_STATUS_ASSESSMENT);
//            String mentorPhone = data.getStringExtra(DetailAssessmentActivity.EXTRA_PHONE);

            int courseIdInt = Integer.parseInt(courseId);
            Assessment assessment = new Assessment(courseIdInt, assessmentType, assessmentDueDate, assessmentStatus);
            assessment.setAssessmentId(assessmentId);
            assessmentViewModel.update(assessment);

            Toast.makeText(this, "Assessment updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Assessment not saved", Toast.LENGTH_SHORT).show();


        }
    }


}

