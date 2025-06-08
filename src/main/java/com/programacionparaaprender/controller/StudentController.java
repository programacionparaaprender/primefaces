package com.programacionparaaprender.controller;

import com.luv2code.jsf.jdbc.Student;
import com.luv2code.jsf.jdbc.StudentDbUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

@ManagedBean
@ViewScoped
public class StudentController implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Student> students;
    private Student selectedStudent;
    private StudentDbUtil studentDbUtil;
    private Logger logger = Logger.getLogger(getClass().getName());
    
    @PostConstruct
    public void init() {
        try {
            students = new ArrayList<>();
            studentDbUtil = StudentDbUtil.getInstance();
            selectedStudent = new Student(); // Inicializa aquí
            loadStudents();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error initializing StudentController", ex);
            addErrorMessage("Error initializing controller: " + ex.getMessage());
        }
    }
    
    
    public void loadStudents() {
        logger.info("Loading students");
        
        try {
            // Get all students from database
            students = studentDbUtil.getStudents();
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error loading students", exc);
            addErrorMessage("Error loading students: " + exc.getMessage());
        }
    }
    public String addStudent() {
        if (selectedStudent == null) {
            addErrorMessage("No student selected");
            return null;
        }
        return addStudent(selectedStudent);
    }
    
    public String addStudent(Student theStudent) {
        logger.info("Adding student: " + theStudent);

        try {
            studentDbUtil.addStudent(theStudent);
            addSuccessMessage("Student added successfully");
            return "list-students?faces-redirect=true";
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error adding student", exc);
            addErrorMessage("Error adding student: " + exc.getMessage());
            return null;
        }
    }
    
    public String updateStudent() {
        if (selectedStudent == null) {
            addErrorMessage("No student selected");
            return null;
        }
        return updateStudent(selectedStudent);
    }
    
    public String updateStudent(Student theStudent) {
        logger.info("Updating student: " + theStudent);
        
        try {
            studentDbUtil.updateStudent(theStudent);
            addSuccessMessage("Student updated successfully");
            return "list-students?faces-redirect=true";
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error updating student: " + theStudent, exc);
            addErrorMessage("Error updating student: " + exc.getMessage());
            return null;
        }
    }
    
    public void deleteStudent(int studentId) {
        logger.info("Deleting student id: " + studentId);
        
        try {
        	Student student = studentDbUtil.getStudent(studentId);
            studentDbUtil.deleteStudent(studentId);
            students.remove(student);
            addSuccessMessage("Student deleted successfully");
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error deleting student id: " + studentId, exc);
            addErrorMessage("Error deleting student: " + exc.getMessage());
        }
    }
    
    public void deleteStudent(Student student) {
        logger.info("Deleting student id: " + student.getId());
        
        try {
            studentDbUtil.deleteStudent(student.getId());
            students.remove(student);
            addSuccessMessage("Student deleted successfully");
        } catch (Exception exc) {
            logger.log(Level.SEVERE, "Error deleting student id: " + student.getId(), exc);
            addErrorMessage("Error deleting student: " + exc.getMessage());
        }
    }
    
    public String prepareCreate() {
    	students = new ArrayList<>();
        selectedStudent = new Student();
        return "create-student?faces-redirect=true";
    }
    
    public String prepareUpdate(int studentId) {
        try {
            // Cargar los datos frescos desde la base de datos
            selectedStudent = studentDbUtil.getStudent(studentId);
            return "update-student?faces-redirect=true&includeViewParams=true";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error preparing update", e);
            addErrorMessage("Error preparing update");
            return null;
        }
    }
    
    
    public String prepareUpdate() {
        try {
            // Cargar los datos frescos desde la base de datos
            selectedStudent = studentDbUtil.getStudent(selectedStudent.getId());
            return "update-student?faces-redirect=true&includeViewParams=true";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error preparing update", e);
            addErrorMessage("Error preparing update");
            return null;
        }
    }
    
    public String prepareUpdate(Student student) {
        try {
            // Cargar los datos frescos desde la base de datos
            selectedStudent = studentDbUtil.getStudent(student.getId());
            return "update-student?faces-redirect=true&includeViewParams=true";
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error preparing update", e);
            addErrorMessage("Error preparing update");
            return null;
        }
    }
    
    
    // Helper methods for messages
    private void addErrorMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }
    
    private void addSuccessMessage(String message) {
        FacesContext.getCurrentInstance().addMessage(null, 
            new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }
    
    // Getters and Setters
    public List<Student> getStudents() {
        return students;
    }
    
    public Student getSelectedStudent() {
        return selectedStudent;
    }
    
    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }
}