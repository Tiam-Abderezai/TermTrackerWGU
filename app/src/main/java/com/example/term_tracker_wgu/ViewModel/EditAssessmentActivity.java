
package com.example.term_tracker_wgu.ViewModel;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.term_tracker_wgu.Model.Assessment;
import com.example.term_tracker_wgu.Notifications.AlertReceiver;
import com.example.term_tracker_wgu.R;

import java.text.DateFormat;
import java.util.Calendar;

public class EditAssessmentActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_ID";

    public static final String EXTRA_TYPE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_TYPE";

    public static final String EXTRA_DUE_DATE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_DUE_DATE";

    public static final String EXTRA_STATUS_ASSESSMENT =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_STATUS_ASSESSMENT";


    private String courseId;
    private String assessmentId;

    //    private EditText editTextId;
    private EditText editTextType;
    private TextView editTextDueDate;
    private EditText editTextStatus;


    private Button buttonDateDue;
    private Calendar calendarDueDate;


    static final int DATE_DIALOG_ID = 0;

    private TextView textDateActive;
    private Calendar calendarDateActive;


    String id;
    String type;
    String due_date;
    String status_assessment;

    public AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment);

//        editTextID = findViewById(R.id.edit_text_id_term);
        editTextType = findViewById(R.id.edit_text_type);
        editTextDueDate = findViewById(R.id.edit_text_due_date);
        editTextStatus = findViewById(R.id.edit_text_status_assessment);


        buttonDateDue = (Button) findViewById(R.id.buttonDueDate);

        calendarDueDate = Calendar.getInstance();


        buttonDateDue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(editTextDueDate, calendarDueDate);
            }
        });


        updateDisplay(editTextDueDate, calendarDueDate);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(DetailCourseActivity.EXTRA_COURSE_ID)) {

            courseId = intent.getStringExtra(DetailCourseActivity.EXTRA_COURSE_ID);
            assessmentId = intent.getStringExtra(DetailAssessmentActivity.EXTRA_ASSESSMENT_ID);
            type = intent.getStringExtra(DetailAssessmentActivity.EXTRA_TYPE);
            due_date = intent.getStringExtra(DetailAssessmentActivity.EXTRA_DUE_DATE);
            status_assessment = intent.getStringExtra(DetailAssessmentActivity.EXTRA_STATUS_ASSESSMENT);
            setTitle("Edit Assessment");

            editTextType.setText(type);
            editTextDueDate.setText(due_date);
            editTextStatus.setText(status_assessment);

        } else {
            setTitle("Edit Assessment");
        }

        setTitle("Edit Assessment");

    }


    private void updateDisplay(TextView dateDisplay, Calendar date) {
        dateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(date.get(Calendar.MONTH) + 1).append("-")
                        .append(date.get(Calendar.DAY_OF_MONTH)).append("-")
                        .append(date.get(Calendar.YEAR)).append(" "));

    }

    public void showDateDialog(TextView dateDisplay, Calendar date) {
        textDateActive = dateDisplay;
        calendarDateActive = date;
        showDialog(DATE_DIALOG_ID);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendarDateActive.set(Calendar.YEAR, year);
            calendarDateActive.set(Calendar.MONTH, monthOfYear);
            calendarDateActive.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay(textDateActive, calendarDateActive);
            unregisterDateDisplay();
        }
    };

    private void unregisterDateDisplay() {
        textDateActive = null;
        calendarDateActive = null;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, dateSetListener, calendarDateActive.get(Calendar.YEAR), calendarDateActive.get(Calendar.MONTH), calendarDateActive.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(calendarDateActive.get(Calendar.YEAR), calendarDateActive.get(Calendar.MONTH), calendarDateActive.get(Calendar.DAY_OF_MONTH));
                break;
        }
    }


    //        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
////    @Override
//    public void onDateSet(DatePickerFragment view, int year, int month, int dayOfMonth) {
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.YEAR, year);
//        c.set(Calendar.MONTH, month);
//        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//        updateDateText(c);
//        startAlarm(c);
//    }
    //


    private void updateDateText(Calendar c) {
        String dateText = "Alarm set for: ";
        dateText += DateFormat.getDateInstance(DateFormat.SHORT);

        editTextDueDate.setText(dateText);
    }

    //
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        editTextDueDate.setText("Alarm canceled");
//    }
    }


    private void saveAssessment() {
        String type = editTextType.getText().toString();
        String due_date = editTextDueDate.getText().toString();
        String status_assessment = editTextStatus.getText().toString();


        if (type.trim().isEmpty() || due_date.trim().isEmpty()) {
            Toast.makeText(this, "Please insert the name and dates.", Toast.LENGTH_SHORT).show();


            return;
        }


        Assessment assessment = new Assessment(Integer.parseInt(courseId), Integer.parseInt(assessmentId), type, due_date, status_assessment);
        assessmentViewModel.update(assessment);

        Intent data = new Intent();
        data.putExtra(DetailCourseActivity.EXTRA_COURSE_ID, courseId);
        data.putExtra(DetailAssessmentActivity.EXTRA_ASSESSMENT_ID, assessmentId);
        data.putExtra(DetailAssessmentActivity.EXTRA_TYPE, type);
        data.putExtra(DetailAssessmentActivity.EXTRA_DUE_DATE, due_date);
        data.putExtra(DetailAssessmentActivity.EXTRA_STATUS_ASSESSMENT, status_assessment);

//        int id = getIntent().getIntExtra(EditAssessmentActivity.EXTRA_ID, -1);
//        if(id != -1) {
//            data.putExtra(EditAssessmentActivity.EXTRA_ID, id);
//        }

//        setResult(RESULT_OK, data);
//        finish();
//        Intent intent = new Intent(DetailAssessmentActivity.class);
//        startActivityForResult();

        setResult(RESULT_OK, data);
        finish();

    }

//    private void startActivityForResult() {
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_assessment_menu, menu);
        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_assessment:
                saveAssessment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
