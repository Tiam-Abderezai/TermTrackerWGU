// CourseAdaptor

package com.example.term_tracker_wgu.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.term_tracker_wgu.Model.Course;
import com.example.term_tracker_wgu.R;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {

    private List<Course> courses = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_items, parent, false);

        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        Course currentCourse = courses.get(position);
        holder.textViewTitle.setText(currentCourse.getCourseTitle());
        holder.textViewStartDate.setText(currentCourse.getCourseDateStart());
        holder.textViewEndDate.setText(currentCourse.getCourseDateEnd());
        holder.textViewNote.setText(currentCourse.getCourseNote());
        holder.textViewStatus.setText(currentCourse.getCourseStatus());
        holder.textViewMentor.setText(currentCourse.getCourseMentor());
        holder.textViewMentorPhone.setText(currentCourse.getCourseMentorPhone());
        holder.textViewMentorEmail.setText(currentCourse.getCourseMentorEmail());

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public Course getCourseAt(int position) {
        return courses.get(position);
    }

    class CourseHolder extends RecyclerView.ViewHolder {

//        private TextView textV
        private TextView textViewTitle;
        private TextView textViewStartDate;
        private TextView textViewEndDate;
        private TextView textViewNote;
        private TextView textViewStatus;
        private TextView textViewMentor;
        private TextView textViewMentorPhone;
        private TextView textViewMentorEmail;


        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStartDate = itemView.findViewById(R.id.text_view_date_start);
            textViewEndDate = itemView.findViewById(R.id.text_view_date_end);
            textViewNote = itemView.findViewById(R.id.text_view_note);
            textViewStatus = itemView.findViewById(R.id.text_view_status_course);
            textViewMentor = itemView.findViewById(R.id.text_view_mentor);
            textViewMentorPhone = itemView.findViewById(R.id.text_view_mentor_phone);
            textViewMentorEmail = itemView.findViewById(R.id.text_view_mentor_email);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(courses.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Course course);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
