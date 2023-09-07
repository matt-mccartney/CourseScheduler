/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author matt
 */
public class CourseEntry {
    
    private final int seats;
    private final String semester;
    private final String courseCode;
    private final String description;
    
    public CourseEntry(String semester, String courseCode, String description, int seats) {
        this.semester = semester;
        this.description = description;
        this.courseCode = courseCode;
        this.seats = seats;
    }

    public int getSeats() {
        return seats;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDescription() {
        return description;
    }

}
