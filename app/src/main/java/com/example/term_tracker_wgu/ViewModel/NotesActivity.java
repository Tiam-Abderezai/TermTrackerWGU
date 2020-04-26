package com.example.term_tracker_wgu.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.term_tracker_wgu.Model.Course;
import com.example.term_tracker_wgu.R;

import static android.content.Intent.createChooser;


public class NotesActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_COURSE_ID";

    private TextView detailTextNote;
    String termId;
    String courseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        detailTextNote = findViewById(R.id.text_note);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_share);


        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        detailTextNote.setText(str);

        if (intent.hasExtra(DetailTermActivity.EXTRA_ID)) {


            termId = intent.getStringExtra(DetailTermActivity.EXTRA_ID);
            courseId = intent.getStringExtra(DetailCourseActivity.EXTRA_COURSE_ID);
            detailTextNote.setText(intent.getStringExtra(DetailCourseActivity.EXTRA_NOTE));

            setTitle("Note Title");

        } else {
            setTitle("Course Detail");
        }

        setTitle("Course Detail");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share_button:

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Your Body Here";
                String shareSubject = "Your Subject Here";

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);

                startActivity(Intent.createChooser(shareIntent, "Share Using"));
                break;
        }

        return super.onOptionsItemSelected(item);

    }
}





