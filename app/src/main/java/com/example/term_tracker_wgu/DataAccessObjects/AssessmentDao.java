// AssessmentDao

package com.example.term_tracker_wgu.DataAccessObjects;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.term_tracker_wgu.Model.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao  {

    @Insert
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);

    @Query("DELETE FROM assessment_table")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessment_table")
    LiveData<List<Assessment>> getAllAssessments();
}
