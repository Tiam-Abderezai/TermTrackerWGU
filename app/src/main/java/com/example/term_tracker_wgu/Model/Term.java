package com.example.term_tracker_wgu.Model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "term_table")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int termId;
    private String termTitle = "";
    private String termDateStart = "";
    private String termDateEnd = "";
    private String termStatus = "";

    public Term(String termTitle, String termDateStart, String termDateEnd, String termStatus) {

        this.termTitle = termTitle;
        this.termDateStart = termDateStart;
        this.termDateEnd = termDateEnd;
        this.termStatus = termStatus;
    }

    public Term(int id, String termTitle, String termDateStart, String termDateEnd, String termStatus) {

        this.termId = id;
        this.termTitle = termTitle;
        this.termDateStart = termDateStart;
        this.termDateEnd = termDateEnd;
        this.termStatus = termStatus;
    }


    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermDateStart(String termDateStart) {
        this.termDateStart = termDateStart;
    }

    public String getTermDateEnd() {
        return termDateEnd;
    }

    public void setTermDateEnd(String termDateEnd) {
        this.termDateEnd = termDateEnd;
    }

    public String getTermDateStart() {
        return termDateStart;
    }

    public void setTermStatus(String termStatus) {
        this.termStatus = termStatus;
    }

    public String getTermStatus() {
        return termStatus;
    }

}
