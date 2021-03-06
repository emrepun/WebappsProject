/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.ProjectApplicationReviewService;
import com.webappsproject.entity.Project;
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
public class SupervisorStudentProposedApplicationListBean {
    
    //declare properties.
    private List<Project> applications;
    private String selected;
    
    //inject services.
    @EJB
    ProjectApplicationReviewService applicationService;  
    
    //get current context.
    FacesContext context = FacesContext.getCurrentInstance();
    
    @PostConstruct
    public void init() {
        //get currently logged-in supervisor and proposed applications to that supervisor.
        String supervisorID = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        applications = applicationService.getStudentProposedApplicationsForSupervisor(supervisorID);
        if (!applications.isEmpty()) {
            selected = applications.get(0).getName();
            applicationService.setSelectedProjectApplication(selected);
        } else {
            selected = "";
            context.addMessage(null, new FacesMessage("There are no student project proposals."));
        }
    }
    
    public SupervisorStudentProposedApplicationListBean() {
        
    }

    public List<Project> getApplications() {
        return applications;
    }

    public void setApplications(List<Project> applications) {
        this.applications = applications;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
    
    public void accept() {
        applicationService.acceptStudentProposal(selected);
        context.addMessage(null, new FacesMessage("Student proposal has been approved."));
    }
    
    public void reject() {
        applicationService.rejectStudentProposal(selected);
        context.addMessage(null, new FacesMessage("Student proposal has been rejected."));
    }
}
