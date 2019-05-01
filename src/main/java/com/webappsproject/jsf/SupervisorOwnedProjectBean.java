/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.ProjectListService;
import com.webappsproject.entity.Project;
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
public class SupervisorOwnedProjectBean implements Serializable {
    
    private List<Project> ownedProjects;
    private String supervisorID;
    
    @EJB
    ProjectListService projectListService;
    
    FacesContext context = FacesContext.getCurrentInstance();
    
    @PostConstruct
    public void init() {
        //get supervisor id from current logged-in user.
        supervisorID = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        
        //get owned projects with supervisor sussex-id.
        ownedProjects = projectListService.getProjectsForSupervisor(supervisorID);
        System.out.println(ownedProjects.size());
        
        if (ownedProjects.isEmpty()) {
            context.addMessage(null, new FacesMessage("You don't have any projects."));
        }
    }
    
    public SupervisorOwnedProjectBean() {
        
    }

    public List<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(List<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public String getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }
}
