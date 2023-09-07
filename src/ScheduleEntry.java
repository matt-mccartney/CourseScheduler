/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Timestamp;
import java.time.Instant;

/**
 *
 * @author matt
 */
public class ScheduleEntry {
    
    private final String semester;
    private final String courseCode;
    private final String studentID;
    private String status;
    private final Timestamp timestamp;
    
    public ScheduleEntry(String semester, String courseCode, String studentID, String status, Timestamp timestamp) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStatus(String status) {
        if (!(status.equals("S") || status.equals("W"))) {
            return;
        }
        this.status = status;
    }
    
    public String getStatus() {
        return status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
    
}
