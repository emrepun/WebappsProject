/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.ProjectCreationService;
import com.webappsproject.ejb.ProjectTopicService;
import com.webappsproject.entity.Project;
import com.webappsproject.entity.ProjectTopic;
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
public class ProjectProposalCreationBean implements Serializable {
    
    //inject services.
    @EJB
    ProjectTopicService projectTopicService;
    
    @EJB
    ProjectCreationService projectCreationService;
    
    FacesContext context = FacesContext.getCurrentInstance();
    
    //declare properties.
    private List<ProjectTopic> projectTopics;
    private String selected;
    
    String name;
    String description;
    String skills;
    
    String supervisorName;
    
    @PostConstruct
    public void init() {
        projectTopics = projectTopicService.getProjectTopicList();
        selected = projectTopics.get(0).getTopicname();
        supervisorName = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
    }
    
    public ProjectProposalCreationBean() {
        
    }

    public List<ProjectTopic> getProjectTopics() {
        return projectTopics;
    }

    public void setProjectTopics(List<ProjectTopic> projectTopics) {
        this.projectTopics = projectTopics;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        System.out.println("Selected project is: " + selected);
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }
    
    public void createProposal() {
        if (selected==null) {
            context.addMessage(null, new FacesMessage("You must select a topic"));
        } else {
            projectCreationService.createProjectProposal(supervisorName, selected, name, description, skills);
            context.addMessage(null, new FacesMessage("Project is created."));
        }
    }
}
