
package com.example.term_tracker_wgu.ViewModel;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.term_tracker_wgu.Fragments.TimePickerFragment;
import com.example.term_tracker_wgu.Notifications.AlertReceiver;
import com.example.term_tracker_wgu.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddCourseActivity extends AppCompatActivity {

//    public static final String EXTRA_ID =
//            "com.example.term_tracker_wgu.ViewModel.EXTRA_ID";
//
//    public static final String EXTRA_TITLE =
//            "com.example.term_tracker_wgu.ViewModel.EXTRA_TITLE";
//
//    public static final String EXTRA_DATE_START =
//            "com.example.term_tracker_wgu.ViewModel.DATE_START";
//
//    public static final String EXTRA_DATE_END =
//            "com.example.term_tracker_wgu.ViewModel.DATE_END";
//
//    public static final String EXTRA_NOTE =
//            "com.example.term_tracker_wgu.ViewModel.DATE_NOTE";
//
//    public static final String EXTRA_STATUS_COURSE =
//            "com.example.term_tracker_wgu.ViewModel.DATE_STATUS_COURSE";


    private EditText addTextID;
    private EditText addTextTitle;
    private TextView addTextStartDate;
    private TextView addTextEndDate;
    private EditText addTextNote;
    private EditText addTextStatus;
    private EditText addTextMentor;
    private EditText addTextMentorPhone;
    private EditText addTextMentorEmail;

    private String termId;


    private Button buttonDateStart;
    private Button buttonDateEnd;
    private Calendar calendarStartDate;
    private Calendar calendarEndDate;

    static final int DATE_DIALOG_ID = 0;

    private TextView textDateActive;
    private Calendar calendarDateActive;

    private TextView textTimeActive;
    private Calendar calendarTimeActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        addTextTitle = findViewById(R.id.add_text_title_course);
        addTextStartDate = findViewById(R.id.add_text_date_start);
        addTextEndDate = findViewById(R.id.add_text_date_end);
        addTextNote = findViewById(R.id.add_text_note_course);
        addTextStatus = findViewById(R.id.add_text_status_course);
        addTextMentor = findViewById(R.id.add_text_mentor);
        addTextMentorPhone = findViewById(R.id.add_text_mentor_phone);
        addTextMentorEmail = findViewById(R.id.add_text_mentor_email);


        buttonDateStart = (Button) findViewById(R.id.buttonStartDate);
        buttonDateEnd = (Button) findViewById(R.id.buttonEndDate);

        calendarStartDate = Calendar.getInstance();
        calendarEndDate = Calendar.getInstance();


        buttonDateStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(addTextStartDate, calendarStartDate);
            }
        });


        buttonDateEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(addTextEndDate, calendarEndDate);
            }
        });

        updateDisplay(addTextStartDate, calendarStartDate);
        updateDisplay(addTextEndDate, calendarEndDate);




        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(DetailTermActivity.EXTRA_ID)) {
            setTitle("Add Course");
//            addTextID.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_ID));
//            addTextTitle.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_TITLE));
//            addTextStartDate.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_DATE_START));
//            addTextEndDate.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_DATE_END));
//            addTextNote.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_NOTE));
//            addTextStatus.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_STATUS_COURSE));
            termId = intent.getStringExtra(DetailTermActivity.EXTRA_ID);

        } else {
            setTitle("Add Course");
        }

        setTitle("Add Course");

    }


    private void updateDisplay(TextView dateDisplay, Calendar date) {
        dateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(date.get(Calendar.YEAR)).append(" ")
                        .append(date.get(Calendar.MONTH) + 1).append("-")
                        .append(date.get(Calendar.DAY_OF_MONTH)).append("-"));
//                        .append(date.get(Calendar.HOUR_OF_DAY)).append(" ")
//                        .append(date.get(Calendar.MINUTE)).append(" "));


    }

    public void showDateDialog(TextView dateDisplay, Calendar date) {
        textDateActive = dateDisplay;
        calendarDateActive = date;
        showDialog(DATE_DIALOG_ID);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()

    {
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


//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
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

        addTextStartDate.setText(dateText);
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
        addTextStartDate.setText("Alarm canceled");
//    }
    }


    private void saveCourse() {
        String title = addTextTitle.getText().toString();
        String date_start = addTextStartDate.getText().toString();
        String date_end = addTextEndDate.getText().toString();
        String note = addTextNote.getText().toString();
        String status_course = addTextStatus.getText().toString();
        String mentor = addTextMentor.getText().toString();
        String mentor_phone = addTextMentorPhone.getText().toString();
        String mentor_email = addTextMentorEmail.getText().toString();


        if (title.trim().isEmpty() || date_start.trim().isEmpty()) {
            Toast.makeText(this, "Please insert the name and dates.", Toast.LENGTH_SHORT).show();


            return;
        }


        Intent data = new Intent();
        data.putExtra(DetailTermActivity.EXTRA_ID, termId);
        data.putExtra(DetailCourseActivity.EXTRA_TITLE, title);
        data.putExtra(DetailCourseActivity.EXTRA_DATE_START, date_start);
        data.putExtra(DetailCourseActivity.EXTRA_DATE_END, date_end);
        data.putExtra(DetailCourseActivity.EXTRA_NOTE, note);
        data.putExtra(DetailCourseActivity.EXTRA_STATUS_COURSE, status_course);
        data.putExtra(DetailCourseActivity.EXTRA_MENTOR, mentor);
        data.putExtra(DetailCourseActivity.EXTRA_MENTOR_PHONE, mentor_phone);
        data.putExtra(DetailCourseActivity.EXTRA_MENTOR_EMAIL, mentor_email);




        int id = getIntent().getIntExtra(DetailTermActivity.EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(DetailTermActivity.EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_course_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_course:
                saveCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
