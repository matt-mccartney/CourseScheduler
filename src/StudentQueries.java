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
public class StudentQueries {
    
    private static Connection connection;
    private static ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static PreparedStatement getStudentList;
    private static ResultSet resultSet;
    
    /**
     * Fetch all students from the Student table of the database.
     * @return List of students. Empty if no students found.
     */
    public static ArrayList<StudentEntry> getAllStudents() {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            // Fetch the students from our Student table.
            getStudentList = connection.prepareStatement("select studentid, firstname, lastname from app.student order by studentid");
            resultSet = getStudentList.executeQuery();
            
            // Create a StudentEntry object for each student in the Student table.
            while(resultSet.next())
            {
                students.add(
                    new StudentEntry(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                    )
                );
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return students;
    }
    
    /**
     * Add a student to the Student table.
     * @param student The StudentEntry object to add to our Student table.
     */
    public static void addStudent(StudentEntry student) {
        connection = DBConnection.getConnection();
        try
        {
            // Attempt to commit StudentEntry to the Student table.
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    /**
     * Retrieve a Student from the Student table as a StudentEntry type.
     * @param studentID The ID of the student
     * @return StudentEntry
     */
    public static StudentEntry getStudent(String studentID) {
        connection = DBConnection.getConnection();

        try {
            getStudent = connection.prepareStatement("SELECT firstname, lastname, studentid FROM app.student WHERE studentid = (?)");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            
            if (resultSet.next()) {
                return new StudentEntry(
                    resultSet.getString(3),
                    resultSet.getString(1),
                    resultSet.getString(2)
                );
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Removes a student from the Student Table based on their ID.
     * @param studentID The ID of the Student
     */
    public static void dropStudent(String studentID) {
        connection = DBConnection.getConnection();
        try {
            dropStudent = connection.prepareStatement("DELETE FROM app.student WHERE studentid = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}
