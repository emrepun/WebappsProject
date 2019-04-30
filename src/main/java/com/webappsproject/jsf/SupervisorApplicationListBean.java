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
        if (!applications.isEmpty()) {
            System.out.println("this run 1");
            selected = applications.get(0).getName();
            applicationService.setSelectedProjectApplication(selected);
        } else {
            System.out.println("this run 2");
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
    
    public void accept() {
        System.out.println("accepted");
        applicationService.acceptApplication();
    }
    
    public void reject() {
        System.out.println("rejected");
        applicationService.setStudent(getStudent());
        applicationService.rejectApplication();
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
