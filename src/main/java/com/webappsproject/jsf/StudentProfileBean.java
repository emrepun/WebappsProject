/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.StudentService;
import com.webappsproject.entity.Project;
import com.webappsproject.entity.Student;
import java.io.Serializable;
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
public class StudentProfileBean implements Serializable {
    
    //declare properties.
    private Student student;
    
    //inject services.
    @EJB
    StudentService service;
    
    //get current context.
    FacesContext context = FacesContext.getCurrentInstance();
    
    @PostConstruct
    public void init() {
        //check student and project status to display informative message on view.
        this.student = service.getLoggedInStudent();
        Project project = this.student.getAssociatedProject();
        if (project != null) {
            if (project.getStatus() == Project.ProjectStatus.ACCEPTED) {
                context.addMessage(null, new FacesMessage("You are all set!"));
            } else if (project.getStatus() == Project.ProjectStatus.PROPOSED) {
                context.addMessage(null, new FacesMessage("Waiting for supervisor approval."));
            }
        } else {
            context.addMessage(null, new FacesMessage("You didn't apply for a project yet."));
        }
    }
    
    public StudentProfileBean() {
        
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    
    
}
