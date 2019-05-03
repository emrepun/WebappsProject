/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.LogoutService;
import com.webappsproject.ejb.ProjectApplicationReviewService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class SupervisorHomeBean implements Serializable {
    
    private String studentProposedNotification;
    private String ownedApplicationNotification;
    
    @EJB
    ProjectApplicationReviewService service; 
    
    @EJB
    LogoutService logoutService;
    
    @PostConstruct
    public void init() {
        //get currently logged-in supervisor and proposed applications amount.
        String supervisorID = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        int studentProposalApplicationAmount = service.getStudentProposedApplicationsForSupervisor(supervisorID).size();
        int ownedProjectApplicationAmount = service.getApplicationsForSupervisor(supervisorID).size();
        
        if (studentProposalApplicationAmount > 0) {
            studentProposedNotification = "You have " + studentProposalApplicationAmount + " proposals made by students, go to Student Proposals page to view.";
        } else {
            studentProposedNotification = "You dont have any student proposal notifications.";
        }
        
        if (ownedProjectApplicationAmount > 0) {
            ownedApplicationNotification = "You have " + ownedProjectApplicationAmount + " applications to your projects, go to Project Applications page to view.";
        } else {
            ownedApplicationNotification = "You dont have any applications to your projects.";
        }
    }
    
    public SupervisorHomeBean() {
        
    }

    public String getStudentProposedNotification() {
        return studentProposedNotification;
    }

    public void setStudentProposedNotification(String notification) {
        this.studentProposedNotification = notification;
    }

    public String getOwnedApplicationNotification() {
        return ownedApplicationNotification;
    }

    public void setOwnedApplicationNotification(String ownedApplicationNotification) {
        this.ownedApplicationNotification = ownedApplicationNotification;
    }
    
    public String logout() {
        return logoutService.logout();
    }
}
