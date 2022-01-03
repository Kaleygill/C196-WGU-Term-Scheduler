package Entities;

import androidx.room.PrimaryKey;
import androidx.room.*;

@Entity(tableName="Assessment_Table")
public class AssessmentEntity {
    @PrimaryKey
    private int assessmentID;
    private String assessmentName;
    private String assessmentDate;
    private String assessmentType;
    private Integer courseID;


    public AssessmentEntity (int assessmentID, String assessmentName, String assessmentDate, String assessmentType, Integer courseID) {
        this.assessmentID = assessmentID;
        this.assessmentName = assessmentName;
        this.assessmentDate = assessmentDate;
        this.assessmentType = assessmentType;
        this.courseID = courseID;
    }



    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "assessmentID=" + assessmentID +
                ", assessmentName=" + assessmentName +
                ", assessmentDate=" + assessmentDate +
                ", assessmentType=" + assessmentType +
                ", courseID=" + courseID +
                '}';
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }


}
