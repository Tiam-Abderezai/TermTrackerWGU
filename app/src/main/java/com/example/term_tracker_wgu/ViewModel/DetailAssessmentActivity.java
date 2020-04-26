// AddAssessmentActivity

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.term_tracker_wgu.Model.Assessment;
import com.example.term_tracker_wgu.Notifications.AlertReceiver;
import com.example.term_tracker_wgu.R;

import java.util.Date;

public class DetailAssessmentActivity extends AppCompatActivity {

    public static final int EDIT_ASSESSMENT_REQUEST = 3;

    public static final String EXTRA_ASSESSMENT_ID =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_ASSESSMENT_ID";

    public static final String EXTRA_TYPE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_TYPE";

    public static final String EXTRA_DUE_DATE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_DUE_DATE";

    public static final String EXTRA_STATUS_ASSESSMENT =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_STATUS_ASSESSMENT";

    private TextView detailTextType;
    private TextView detailTextDueDate;
    private TextView detailTextStatus;

    String courseId;
    String assessmentId;

    String type;
    String due_date;
    String status;

    public long milisecs = System.currentTimeMillis();
    public static final int REQUEST_START_DATE_CODE = 201;
    final String CHANNEL_ID = "Channel id";
    NotificationCompat.Builder builder;
    AlarmManager alarmManager;
    PendingIntent pendingIntentStart;
    PendingIntent pendingIntentEnd;

    @RequiresApi(api = Build.VERSION_CODES.N)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_assessment);

        detailTextType = findViewById(R.id.detail_text_type);
        detailTextDueDate = findViewById(R.id.detail_text_due_date);
        detailTextStatus = findViewById(R.id.detail_text_status_assessment);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ASSESSMENT_ID)) {
            setTitle("Assessment Detail");

            courseId = intent.getStringExtra(DetailCourseActivity.EXTRA_COURSE_ID);
            assessmentId = intent.getStringExtra(DetailAssessmentActivity.EXTRA_ASSESSMENT_ID);
            detailTextType.setText(intent.getStringExtra(DetailAssessmentActivity.EXTRA_TYPE));
            detailTextDueDate.setText(intent.getStringExtra(DetailAssessmentActivity.EXTRA_DUE_DATE));
            detailTextStatus.setText(intent.getStringExtra(DetailAssessmentActivity.EXTRA_STATUS_ASSESSMENT));

        } else {
            setTitle("Assessment Detail");
        }

        setTitle("Assessment Detail");



        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        Intent alarmIntent = new Intent(this, AlertReceiver.class);
        pendingIntentStart = PendingIntent.getBroadcast(this, REQUEST_START_DATE_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        // Current Time Variables
        long currentTimeStartDate = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");

        Date resultDate = new Date(currentTimeStartDate);
        System.out.println("Current Date" + sdf.format(resultDate));


        String stringStartDate = detailTextDueDate.getText().toString().replace(" ", "").replace("-", "");
        System.out.println("@@@");
        System.out.println("Start Date" + stringStartDate);
        System.out.println("Result Date" + sdf.format(resultDate));
//        System.out.println(sdf.format(stringStartDate));
        Toast.makeText(this, "Assessment ", Toast.LENGTH_LONG).show();
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntentStart);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_assessment_menu, menu);
        return true;
    }

    public void onOptionsItemSelected(Assessment assessment) {
        Intent intent = new Intent(DetailAssessmentActivity.this, EditAssessmentActivity.class);
        intent.putExtra(DetailCourseActivity.EXTRA_COURSE_ID, courseId);
        intent.putExtra(DetailAssessmentActivity.EXTRA_ASSESSMENT_ID, assessmentId);
        intent.putExtra(DetailAssessmentActivity.EXTRA_TYPE, assessment.getAssessmentType());
        intent.putExtra(DetailAssessmentActivity.EXTRA_DUE_DATE, assessment.getAssessmentDueDate());
        intent.putExtra(DetailAssessmentActivity.EXTRA_STATUS_ASSESSMENT, assessment.getAssessmentStatus());

        startActivityForResult(intent, EDIT_ASSESSMENT_REQUEST);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_assessment:

                Intent intent = new Intent(this, EditAssessmentActivity.class);
                intent.putExtra(DetailCourseActivity.EXTRA_COURSE_ID, courseId);
                intent.putExtra(DetailAssessmentActivity.EXTRA_ASSESSMENT_ID, assessmentId);
                intent.putExtra(DetailAssessmentActivity.EXTRA_TYPE, detailTextType.getText().toString());
                intent.putExtra(DetailAssessmentActivity.EXTRA_DUE_DATE, detailTextDueDate.getText().toString());
                intent.putExtra(DetailAssessmentActivity.EXTRA_STATUS_ASSESSMENT, detailTextStatus.getText().toString());

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
