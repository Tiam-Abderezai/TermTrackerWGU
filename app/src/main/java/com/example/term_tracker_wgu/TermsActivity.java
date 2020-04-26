// TermsActivity

package com.example.term_tracker_wgu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.term_tracker_wgu.Adapters.TermAdapter;
import com.example.term_tracker_wgu.Model.Course;
import com.example.term_tracker_wgu.Model.Term;
import com.example.term_tracker_wgu.ViewModel.AddTermActivity;
import com.example.term_tracker_wgu.ViewModel.CourseViewModel;
import com.example.term_tracker_wgu.ViewModel.DetailTermActivity;
import com.example.term_tracker_wgu.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TermsActivity extends AppCompatActivity {

//    private Button mainButton;

    // Static Add/Detail Request Constants
    public static final int ADD_TERM_REQUEST = 1;
    public static final int DETAIL_TERM_REQUEST = 2;
    // TermViewModel
    public TermViewModel termViewModel;
    public CourseViewModel courseViewModel;

    private int termId;

    private boolean termHasCourses;

    LiveData<List<Course>> courseList;

    // onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate TermsActivity
        super.onCreate(savedInstanceState);
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);

//                courseViewModel.getAllCourse()
        // Load "activity_terms" layout
        setContentView(R.layout.activity_terms);
        // Load FAB "button_add_term" layout
        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);
        // Load Add Term button
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            //Load onClick method
            public void onClick(View v) {
                // When clicked, loads AddTermActivity
                Intent intent = new Intent(TermsActivity.this, AddTermActivity.class);
                // ?
                startActivityForResult(intent, ADD_TERM_REQUEST);
            }
        });

        // Declare and load "recycler_view_terms" layout
        // Sets recyclerView configurations
        RecyclerView recyclerView = findViewById(R.id.recycler_view_terms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Declares "adapter" constant
        final TermAdapter adapter = new TermAdapter();
        // Calls "setAdapter(adapter)" setter, passing "adapter" constant
        recyclerView.setAdapter(adapter);

        // ?
        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable List<Term> terms) {
                adapter.setTerms(terms);
            }
        });

//        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseList = courseViewModel.getAllCourses();

        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courses) {
                termHasCourses = false;
//                List<Course> coursesInTerm = new ArrayList<>(); //create a new list
//                int intId = Integer.parseInt(id); //convert the class-level String variable id to an int so I can compare later on
                for (Course c : courses) { //each course in courses will have a turn to be c
                    if (c.getTermId() == termId) { //if the term id in the course matches the class-level id variable..
                        termHasCourses = true;
                        //                        coursesInTerm.add(c); //add the course to coursesInTerm
                    }
                }
//                adapter.setCourses(coursesInTerm);//set the courses in the adapter (this line used to be adapter.setCourses(courses)
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //////
//                List courseList = CourseDao
                //
                List<Term> termList = termViewModel.getAllTerms().getValue();
                int termId = termList.get(viewHolder.getAdapterPosition()).getTermId(); //termID was not being captured!

                int number = 0;

                try {
                    number = courseViewModel.getNumCoursesInTerm(termId);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (number == 0) {
                    termViewModel.delete(adapter.getTermAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(TermsActivity.this, "Term deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TermsActivity.this, "Term has courses!", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TermAdapter.OnItemClickListener() {

            // Calls onItemClick() method, so when clicked, loads DetailTermActivity with
            // all the Term variables using "intent.putExtra()" methods
            @Override
            public void onItemClick(Term term) {
                Intent intent = new Intent(TermsActivity.this, DetailTermActivity.class);
                // "term.getTermID" is initialized to "int id" variable to represent the rows from the
                // SQL-Lite database.
                termId = term.getTermId();
                String strId = Integer.toString(termId);
//                intent.putExtra(DetailTermActivity.EXTRA_ID, term.getTermId());

                intent.putExtra(DetailTermActivity.EXTRA_ID, strId);
                intent.putExtra(DetailTermActivity.EXTRA_TITLE, term.getTermTitle());
                intent.putExtra(DetailTermActivity.EXTRA_DATE_START, term.getTermDateStart());
                intent.putExtra(DetailTermActivity.EXTRA_DATE_END, term.getTermDateEnd());
                intent.putExtra(DetailTermActivity.EXTRA_STATUS_TERM, term.getTermStatus());
                startActivityForResult(intent, DETAIL_TERM_REQUEST);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK) {

            String title = data.getStringExtra(DetailTermActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(DetailTermActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(DetailTermActivity.EXTRA_DATE_END);
            String status_term = data.getStringExtra(DetailTermActivity.EXTRA_STATUS_TERM);

            Term term = new Term(title, date_start, date_end, status_term);
            termViewModel.insert(term);

            Toast.makeText(this, "Term saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == DETAIL_TERM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(DetailTermActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Term can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(DetailTermActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(DetailTermActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(DetailTermActivity.EXTRA_DATE_END);
            String status_term = data.getStringExtra(DetailTermActivity.EXTRA_STATUS_TERM);

            Term term = new Term(title, date_start, date_end, status_term);
            term.setTermId(id);
            termViewModel.update(term);

            Toast.makeText(this, "Term updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();


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
            case R.id.delete_all_terms:
                termViewModel.deleteAllTerms();
                Toast.makeText(this, "All terms deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}


