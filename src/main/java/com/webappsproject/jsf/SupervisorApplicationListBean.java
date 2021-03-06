/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.ProjectApplicationReviewService;
import com.webappsproject.entity.Project;
import com.webappsproject.entity.Student;
import java.io.Serializable;
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
public class SupervisorApplicationListBean implements Serializable {
    
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
        String supervisorID = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        applications = applicationService.getApplicationsForSupervisor(supervisorID);
        if (!applications.isEmpty()) {
            selected = applications.get(0).getName();
            applicationService.setSelectedProjectApplication(selected);
        } else {
            context.addMessage(null, new FacesMessage("There are no applications."));
            selected = "";
        }
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
        System.out.println("this run 3");
        this.selected = selected;
        applicationService.setSelectedProjectApplication(selected);
    }
    
    public String accept() {
        System.out.println("accepted");
        applicationService.acceptApplication();
        context.addMessage(null, new FacesMessage("Application accepted."));
        return "supervisorHome";
    }
    
    public String reject() {
        System.out.println("rejected");
        applicationService.setStudent(getStudent());
        applicationService.rejectApplication();
        return "supervisorHome";
    }
    
    public String getStudent() {
        for (Project p: applications) {
            if (p.getName().equals(selected)) {
                return p.getStudent().getSussexId();
            }
        }
        
        return null;
    }
}
