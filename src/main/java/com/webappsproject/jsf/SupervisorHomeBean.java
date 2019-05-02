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
    
    private String notification;
    
    @EJB
    ProjectApplicationReviewService service; 
    
    @EJB
    LogoutService logoutService;
    
    @PostConstruct
    public void init() {
        //get currently logged-in supervisor and proposed applications amount.
        String supervisorID = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        int applicationAmount = service.getStudentProposedApplicationsForSupervisor(supervisorID).size();
        
        if (applicationAmount > 0) {
            notification = "You have " + applicationAmount + " proposals made by students, go to Student Proposals page to display.";
        } else {
            notification = "You dont have any notifications.";
        }   
    }
    
    public SupervisorHomeBean() {
        
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
    
    public String logout() {
        return logoutService.logout();
    }
}
