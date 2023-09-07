
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author matt
 */
public class CourseQueries {
    
    private static Connection connection;
    private static ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
    private static PreparedStatement addCourse;
    private static PreparedStatement dropCourse;
    private static PreparedStatement getCourseList;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement getCourseCodeList;
    private static ResultSet resultSet;
    
    /**
     * Get all courses for a particular semester.
     * @param semester the Semester to retrieve courses for.
     * @return List of courses for the requested semester.
     */
    public static ArrayList<CourseEntry> getAllCourses(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        try
        {
            getCourseList = connection.prepareStatement("select coursecode, semester, description, seats from app.course where semester = (?) order by coursecode");
            getCourseList.setString(1, semester);
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                courses.add(
                    new CourseEntry(
                        resultSet.getString(2),
                        resultSet.getString(1),
                        resultSet.getString(3),
                        resultSet.getInt(4)
                    )
                );
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return courses;
    }
    
    /**
     * Get a list of course codes for all courses during a semester.
     * @param semester The semester to return course codes for.
     * @return List of String representing course codes.
     */
    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try
        {
            getCourseCodeList = connection.prepareStatement("select coursecode from app.course where semester = (?) order by coursecode");
            getCourseCodeList.setString(1, semester);
            resultSet = getCourseCodeList.executeQuery();
            
            // Add each course code to the `courseCodes` list.
            while(resultSet.next())
            {
                courseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return courseCodes;
    }
    
    /**
     * Get the maximum amount of seats for a class.
     * @param semester Semester class is being held.
     * @param courseCode The class for which to get the number of seats
     * @return An integer representing the number of seats in a course.
     */
    public static int getCourseSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seats = -1;

        try
        {
            getCourseSeats = connection.prepareStatement("select seats from app.course where coursecode = '%s' and semester = '%s'".formatted(courseCode, semester));
            resultSet = getCourseSeats.executeQuery();
            
            // Ensure that the count entry exists and update `seats` if possible.
            if (resultSet.next()) {
                seats = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return seats;
    }
    
    /**
     * Add a course to the Course table.
     * @param course CourseEntry object to add.
     */
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        
        try
        {
            // Attempt to commit new course to Course table.
            addCourse = connection.prepareStatement("insert into app.course (coursecode, description, semester, seats) values (?,?,?,?)");
            addCourse.setString(1, course.getCourseCode());
            addCourse.setString(2, course.getDescription());
            addCourse.setString(3, course.getSemester());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    /**
     * Removes a course from the Course Table based on the semester and course code.
     * @param semester The semester the course is offered.
     * @param courseCode The code for the course being offered.
     */
    public static void dropCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        try {
            dropCourse = connection.prepareStatement("DELETE FROM app.course WHERE semester = ? AND coursecode = ?");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}
