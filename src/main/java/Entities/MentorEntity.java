package Entities;

import androidx.room.PrimaryKey;
import androidx.room.*;

@Entity(tableName = "mentor_table")
public class MentorEntity {
        @PrimaryKey
        private int mentorID;
        private String mentorName;
        private String mentorEmail;
        private String mentorPhone;
        private Integer courseID;

        @Override
        public String toString () {
            return "MentorEntity{" +
                    "mentorID=" + mentorID +
                    ", mentorName=" + mentorName +
                    ", mentorEmail=" + mentorEmail +
                    ", mentorPhone=" + mentorPhone +
                    ", courseID=" + courseID +
                    '}';
        }

        public MentorEntity(int mentorID, String mentorName, String mentorEmail, String mentorPhone, Integer courseID) {
            this.mentorID = mentorID;
            this.mentorName = mentorName;
            this.mentorEmail = mentorEmail;
            this.mentorPhone = mentorPhone;
            this.courseID = courseID;
        }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public String getMentorName() {
            return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorEmail() {
            return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public String getMentorPhone() {
            return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }
}
