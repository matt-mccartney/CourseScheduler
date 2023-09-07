Here is a draft README for the Course Scheduler project Part 2:

# Course Scheduler App

This is the second part of a course scheduling application for a college, built using Java and Derby database. It allows admins and students to schedule courses by semester.

## Features 

**Admin functions:**

- Add new semesters, courses, and students
- Display list of students enrolled in a course  
- Drop a student from all courses
- Drop a course from the schedule

**Student functions:**

- Schedule for courses if seats available
- Waitlist for courses that are full  
- View schedule for current semester
- Drop a course from their schedule

**Additional features:**

- Validations for all user inputs
- Database updates via SQL statements 
- Prepared statements used for variable substitution  
- Displays confirmations and changes made after each action
- Intuitive GUI with dropdowns and menus

## Usage

The main GUI class launches the application. 

Admins can add semesters, courses, and students before scheduling begins. To schedule a student in a course, the semester, student, and course must exist in the database. 

Students can view their schedule, drop courses, and get waitlisted for full classes.

See the included test scripts for example admin and student workflows.

## Database Design

The Derby database consists of the following tables:

- `Semester` - Contains the semester names
- `Course` - Stores course data like semester, code, title, seats  
- `Student` - Student information like ID, name
- `Schedule` - Enrollments with semester, student ID, and course code

Foreign keys relate the Schedule table to Semester, Student, and Course. 

## Implementation

Built using Java Swing for the GUI. Database access and CRUD operations handled via JDBC using SQL statements and prepared statements. 

Four model classes map to the database entities - `Semester`, `Course`, `Student`, and `Schedule`. The GUI and control logic is handled in `MainGUI` and other GUI forms.

## Installation

Import the project into NetBeans. Run the SQL script to create the empty database schema. Configure the JDBC connection in `MainGUI.java`.  
