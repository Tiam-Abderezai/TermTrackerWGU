//package com.example.term_tracker_wgu;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.term_tracker_wgu.ViewModel.TermViewModel;
//
//public class MainActivity extends AppCompatActivity {
//
////    private Button termsButton;
////    private Button coursesButton;
////    private Button assessmentsButton;
////    private Button mentorsButton;
////
////    private TermViewModel termViewModel;
////    private TermViewModel courseViewModel;
////    private TermViewModel mentorViewModel;
////    private TermViewModel assessmentViewModel;
//
//    ////    final Button coursesButton = findViewById(R.id.courses_button);
////    final Button assessmentsButton = findViewById(R.id.assessments_button);
////    final Button mentorsButton = findViewById(R.id.mentors_button);
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_terms);
//
//
////        termsButton = (Button) findViewById(R.id.terms_button);
////        termsButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                loadTermsActivity();
////            }
////        });
//
//
//        coursesButton = (Button) findViewById(R.id.courses_button);
//        coursesButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadCoursesActivity();
//            }
//        });
//
//
//        assessmentsButton = (Button) findViewById(R.id.assessments_button);
//        assessmentsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadAssessmentsActivity();
//            }
//        });
//
//        mentorsButton = (Button) findViewById(R.id.mentors_button);
//        mentorsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loadMentorsActivity();
//            }
//        });
//
//    }
//
//    public void loadTermsActivity() {
//        Intent intent = new Intent(this, TermsActivity.class);
//        startActivity(intent);
//    }
//
//    public void loadCoursesActivity() {
//        Intent intent = new Intent(this, CoursesActivity.class);
//        startActivity(intent);
//    }
//
//    public void loadAssessmentsActivity() {
//        Intent intent = new Intent(this, AssessmentsActivity.class);
//        startActivity(intent);
//    }
//
//    public void loadMentorsActivity() {
//        Intent intent = new Intent(this, MentorsActivity.class);
//        startActivity(intent);
//    }
//
//
//}
//
