# My Personal Project

## Project Background

This personal project will be a "Canvas like" application for teachers, TA's, and students to submit/ mark homeworks. 
The project will include structures like classes to indicate the grouping of students & TA's. It will also include different interfaces for different roles.

Students, TA's, and teachers will use this application.

I want to make this project as I work as a tutor, and the place that I work at still utilizes emails & Google Drive for students to submit their homeworks.

## User Stories

As a Teacher, I want to:
- Add assignments to classes
- View assignments of classes
- Change assignments of classes
- Delete assignments of classes

- Save assignment information (Save newly created assignments)
- Load from previously created assignments

### Instructions for Grader
Login with "Teacher" and "pw"
Click on Create Assignment, select class and fill in details to create assignment
Click on Change Assignment, select class and assignment, fill in details to change assignment
Click on View Assignment, select class and assignment to view the details of the assignment
Click on Save/ Load Assignment, assignment data will be loaded from JSON

The background image is at the beginning of Teacher Login

### Phase 4: Task 2
Thu Apr 04 17:45:07 PDT 2024: Teacher successfully logged in.\
Thu Apr 04 17:45:24 PDT 2024: Teacher created a new Assignment Assignment 1 for CPSC 110\
Thu Apr 04 17:45:36 PDT 2024: Loaded current database from JSON files

### Phase 4: Task 3
I would change the SwingUI class to reduce the number of lines there are in each user interface. Currently, there are a 
lot of duplicated code that can be reduced by abstracting some portions of the code. I can also split the SwingUI class
into multiple classes to reduce the number of code for each class so that it is easier to debug.

Inside the model package, I can refactor the code so that Class has a bidirectional relationship with the User class,
instead of having to keep a list of Students, Teachers, and TAs. Similarly, I reduce the dependencies Database have by 
storing all users in one list instead of three separate lists for Students, Teachers, and TAs.