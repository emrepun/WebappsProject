/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;
import com.webappsproject.ejb.ProjectListService;
import com.webappsproject.ejb.ProjectTopicService;
import com.webappsproject.entity.ProjectTopic;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class ProjectTopicDisplayBean implements Serializable {
    
    //declare properties.
    private String selected;
    private List<ProjectTopic> projectTopics;
    
    //inject services.
    @EJB
    ProjectTopicService projectTopicService;
    
    @EJB
    ProjectListService projectListService;
    
    //get current context.
    FacesContext context = FacesContext.getCurrentInstance();
    
    @PostConstruct
    public void init() {
        projectTopics = projectTopicService.getProjectTopicList(); 
        if (projectTopics.isEmpty()) {
            context.addMessage(null, new FacesMessage("There are no project topics."));
            selected = "";
        } else {
            selected = projectTopics.get(0).getTopicname();
        }
        projectListService.setSelectedProjectTopic(selected);
    }
    
    public ProjectTopicDisplayBean() {
        
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        System.out.println("selected: " + selected);
        
        //update the selected project topic of the service used to display all projects.
        projectListService.setSelectedProjectTopic(selected);
        this.selected = selected;
    }
    
    public List<ProjectTopic> getProjectTopics() {
        return projectTopics;
    }

    public void setProjectTopics(List<ProjectTopic> projectTopics) {
        this.projectTopics = projectTopics;
    }
    
    //navigate next page if projectTopics are not empty.
    public String displayListForTopic() {
        if (projectTopics.isEmpty()) {
            return "";
        } else {
            return "projectList";
        }
    }
}
