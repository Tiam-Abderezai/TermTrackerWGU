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

import com.example.term_tracker_wgu.Model.Term;
import com.example.term_tracker_wgu.Notifications.AlertReceiver;
import com.example.term_tracker_wgu.R;

import java.text.DateFormat;
import java.util.Calendar;

public class EditTermActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_TITLE";

    public static final String EXTRA_DATE_START =
            "com.example.term_tracker_wgu.ViewModel.DATE_START";

    public static final String EXTRA_DATE_END =
            "com.example.term_tracker_wgu.ViewModel.DATE_END";

    public static final String EXTRA_STATUS_TERM =
            "com.example.term_tracker_wgu.ViewModel.STATUS_TERM";

    private Button buttonDateStart;
    private Button buttonDateEnd;
    private Calendar calendarStartDate;
    private Calendar calendarEndDate;

    static final int DATE_DIALOG_ID = 0;

    private TextView textDateActive;
    private Calendar calendarDateActive;


    private EditText editTextTitle;
    private TextView editTextStartDate;
    private TextView editTextEndDate;
    private EditText editTextStatus;

    String id;
    String title;
    String date_start;
    String date_end;
    String status;

    public TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);

        editTextTitle = findViewById(R.id.edit_text_title_term);
        editTextStartDate = findViewById(R.id.edit_text_date_start);
        editTextEndDate = findViewById(R.id.edit_text_date_end);
        editTextStatus = findViewById(R.id.edit_text_status_term);


        buttonDateStart = (Button) findViewById(R.id.buttonStartDate);
        buttonDateEnd = (Button) findViewById(R.id.buttonEndDate);

        calendarStartDate = Calendar.getInstance();
        calendarEndDate = Calendar.getInstance();


        buttonDateStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(editTextStartDate, calendarStartDate);
            }
        });


        buttonDateEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDateDialog(editTextEndDate, calendarEndDate);
            }
        });

        updateDisplay(editTextStartDate, calendarStartDate);
        updateDisplay(editTextEndDate, calendarEndDate);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(DetailTermActivity.EXTRA_ID)) {

            id = intent.getStringExtra(DetailTermActivity.EXTRA_ID);
            title = intent.getStringExtra(DetailTermActivity.EXTRA_TITLE);
            date_start = intent.getStringExtra(DetailTermActivity.EXTRA_DATE_START);
            date_end = intent.getStringExtra(DetailTermActivity.EXTRA_DATE_END);
            status =  intent.getStringExtra(DetailTermActivity.EXTRA_STATUS_TERM);
            setTitle("Edit Term");


            editTextTitle.setText(title);
            editTextStartDate.setText(date_start);
            editTextEndDate.setText(date_end);
            editTextStatus.setText(status);




        } else {
            setTitle("Edit Term");
        }

        setTitle("Edit Term");

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

        editTextStartDate.setText(dateText);
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
        editTextStartDate.setText("Alarm canceled");
//    }
    }

    private void saveTerm() {

        String title = editTextTitle.getText().toString();
        String date_start = editTextStartDate.getText().toString();
        String date_end = editTextEndDate.getText().toString();
        String status = editTextStatus.getText().toString();


        if (title.trim().isEmpty() || date_start.trim().isEmpty() || date_end.trim().isEmpty()) {
            Toast.makeText(this, "Please insert the name and dates.", Toast.LENGTH_SHORT).show();


            return;
        }



        Term term = new Term(Integer.parseInt(id), title, date_start, date_end, status);
        termViewModel.update(term);

        Intent data = new Intent();
        data.putExtra(DetailTermActivity.EXTRA_ID, id);
        data.putExtra(DetailTermActivity.EXTRA_TITLE, title);
        data.putExtra(DetailTermActivity.EXTRA_DATE_START, date_start);
        data.putExtra(DetailTermActivity.EXTRA_DATE_END, date_end);
        data.putExtra(DetailTermActivity.EXTRA_STATUS_TERM, status);

//        int id = getIntent().getIntExtra(EditTermActivity.EXTRA_ID, -1);
//        if (id != null) {
//            data.putExtra(EditTermActivity.EXTRA_ID, id);
//        }

        setResult(RESULT_OK, data);
        finish();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_term_menu, menu);
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_term:
                saveTerm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
