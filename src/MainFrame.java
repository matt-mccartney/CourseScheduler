
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author acv
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    private String currentSemester;
    private String author;
    private String project;

    public MainFrame() {
        initComponents();
        checkData();
        rebuildSemesterComboBoxes();
        rebuildCourseComboBoxes();
        rebuildStudentComboBoxes();
        rebuildCourseTable();
    }

    /**
     *  Updates the table that displays the courses for each semester. Called
     *  upon program start, changing semester, and upon request.
     */
    public void rebuildCourseTable() {
        ArrayList<CourseEntry> courses = CourseQueries.getAllCourses(currentSemester);
        DefaultTableModel model = (DefaultTableModel) displayCoursesTable.getModel();
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for(int i = rowCount - 1; i >=0; i--)
        {
           model.removeRow(i); 
        }
        for (CourseEntry course: courses) {
            model.addRow(new Object[]{course.getCourseCode(), course.getDescription(), course.getSeats()});
        }
    }

    /**
     * Builds the table for displaying a students schedule. Called upon request
     * from the "Display Student" panel.
     * @param student The StudentEntry object for whom to display courses.
     */
    public void rebuildDisplayStudentTable(StudentEntry student) {
        ArrayList<ScheduleEntry> courses = ScheduleQueries.getScheduleByStudent(currentSemester, student.getStudentID());
        DefaultTableModel model = (DefaultTableModel) displayStudentTable.getModel();
        int rowCount = model.getRowCount();
        //Remove rows one by one from the end of the table
        for(int i = rowCount - 1; i >=0; i--)
        {
           model.removeRow(i); 
        }
        for (ScheduleEntry course: courses) {
            model.addRow(new Object[]{course.getCourseCode(), course.getStatus().equals("S") ? "Scheduled" : "Waitlisted"});
        }
    }

    /**
    * Updates the table that displays the list of students who are scheduled or
    * waitlisted for a selected course in the current semester.
    */
    public void rebuildDisplayCourseListOfStudentsTable() {
        String courseCode = (String) displayCourseListOfStudentsCombo.getSelectedItem();
        ArrayList<ScheduleEntry> scheduled = ScheduleQueries.getScheduledStudentsByCourse(currentSemester, courseCode);
        ArrayList<ScheduleEntry> waitlisted = ScheduleQueries.getWaitlistedStudentsByCourse(currentSemester, (String) displayCourseListOfStudentsCombo.getSelectedItem());
        DefaultTableModel modelS = (DefaultTableModel) scheduledStudentsCourseListTable.getModel();
        DefaultTableModel modelW = (DefaultTableModel) waitlistedStudentsCourseListTable.getModel();
        int rowCount = modelS.getRowCount();
        //Remove rows one by one from the end of the table
        for(int i = rowCount - 1; i >=0; i--)
        {
           modelS.removeRow(i); 
        }
        rowCount = modelW.getRowCount();
        //Remove rows one by one from the end of the table
        for(int i = rowCount - 1; i >=0; i--)
        {
           modelW.removeRow(i); 
        }
        StudentEntry student;
        for (ScheduleEntry schedule: scheduled) {
            student = StudentQueries.getStudent(schedule.getStudentID());
            modelS.addRow(new Object[]{student.getLastName(), student.getFirstName(), schedule.getStudentID()});
        }
        for (ScheduleEntry schedule: waitlisted) {
            student = StudentQueries.getStudent(schedule.getStudentID());
            modelW.addRow(new Object[]{student.getLastName(), student.getFirstName(), schedule.getStudentID()});
        }
    }
    
    /**
     * Refreshes the data in any JComboBox that displays the saved semesters
     * from the database.
     */
    public void rebuildSemesterComboBoxes() {
        ArrayList<String> semesters = SemesterQueries.getSemesterList();
        currentSemesterComboBox.setModel(new javax.swing.DefaultComboBoxModel(semesters.toArray()));
        if (semesters.size() > 0) {
            currentSemesterLabel.setText(semesters.get(0));
            currentSemester = semesters.get(0);
        } else {
            currentSemesterLabel.setText("None, add a semester.");
            currentSemester = "None";
        }
    }
    
    /**
     * Refreshes the data in any JComboBox that displays the saved courses
     * from the database. Called upon modifying the course list.
     */
    public void rebuildCourseComboBoxes() {
        ArrayList<String> courses = CourseQueries.getAllCourseCodes(currentSemester);
        scheduleCourseComboBox.setModel(new javax.swing.DefaultComboBoxModel(courses.toArray()));
        adminDropCourseCombo.setModel(new javax.swing.DefaultComboBoxModel(courses.toArray()));
        displayCourseListOfStudentsCombo.setModel(new javax.swing.DefaultComboBoxModel(courses.toArray()));
        studentDropCourseCourseCombo.setModel(new javax.swing.DefaultComboBoxModel(courses.toArray()));
    }
    
    /**
     * Refreshes the data in any JComboBox that displays the saved students
     * from the database. Called upon modifying enrolled students.
     */
    public void rebuildStudentComboBoxes() {
        ArrayList<StudentEntry> students = StudentQueries.getAllStudents();
        scheduleStudentComboBox.setModel(new javax.swing.DefaultComboBoxModel(students.toArray()));
        displayStudentComboBox.setModel(new javax.swing.DefaultComboBoxModel(students.toArray()));
        adminDropStudentCombo.setModel(new javax.swing.DefaultComboBoxModel(students.toArray()));
        studentDropCourseStudentCombo.setModel(new javax.swing.DefaultComboBoxModel(students.toArray()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        dropStudentPanel = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        semesterNameLabel = new javax.swing.JLabel();
        semesterNameTextfield = new javax.swing.JTextField();
        addSemesterSubmitButton = new javax.swing.JButton();
        addSemesterStatusLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        courseCode = new javax.swing.JLabel();
        courseDescription = new javax.swing.JLabel();
        courseSeats = new javax.swing.JLabel();
        courseCodeTextfield = new javax.swing.JTextField();
        courseDescriptionTextfield = new javax.swing.JTextField();
        addCourseButton = new javax.swing.JButton();
        courseSeatsSpinner = new javax.swing.JSpinner();
        setCourseStatusLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        studentID = new javax.swing.JLabel();
        studentFirstName = new javax.swing.JLabel();
        studentLastName = new javax.swing.JLabel();
        studentIDTextField = new javax.swing.JTextField();
        studentFirstNameTextField = new javax.swing.JTextField();
        studentLastNameTextField = new javax.swing.JTextField();
        submitStudentButton = new javax.swing.JButton();
        setStudentStatusLabel = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        displayCourseListOfStudentsCombo = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        scheduledStudentsCourseListTable = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        displayCourseListOfStudentsBtn = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        waitlistedStudentsCourseListTable = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        adminDropStudentCombo = new javax.swing.JComboBox<>();
        adminDropStudentBtn = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        adminDropStudentText = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        adminDropCourseCombo = new javax.swing.JComboBox<>();
        adminDropCourseBtn = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        adminDropCourseText = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        displayStudentPanel = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        displayCoursesTable = new javax.swing.JTable();
        displayCoursesButton = new javax.swing.JButton();
        addScheduleStatusLabel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        scheduleCourseComboBox = new javax.swing.JComboBox<>();
        scheduleStudentComboBox = new javax.swing.JComboBox<>();
        addScheduleButton = new javax.swing.JButton();
        scheduleCourseStatusLabel = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        displayStudentComboBox = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        displayStudentTable = new javax.swing.JTable();
        displayStudentButton = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        studentDropCourseStudentCombo = new javax.swing.JComboBox<>();
        studentDropCourseCourseCombo = new javax.swing.JComboBox<>();
        studentDropCourseBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        studentDropCourseText = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        currentSemesterLabel = new javax.swing.JLabel();
        currentSemesterComboBox = new javax.swing.JComboBox<>();
        changeSemesterButton = new javax.swing.JButton();
        aboutButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Course Scheduler");

        semesterNameLabel.setText("Semester Name:");

        semesterNameTextfield.setColumns(20);

        addSemesterSubmitButton.setText("Submit");
        addSemesterSubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSemesterSubmitButtonActionPerformed(evt);
            }
        });

        addSemesterStatusLabel.setText("                                                   ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addSemesterStatusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(addSemesterSubmitButton)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(semesterNameLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(semesterNameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(397, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(semesterNameLabel)
                    .addComponent(semesterNameTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addSemesterSubmitButton)
                .addGap(18, 18, 18)
                .addComponent(addSemesterStatusLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dropStudentPanel.addTab("Add Semester", jPanel3);

        courseCode.setText("Course Code:");

        courseDescription.setText("Course Description:");

        courseSeats.setText("Course Seats:");

        courseDescriptionTextfield.setColumns(30);

        addCourseButton.setText("Submit");
        addCourseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCourseButtonActionPerformed(evt);
            }
        });

        setCourseStatusLabel.setText(" ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(setCourseStatusLabel))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addCourseButton)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(courseCode)
                                    .addComponent(courseDescription)
                                    .addComponent(courseSeats))
                                .addGap(32, 32, 32)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(courseCodeTextfield, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                    .addComponent(courseDescriptionTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(courseSeatsSpinner))))))
                .addContainerGap(491, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseCode)
                    .addComponent(courseCodeTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseDescription)
                    .addComponent(courseDescriptionTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseSeats)
                    .addComponent(courseSeatsSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addCourseButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(setCourseStatusLabel)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        dropStudentPanel.addTab("Add Course", jPanel4);

        studentID.setText("Student ID:");

        studentFirstName.setText("First Name:");

        studentLastName.setText("Last Name:");

        studentLastNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentLastNameTextFieldActionPerformed(evt);
            }
        });

        submitStudentButton.setText("Submit");
        submitStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitStudentButtonActionPerformed(evt);
            }
        });

        setStudentStatusLabel.setText(" ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(setStudentStatusLabel))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(submitStudentButton)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(studentLastName)
                                    .addGap(32, 32, 32)
                                    .addComponent(studentLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(studentFirstName)
                                        .addComponent(studentID))
                                    .addGap(32, 32, 32)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(studentFirstNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                        .addComponent(studentIDTextField)))))))
                .addGap(539, 539, 539))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studentID)
                    .addComponent(studentIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studentFirstName)
                    .addComponent(studentFirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studentLastName)
                    .addComponent(studentLastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(submitStudentButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(setStudentStatusLabel)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        dropStudentPanel.addTab("Add Student", jPanel5);

        jLabel8.setText("Select Course:");

        displayCourseListOfStudentsCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        scheduledStudentsCourseListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Last Name", "First Name", "Student ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(scheduledStudentsCourseListTable);

        jLabel9.setText("Scheduled Students:");

        jLabel10.setText("Waitlisted Students:");

        displayCourseListOfStudentsBtn.setText("Display");
        displayCourseListOfStudentsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayCourseListOfStudentsBtnActionPerformed(evt);
            }
        });

        waitlistedStudentsCourseListTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Last Name", "First Name", "Student ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(waitlistedStudentsCourseListTable);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(displayCourseListOfStudentsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(displayCourseListOfStudentsBtn))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(140, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(displayCourseListOfStudentsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(displayCourseListOfStudentsBtn))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        dropStudentPanel.addTab("Display Course List of Students", jPanel8);

        jLabel11.setText("Select Student:");

        adminDropStudentCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        adminDropStudentBtn.setText("Drop Student");
        adminDropStudentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminDropStudentBtnActionPerformed(evt);
            }
        });

        adminDropStudentText.setColumns(20);
        adminDropStudentText.setRows(5);
        jScrollPane4.setViewportView(adminDropStudentText);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(adminDropStudentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(adminDropStudentBtn)))
                .addContainerGap(308, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(adminDropStudentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminDropStudentBtn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        dropStudentPanel.addTab("Drop Student", jPanel9);

        jLabel12.setText("Select Course:");

        adminDropCourseCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        adminDropCourseBtn.setText("Drop Course");
        adminDropCourseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminDropCourseBtnActionPerformed(evt);
            }
        });

        adminDropCourseText.setColumns(20);
        adminDropCourseText.setRows(5);
        jScrollPane7.setViewportView(adminDropCourseText);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(adminDropCourseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(adminDropCourseBtn)))
                .addContainerGap(308, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(adminDropCourseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminDropCourseBtn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        dropStudentPanel.addTab("Drop Course", jPanel10);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dropStudentPanel)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(dropStudentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Admin", jPanel1);

        displayCoursesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Course Code", "Description", "Seats"
            }
        ));
        jScrollPane1.setViewportView(displayCoursesTable);

        displayCoursesButton.setText("Display");
        displayCoursesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayCoursesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(displayCoursesButton)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 306, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(displayCoursesButton)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        displayStudentPanel.addTab("Display Courses", jPanel6);

        jLabel4.setText("Select Course:");

        jLabel5.setText("Select Student:");

        scheduleCourseComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        scheduleStudentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        addScheduleButton.setText("Submit");
        addScheduleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addScheduleButtonActionPerformed(evt);
            }
        });

        scheduleCourseStatusLabel.setText(" ");

        javax.swing.GroupLayout addScheduleStatusLabelLayout = new javax.swing.GroupLayout(addScheduleStatusLabel);
        addScheduleStatusLabel.setLayout(addScheduleStatusLabelLayout);
        addScheduleStatusLabelLayout.setHorizontalGroup(
            addScheduleStatusLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addScheduleStatusLabelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addScheduleStatusLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addScheduleStatusLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(addScheduleButton)
                        .addGroup(addScheduleStatusLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(addScheduleStatusLabelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(scheduleCourseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(addScheduleStatusLabelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(14, 14, 14)
                                .addComponent(scheduleStudentComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(scheduleCourseStatusLabel))
                .addContainerGap(535, Short.MAX_VALUE))
        );
        addScheduleStatusLabelLayout.setVerticalGroup(
            addScheduleStatusLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addScheduleStatusLabelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addScheduleStatusLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(scheduleCourseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addScheduleStatusLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(scheduleStudentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(addScheduleButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scheduleCourseStatusLabel)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        displayStudentPanel.addTab("Schedule Courses", addScheduleStatusLabel);

        jLabel6.setText("Select Student:");

        displayStudentComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        displayStudentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Course Code", "Status"
            }
        ));
        jScrollPane2.setViewportView(displayStudentTable);

        displayStudentButton.setText("Display");
        displayStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayStudentButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(14, 14, 14)
                        .addComponent(displayStudentComboBox, 0, 113, Short.MAX_VALUE)
                        .addGap(542, 542, 542))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(displayStudentButton)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(displayStudentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(displayStudentButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        displayStudentPanel.addTab("Display Student", jPanel7);

        jLabel3.setText("Select Student:");

        jLabel7.setText("Select Course:");

        studentDropCourseStudentCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        studentDropCourseCourseCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        studentDropCourseBtn.setText("Drop Course");
        studentDropCourseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                studentDropCourseBtnActionPerformed(evt);
            }
        });

        studentDropCourseText.setColumns(20);
        studentDropCourseText.setRows(5);
        jScrollPane3.setViewportView(studentDropCourseText);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(studentDropCourseBtn)
                        .addGroup(jPanel11Layout.createSequentialGroup()
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel7))
                            .addGap(28, 28, 28)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(studentDropCourseCourseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(studentDropCourseStudentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(408, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(studentDropCourseStudentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(studentDropCourseCourseCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(studentDropCourseBtn)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );

        displayStudentPanel.addTab("Drop Course", jPanel11);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayStudentPanel)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayStudentPanel)
        );

        jTabbedPane1.addTab("Student", jPanel2);

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 16)); // NOI18N
        jLabel2.setText("Current Semester: ");

        currentSemesterLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 16)); // NOI18N
        currentSemesterLabel.setText("           ");

        currentSemesterComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        changeSemesterButton.setText("Change Semester");
        changeSemesterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeSemesterButtonActionPerformed(evt);
            }
        });

        aboutButton.setText("About");
        aboutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currentSemesterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(currentSemesterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(changeSemesterButton)
                                .addGap(31, 31, 31)
                                .addComponent(aboutButton)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(currentSemesterLabel)
                    .addComponent(currentSemesterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeSemesterButton)
                    .addComponent(aboutButton))
                .addGap(24, 24, 24)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action to perform when a new semester is added to the Semester table. Adds
     * semester to JComboBox and alerts user.
     * @param evt The button click event.
     */
    private void addSemesterSubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSemesterSubmitButtonActionPerformed
        String semester = semesterNameTextfield.getText();
        
        SemesterQueries.addSemester(semester);
        addSemesterStatusLabel.setText("Semester " + semester + " has been added.");
        
        // Add semester to JComboBox objects so it can be utilized.
        rebuildSemesterComboBoxes();
    }//GEN-LAST:event_addSemesterSubmitButtonActionPerformed

    /**
     * Action to perform when the about button is clicked. Displays a message
     * dialog containing program information.
     * @param evt The button click event.
     */
    private void aboutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutButtonActionPerformed
        // display about information.
        JOptionPane.showMessageDialog(null, "Author: " + author + " Project: " + project);
    }//GEN-LAST:event_aboutButtonActionPerformed

    /**
     * Action to perform when the submit button is clicked on the Schedule
     * Course panel. Either enrolls or wait-lists a student depending on seat
     * availability, adding them to the Schedule table.
     * @param evt The button click event.
     */
    private void addScheduleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addScheduleButtonActionPerformed
        String courseCode = (String) scheduleCourseComboBox.getSelectedItem();
        StudentEntry student = (StudentEntry) scheduleStudentComboBox.getSelectedItem();
        
        // Calculate course availability by getting # of enrolled students and total seats.
        int availSeats = CourseQueries.getCourseSeats(currentSemester, courseCode);
        int currentEnrollment = ScheduleQueries.getScheduledStudentCount(currentSemester, courseCode);
        String status = availSeats > currentEnrollment ? "S" : "W";
        
        // Create ScheduleEntry object and commit it to the database.
        ScheduleEntry schedule = new ScheduleEntry(
                currentSemester,
                courseCode,
                student.getStudentID(),
                status,
                new Timestamp(System.currentTimeMillis())
        );
        ScheduleQueries.addScheduleEntry(schedule);
        
        // Alert user that commit was successful.
        scheduleCourseStatusLabel.setText("%s has been %s for %s.".formatted(student, status.equals("S") ? "scheduled" : "waitlisted", courseCode));
    }//GEN-LAST:event_addScheduleButtonActionPerformed

    /**
     * Renders the appropriate table for the selected student, displaying all of
     * their courses and the status of their enrollment via a JTable.
     * @param evt The button click event.
     */
    private void displayStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayStudentButtonActionPerformed
        StudentEntry student = (StudentEntry) displayStudentComboBox.getSelectedItem();
        rebuildDisplayStudentTable(student);
    }//GEN-LAST:event_displayStudentButtonActionPerformed

    /**
     * The action to perform when the submit button is clicked on the Add Course
     * panel. This adds the course to the Course table and updates any necessary
     * JComboBox objects.
     * @param evt The button click event.
     */
    private void addCourseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCourseButtonActionPerformed
        String courseCode = courseCodeTextfield.getText();
        String courseDesc = courseDescriptionTextfield.getText();
        int courseSeats = (int) courseSeatsSpinner.getValue();
        
        CourseQueries.addCourse(new CourseEntry(currentSemester, courseCode, courseDesc, courseSeats));
        setCourseStatusLabel.setText("Course %s has been added.".formatted(courseCode));
 
        // Add new course to JComboBox so it can be utilized.
        rebuildCourseComboBoxes();
        // Update Display Courses tab
        rebuildCourseTable();
    }//GEN-LAST:event_addCourseButtonActionPerformed

    /**
     * The action to perform when the Change Semester button is clicked. This
     * updates `currentSemester` and rebuilds any necessary JComboBoxes.
     * @param evt The button click event.
     */
    private void changeSemesterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeSemesterButtonActionPerformed
        currentSemester = (String) currentSemesterComboBox.getSelectedItem();
        rebuildCourseComboBoxes();
        rebuildCourseTable();
    }//GEN-LAST:event_changeSemesterButtonActionPerformed

    /**
     * This action is performed when the Display button is called on the Display
     * Courses tab. It rebuilds the table to show available courses for the
     * semester.
     * @param evt The button click event.
     */
    private void displayCoursesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayCoursesButtonActionPerformed
        rebuildCourseTable();
    }//GEN-LAST:event_displayCoursesButtonActionPerformed

    /**
     * Action to perform when the submit button is clicked on the Add Student
     * panel. Adds new student to Student table and alerts user.
     * @param evt The button click event.
     */
    private void submitStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitStudentButtonActionPerformed
        String studentID = studentIDTextField.getText();
        String fName = studentFirstNameTextField.getText();
        String lName = studentLastNameTextField.getText();

        StudentQueries.addStudent(new StudentEntry(studentID, fName, lName));
        setStudentStatusLabel.setText("Student %s %s has been added.".formatted(fName, lName));

        // Update JComboBox objects to include newly added student.
        rebuildStudentComboBoxes();
    }//GEN-LAST:event_submitStudentButtonActionPerformed

    private void studentLastNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentLastNameTextFieldActionPerformed

    }//GEN-LAST:event_studentLastNameTextFieldActionPerformed

    private void displayCourseListOfStudentsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayCourseListOfStudentsBtnActionPerformed
        rebuildDisplayCourseListOfStudentsTable();
    }//GEN-LAST:event_displayCourseListOfStudentsBtnActionPerformed

    /**
     * Removes a selected student from all schedules and waitlists, updates the
     * UI to reflect changes, and deletes the student from the system.
     *
     * @param evt An action event that occurred on the adminDropStudentBtn GUI
     * button.
     */
    private void adminDropStudentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminDropStudentBtnActionPerformed
        ArrayList<ScheduleEntry> schedules;
        ArrayList<ScheduleEntry> waitlisted;
        StudentEntry student = (StudentEntry) adminDropStudentCombo.getSelectedItem();
        String msg = "%s %s has been dropped from the list of students.\n\n".formatted(student, student.getStudentID());
        // Iterate over each semester to fully remove Student
        for (String semester: SemesterQueries.getSemesterList()) {
            schedules = ScheduleQueries.getScheduleByStudent(semester, student.getStudentID());
            msg = msg.concat("For Semester: " + semester + "\n");
            // For each schedule in the semester, remove the Student and bump waitlist if needed.
            for (ScheduleEntry schedule: schedules) {
                msg = msg.concat("%s %s has been dropped from %s\n".formatted(student, student.getStudentID(), schedule.getCourseCode()));
                ScheduleQueries.dropStudentScheduleByCourse(semester, student.getStudentID(), schedule.getCourseCode());
                waitlisted = ScheduleQueries.getWaitlistedStudentsByCourse(semester, schedule.getCourseCode());
                if (waitlisted.size() > 0) {
                    ScheduleQueries.updateScheduleEntry(semester, waitlisted.get(0));
                    msg = msg.concat("%s %s has been scheduled into %s".formatted(StudentQueries.getStudent(waitlisted.get(0).getStudentID()), waitlisted.get(0).getStudentID(), schedule.getCourseCode()));
                }
            }
            msg = msg.concat("\n");
        }
        // Update UI to represent changes
        adminDropStudentText.setText(msg);
        StudentQueries.dropStudent(student.getStudentID());
        rebuildStudentComboBoxes();
    }//GEN-LAST:event_adminDropStudentBtnActionPerformed

    /**
    * Drops a selected course for a selected student, updates the schedule and waitlist accordingly, 
    * and updates the UI to reflect changes.
    *
    * @param evt An action event that occurred on the studentDropCourseBtn GUI button.
    */
    private void studentDropCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_studentDropCourseBtnActionPerformed
        ArrayList<ScheduleEntry> waitlisted;
        StudentEntry student = (StudentEntry) studentDropCourseStudentCombo.getSelectedItem();
        String courseCode = (String) studentDropCourseCourseCombo.getSelectedItem();
        waitlisted = ScheduleQueries.getWaitlistedStudentsByCourse(currentSemester, courseCode);
        String msg = "%s %s has been dropped from %s\n".formatted(student, student.getStudentID(), courseCode);
        ScheduleQueries.dropStudentScheduleByCourse(currentSemester, student.getStudentID(), courseCode);
        // If waitlist is not empty, bump someone into the course.
        if (waitlisted.size() > 0) {
            ScheduleQueries.updateScheduleEntry(currentSemester, waitlisted.get(0));
            msg = msg.concat("%s %s has been scheduled into %s".formatted(StudentQueries.getStudent(waitlisted.get(0).getStudentID()), waitlisted.get(0).getStudentID(), courseCode));
        }
        // Update UI to reflect changes
        studentDropCourseText.setText(msg);
    }//GEN-LAST:event_studentDropCourseBtnActionPerformed

    /**
    * Drops the selected course from the current semester's schedule, removes it from the database, 
    * and updates the UI to reflect the changes. Additionally, this method generates a message indicating 
    * which students were dropped from the course and updates the UI with the message.
    *
    * @param evt An action event that occurred on the adminDropCourseBtn GUI button.
    */
    private void adminDropCourseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminDropCourseBtnActionPerformed
        String course = (String) adminDropCourseCombo.getSelectedItem();
        ArrayList<ScheduleEntry> scheduled = ScheduleQueries.getScheduledStudentsByCourse(currentSemester, course);
        ArrayList<ScheduleEntry> waitlisted = ScheduleQueries.getWaitlistedStudentsByCourse(currentSemester, course);
        ScheduleQueries.dropScheduleByCourse(currentSemester, course);
        CourseQueries.dropCourse(currentSemester, course);
        
        String msg = "Scheduled students dropped from the course:";
        for (ScheduleEntry entry: scheduled) {
            msg = msg.concat("\n%s".formatted(StudentQueries.getStudent(entry.getStudentID())));
        }
        msg = msg.concat("\n\nWaitlisted students dropped from the course:");
        for (ScheduleEntry entry: waitlisted) {
            msg = msg.concat("\n%s".formatted(StudentQueries.getStudent(entry.getStudentID())));
        }
        // Update UI to reflect changes
        adminDropCourseText.setText(msg);
        rebuildCourseComboBoxes();
    }//GEN-LAST:event_adminDropCourseBtnActionPerformed

    private void checkData() {
        try {
            FileReader reader = new FileReader("xzq789yy.txt");
            BufferedReader breader = new BufferedReader(reader);

            String encodedAuthor = breader.readLine();
            String encodedProject = breader.readLine();
            byte[] decodedAuthor = Base64.getDecoder().decode(encodedAuthor);
            author = new String(decodedAuthor);
            byte[] decodedProject = Base64.getDecoder().decode(encodedProject);
            project = new String(decodedProject);
            reader.close();

        } catch (FileNotFoundException e) {
            //get user info and create file
            author = JOptionPane.showInputDialog("Enter your first and last name.");
            project = "Course Scheduler Spring 2023";

            //write data to the data file.
            try {
                FileWriter writer = new FileWriter("xzq789yy.txt", true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                // encode the output data.
                String encodedAuthor = Base64.getEncoder().encodeToString(author.getBytes());

                bufferedWriter.write(encodedAuthor);
                bufferedWriter.newLine();

                String encodedProject = Base64.getEncoder().encodeToString(project.getBytes());
                bufferedWriter.write(encodedProject);

                bufferedWriter.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.exit(1);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aboutButton;
    private javax.swing.JButton addCourseButton;
    private javax.swing.JButton addScheduleButton;
    private javax.swing.JPanel addScheduleStatusLabel;
    private javax.swing.JLabel addSemesterStatusLabel;
    private javax.swing.JButton addSemesterSubmitButton;
    private javax.swing.JButton adminDropCourseBtn;
    private javax.swing.JComboBox<String> adminDropCourseCombo;
    private javax.swing.JTextArea adminDropCourseText;
    private javax.swing.JButton adminDropStudentBtn;
    private javax.swing.JComboBox<String> adminDropStudentCombo;
    private javax.swing.JTextArea adminDropStudentText;
    private javax.swing.JButton changeSemesterButton;
    private javax.swing.JLabel courseCode;
    private javax.swing.JTextField courseCodeTextfield;
    private javax.swing.JLabel courseDescription;
    private javax.swing.JTextField courseDescriptionTextfield;
    private javax.swing.JLabel courseSeats;
    private javax.swing.JSpinner courseSeatsSpinner;
    private javax.swing.JComboBox<String> currentSemesterComboBox;
    private javax.swing.JLabel currentSemesterLabel;
    private javax.swing.JButton displayCourseListOfStudentsBtn;
    private javax.swing.JComboBox<String> displayCourseListOfStudentsCombo;
    private javax.swing.JButton displayCoursesButton;
    private javax.swing.JTable displayCoursesTable;
    private javax.swing.JButton displayStudentButton;
    private javax.swing.JComboBox<String> displayStudentComboBox;
    private javax.swing.JTabbedPane displayStudentPanel;
    private javax.swing.JTable displayStudentTable;
    private javax.swing.JTabbedPane dropStudentPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> scheduleCourseComboBox;
    private javax.swing.JLabel scheduleCourseStatusLabel;
    private javax.swing.JComboBox<String> scheduleStudentComboBox;
    private javax.swing.JTable scheduledStudentsCourseListTable;
    private javax.swing.JLabel semesterNameLabel;
    private javax.swing.JTextField semesterNameTextfield;
    private javax.swing.JLabel setCourseStatusLabel;
    private javax.swing.JLabel setStudentStatusLabel;
    private javax.swing.JButton studentDropCourseBtn;
    private javax.swing.JComboBox<String> studentDropCourseCourseCombo;
    private javax.swing.JComboBox<String> studentDropCourseStudentCombo;
    private javax.swing.JTextArea studentDropCourseText;
    private javax.swing.JLabel studentFirstName;
    private javax.swing.JTextField studentFirstNameTextField;
    private javax.swing.JLabel studentID;
    private javax.swing.JTextField studentIDTextField;
    private javax.swing.JLabel studentLastName;
    private javax.swing.JTextField studentLastNameTextField;
    private javax.swing.JButton submitStudentButton;
    private javax.swing.JTable waitlistedStudentsCourseListTable;
    // End of variables declaration//GEN-END:variables
}
