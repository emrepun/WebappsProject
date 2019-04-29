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
import javax.inject.Named;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class ProjectListDisplayBean {
    
    private String topicName;
    private List<Project> projects;
    
    @EJB
    ProjectListService projectListService;
    
    @PostConstruct
    public void init() {
        this.topicName = projectListService.getSelectedProjectTopic();
        if (this.topicName != null) {
            this.projects = projectListService.getProjectsForProjectTopicName(topicName);
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
        System.out.println("this ran.");
        System.out.println(projects.size());
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
}
