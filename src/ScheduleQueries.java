/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author matt
 */
public class ScheduleQueries {
    
    private static Connection connection;
    private static ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
    private static PreparedStatement addSchedule;
    private static PreparedStatement getStudentScheduleList;
    private static PreparedStatement getStudentScheduleCount;
    private static PreparedStatement scheduledStudentsByCourse;
    private static PreparedStatement waitlistedStudentsByCourse;
    private static PreparedStatement updateSchedule;
    private static PreparedStatement dropByCourse;
    private static PreparedStatement dropStudentByCourse;
    private static ResultSet resultSet;
    
    /**
     * Commit a ScheduleEntry object to the Schedule table.
     * @param schedule ScheduleEntry to commit.
     */
    public static void addScheduleEntry(ScheduleEntry schedule) {
        connection = DBConnection.getConnection();
        try
        {
            // Attempt to commit ScheduleEntry to the Schedule table.
            addSchedule = connection.prepareStatement("insert into app.schedule (coursecode, semester, studentid, status, timestamp) values (?,?,?,?,?)");
            addSchedule.setString(1, schedule.getCourseCode());
            addSchedule.setString(2, schedule.getSemester());
            addSchedule.setString(3, schedule.getStudentID());
            addSchedule.setString(4, schedule.getStatus());
            addSchedule.setTimestamp(5, schedule.getTimestamp());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    /**
     * Get all of the Schedules for a student provided the semester and student id.
     * @param semester The semester for what schedules to return.
     * @param studentID The student that is scheduled for the courses.
     * @return List of ScheduleEntry.
     */
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        try
        {
            // Fetch schedules that match the specified StudentID and semester.
            getStudentScheduleList = connection.prepareStatement("select coursecode, semester, studentid, status, timestamp from app.schedule where studentid = (?) and semester = (?)");
            getStudentScheduleList.setString(1, studentID);
            getStudentScheduleList.setString(2, semester);
            resultSet = getStudentScheduleList.executeQuery();
            
            // For each schedule, create a ScheduleEntry and add it to `schedules`.
            while(resultSet.next()) {
                schedules.add(
                    new ScheduleEntry(
                        resultSet.getString(2),
                        resultSet.getString(1),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5)
                    )
                );
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return schedules;
    }
    
    /**
     * Get the number of students enrolled in a course.
     * @param semester The semester for the course.
     * @param courseCode The course to check enrollment
     * @return Integer quantifying number of enrolled students.
     */
    public static int getScheduledStudentCount(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int count = -1;
        try
        {
            getStudentScheduleCount = connection.prepareStatement("select count(*) from app.schedule where coursecode = (?) and semester = (?)");
            getStudentScheduleCount.setString(1, courseCode);
            getStudentScheduleCount.setString(2, semester);
            resultSet = getStudentScheduleCount.executeQuery();
            
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return count;
    }
    
    /**
     * Get the schedules for all students enrolled in a particular course
     * @param semester The semester the course takes place
     * @param courseCode The course to get schedules for
     * @return ArrayList of ScheduleEntry
     */
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        
        try
        {
            // Fetch schedules that match the specified StudentID and semester.
            scheduledStudentsByCourse = connection.prepareStatement("select coursecode, semester, studentid, status, timestamp from app.schedule where coursecode = (?) and semester = (?) and status = 'S' ORDER BY timestamp");
            scheduledStudentsByCourse.setString(2, semester);
            scheduledStudentsByCourse.setString(1, courseCode);
            resultSet = scheduledStudentsByCourse.executeQuery();
            
            // For each schedule, create a ScheduleEntry and add it to `schedules`.
            while(resultSet.next()) {
                schedules.add(
                    new ScheduleEntry(
                        resultSet.getString(2),
                        resultSet.getString(1),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5)
                    )
                );
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return schedules;
    }
    
    /**
     * Get all waitlisted students for a course.
     * @param semester The semester the course is being held.
     * @param courseCode The course to get schedules for.
     * @return ArrayList of ScheduleEntry
     */
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        
        try
        {
            // Fetch schedules that match the specified StudentID and semester.
            waitlistedStudentsByCourse = connection.prepareStatement("select coursecode, semester, studentid, status, timestamp from app.schedule where coursecode = (?) and semester = (?) and status = 'W' ORDER BY timestamp");
            waitlistedStudentsByCourse.setString(2, semester);
            waitlistedStudentsByCourse.setString(1, courseCode);
            resultSet = waitlistedStudentsByCourse.executeQuery();
            
            // For each schedule, create a ScheduleEntry and add it to `schedules`.
            while(resultSet.next()) {
                schedules.add(
                    new ScheduleEntry(
                        resultSet.getString(2),
                        resultSet.getString(1),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getTimestamp(5)
                    )
                );
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return schedules;
    }
    
    /**
     * Remove a student from a specified course.
     * @param semester The semester the course is held.
     * @param studentID The ID of the student to remove.
     * @param courseCode The course to remove the student from.
     */
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode) {
        connection = DBConnection.getConnection();
        
        try
        {
            // Fetch schedules that match the specified StudentID and semester.
            dropStudentByCourse = connection.prepareStatement("DELETE from app.schedule where coursecode = (?) and semester = (?) and studentid = (?)");
            dropStudentByCourse.setString(2, semester);
            dropStudentByCourse.setString(1, courseCode);
            dropStudentByCourse.setString(3, studentID);
            dropStudentByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    /**
     * Remove all schedules for a particular course.
     * @param semester The semester the course is being held.
     * @param courseCode The course to drop schedules for.
     */
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        
        try
        {
            // Fetch schedules that match the specified StudentID and semester.
            dropByCourse = connection.prepareStatement("DELETE FROM app.schedule WHERE coursecode = (?) and semester = (?)");
            dropByCourse.setString(2, semester);
            dropByCourse.setString(1, courseCode);
            dropByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    /**
     * Change a student's schedule status in the Schedule table.
     * @param semester The semester the schedule is for.
     * @param entry The ScheduleEntry object to update.
     */
    public static void updateScheduleEntry(String semester, ScheduleEntry entry) {
        connection = DBConnection.getConnection();
        if (entry.getStatus().equals("S")) {
            entry.setStatus("W");
        }
        else {
            entry.setStatus("S");
        }
        
        try
        {
            // Fetch schedules that match the specified StudentID and semester.
            updateSchedule = connection.prepareStatement("UPDATE app.schedule SET status = (?) WHERE semester = (?) and coursecode = (?) and studentid = (?)");
            updateSchedule.setString(1, entry.getStatus());
            updateSchedule.setString(2, entry.getSemester());
            updateSchedule.setString(3, entry.getCourseCode());
            updateSchedule.setString(4, entry.getStudentID());
            updateSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}
