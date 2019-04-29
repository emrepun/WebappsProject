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
import javax.inject.Named;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class StudentProjectListBean implements Serializable {
    
    private String selected;
    private List<Project> projects;
    private List<Project> availableProjects;
    
    @EJB
    ProjectProposalService proposalService;
    
    @PostConstruct
    public void init() {
        projects = proposalService.getAllProjectsForStudents();
        availableProjects = proposalService.getAllAvailableProjectsForStudents();
    }
    
    public StudentProjectListBean() {
        
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
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
    
    
}
