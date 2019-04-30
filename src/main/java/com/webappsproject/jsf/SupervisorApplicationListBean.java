/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.ProjectApplicationReviewService;
import com.webappsproject.entity.Project;
import java.io.Serializable;
import java.util.List;
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
public class SupervisorApplicationListBean implements Serializable {
    
    private List<Project> applications;
    private String selected;
    
    @EJB
    ProjectApplicationReviewService applicationService;        
    
    FacesContext context = FacesContext.getCurrentInstance();
    
    @PostConstruct
    public void init() {
        String supervisorID = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        applications = applicationService.getApplicationsForSupervisor(supervisorID);
    }
    
    public SupervisorApplicationListBean() {
        
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
        applicationService.setSelectedProjectApplication(selected);
    }
    
    public void accept() {
        System.out.println("accepted");
        //Implementation pending
    }
    
    public void reject() {
        System.out.println("rejected");
        //Implementation pending
    }
}
