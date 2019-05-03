/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.StudentService;
import com.webappsproject.entity.Student;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class SupervisorOwnedStudentsBean {
    
    //declare properties.
    private List<Student> supervisedStudents;
    private String supervisorID;
    
    //inject services.
    @EJB
    StudentService studentService;        
    
    //get current context.
    FacesContext context = FacesContext.getCurrentInstance();
    
    @PostConstruct
    public void init() {
        //get supervisor id from current logged-in user.
        supervisorID = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        
        supervisedStudents = studentService.getStudentsForSupervisor(supervisorID);
        
        if (supervisedStudents.isEmpty()) {
            context.addMessage(null, new FacesMessage("You don't supervise any students."));
        }
    }
    
    public SupervisorOwnedStudentsBean() {
        
    }

    public List<Student> getSupervisedStudents() {
        return supervisedStudents;
    }

    public void setSupervisedStudents(List<Student> supervisedStudents) {
        this.supervisedStudents = supervisedStudents;
    }

    public String getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }
}
