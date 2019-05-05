/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.ProjectListService;
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
public class ProjectListDisplayBean {
    
    //declare properties.
    private String topicName;
    private List<Project> projects;
    
    //inject services.
    @EJB
    ProjectListService projectListService;
    
    //get current context.
    FacesContext context = FacesContext.getCurrentInstance();
    
    @PostConstruct
    public void init() {
        this.topicName = projectListService.getSelectedProjectTopic();
        if (this.topicName != null) {
            System.out.println(this.topicName);
            this.projects = projectListService.getProjectsForProjectTopicName(topicName);
            if (projects.isEmpty()) {
                context.addMessage(null, new FacesMessage("There are no projects for this topic."));
            }
        }
    }
    
    public ProjectListDisplayBean() {
        
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
}
