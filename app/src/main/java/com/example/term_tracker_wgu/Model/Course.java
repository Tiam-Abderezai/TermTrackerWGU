package com.example.term_tracker_wgu.Model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private int termId;
    private String courseTitle = "";
    private String courseDateStart = "";
    private String courseDateEnd = "";
    private String courseNote = "";
    private String courseStatus = "";
    private String courseMentor = "";
    private String courseMentorPhone = "";
    private String courseMentorEmail = "";

    public Course(int termId, String courseTitle, String courseDateStart, String courseDateEnd, String courseNote, String courseStatus, String courseMentor, String courseMentorPhone, String courseMentorEmail) {

        this.termId = termId;
        this.courseTitle = courseTitle;
        this.courseDateStart = courseDateStart;
        this.courseDateEnd = courseDateEnd;
        this.courseNote = courseNote;
        this.courseStatus = courseStatus;
        this.courseMentor = courseMentor;
        this.courseMentorPhone = courseMentorPhone;
        this.courseMentorEmail = courseMentorEmail;

    }

    public Course(int termId, int id, String courseTitle, String courseDateStart, String courseDateEnd, String courseNote, String courseStatus, String courseMentor, String courseMentorPhone, String courseMentorEmail) {

        this.termId = termId;
        this.courseId = id;
        this.courseTitle = courseTitle;
        this.courseDateStart = courseDateStart;
        this.courseDateEnd = courseDateEnd;
        this.courseNote = courseNote;
        this.courseStatus = courseStatus;
        this.courseMentor = courseMentor;
        this.courseMentorPhone = courseMentorPhone;
        this.courseMentorEmail = courseMentorEmail;
    }


    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getTermId() {
        return termId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseDateStart(String courseDateStart) {
        this.courseDateStart = courseDateStart;
    }

    public String getCourseDateEnd() {
        return courseDateEnd;
    }

    public void setCourseDateEnd(String courseDateEnd) {
        this.courseDateEnd = courseDateEnd;
    }

    public String getCourseDateStart() {
        return courseDateStart;
    }

    public void setCourseNote(String courseNotes) {
        this.courseNote = courseNotes;
    }

    public String getCourseNote() {
        return courseNote;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseMentor(String courseMentor) {
        this.courseMentor = courseMentor;
    }

    public String getCourseMentor() {
        return courseMentor;
    }
    public void setCourseMentorPhone(String courseMentorPhone) {
        this.courseMentorPhone = courseMentorPhone;
    }

    public String getCourseMentorPhone() {
        return courseMentorPhone;
    }
    public void setCourseMentorEmail(String courseMentorEmail) {
        this.courseMentorEmail = courseMentorEmail;
    }

    public String getCourseMentorEmail() {
        return courseMentorEmail;
    }


}