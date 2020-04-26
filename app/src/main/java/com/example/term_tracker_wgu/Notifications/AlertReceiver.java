package com.example.term_tracker_wgu.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.term_tracker_wgu.ViewModel.DetailCourseActivity;

import java.util.Date;


public class AlertReceiver extends BroadcastReceiver {
    //    private static final int LONG_DELAY = 3500;
//    private CountDownTimer toastCountDown;
    private Toast message;

    ;


    long currentTimeStartDate = System.currentTimeMillis();
//
//    Intent intent = getIntent();
//        if (intent.hasExtra(EXTRA_COURSE_ID)) {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {

        SimpleDateFormat sdfStart = new SimpleDateFormat("yyyy-MM-dd");
        Date resultDate = new Date(currentTimeStartDate);
        sdfStart.format(resultDate);


        Toast.makeText(context, "STARTs on " + resultDate, Toast.LENGTH_LONG).show();


//        Toast.makeText(context, "Course starts today " + resultStartDate + " and ends on " + resultEndDate, Toast.LENGTH_SHORT).show();

    }


}
