/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.ProjectProposalService;
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
public class StudentProjectListBean implements Serializable {
    
    //declare properties.
    private String selected;
    private List<Project> projects;
    private List<Project> availableProjects;
    
    //inject services.
    @EJB
    ProjectProposalService proposalService;
    
    //get current context.
    FacesContext context = FacesContext.getCurrentInstance();
    
    @PostConstruct
    public void init() {
        projects = proposalService.getAllProjectsForStudents();
        availableProjects = proposalService.getAllAvailableProjectsForStudents();
        if (availableProjects.isEmpty()) {
            context.addMessage(null, new FacesMessage("There are no available projects"));
            selected = "";
        } else {
            selected = availableProjects.get(0).getName();
        }
    }
    
    public StudentProjectListBean() {
        
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
        //proposalService.setSelectedProject(this.selected);
        System.out.println("Selected project: " + this.selected);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Project> getAvailableProjects() {
        return availableProjects;
    }

    public void setAvailableProjects(List<Project> availableProjects) {
        this.availableProjects = availableProjects;
    }
    
    public void applyForProject() {
        System.out.println("applied for project");
        int result = proposalService.applyForProjectWithName(selected);
        //inform student depending on the outcome of the method.
        switch (result) {
            case -1:
                context.addMessage(null, new FacesMessage("You already have a project."));
                break;
            case 0:
                context.addMessage(null, new FacesMessage("You already applied to a project"));
                break;
            case 1:
                context.addMessage(null, new FacesMessage("Application Completed."));
                break;
            default:
                context.addMessage(null, new FacesMessage("An error occurred"));              
        }
    }
}
