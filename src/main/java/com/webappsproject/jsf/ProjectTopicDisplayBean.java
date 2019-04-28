/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;
import com.webappsproject.ejb.ProjectTopicService;
import com.webappsproject.entity.ProjectTopic;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class ProjectTopicDisplayBean implements Serializable {
    
    private String name;
    private List<ProjectTopic> projectTopics;
    
    @EJB
    ProjectTopicService projectTopicService;
    
    @PostConstruct
    public void init() {
        name = "Project Topics";
        projectTopics = projectTopicService.getProjectTopicList();       
    }
    
    public ProjectTopicDisplayBean() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProjectTopic> getProjectTopics() {
        return projectTopics;
    }

    public void setProjectTopics(List<ProjectTopic> projectTopics) {
        this.projectTopics = projectTopics;
    }
}
