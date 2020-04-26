//Assessment

package com.example.term_tracker_wgu.Model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment_table")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private int courseId;
    private String assessmentType = "";
    private String assessmentDueDate = "";
    private String assessmentStatus = "";

    public Assessment(int courseId, String assessmentType, String assessmentDueDate, String assessmentStatus) {

        this.courseId = courseId;
        this.assessmentType = assessmentType;
        this.assessmentDueDate = assessmentDueDate;
        this.assessmentStatus = assessmentStatus;
    }


    public Assessment(int courseId, int id, String assessmentType, String assessmentDueDate, String assessmentStatus) {

        this.courseId = courseId;
        this.assessmentId = id;
        this.assessmentType = assessmentType;
        this.assessmentDueDate = assessmentDueDate;
        this.assessmentStatus = assessmentStatus;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }


    public int getCourseId() {
        return courseId;
    }




    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentDueDate(String assessmentDueDate) {
        this.assessmentDueDate = assessmentDueDate;
    }

    public String getAssessmentDueDate() {
        return assessmentDueDate;
    }

    public void setAssessmentStatus(String assessmentStatus) {
        this.assessmentStatus = assessmentStatus;
    }

    public String getAssessmentStatus() {
        return assessmentStatus;
    }


}
