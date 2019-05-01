/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;
import com.webappsproject.ejb.ProjectCreationService;
import com.webappsproject.ejb.ProjectTopicService;
import com.webappsproject.ejb.SupervisorService;
import com.webappsproject.entity.ProjectTopic;
import com.webappsproject.entity.Supervisor;

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
public class StudentProjectCreationBean {
    
    private String selectedTopicName;
    private String selectedSupervisorId;
    private List<ProjectTopic> topics;
    private List<Supervisor> supervisors;
    
    private String projectName;
    private String projectDescription;
    private String requiredSkills;
    
    
    @EJB
    ProjectTopicService topicService;
    
    @EJB
    SupervisorService supervisorService;
    
    @EJB
    ProjectCreationService projectCreationService;
    
    FacesContext context = FacesContext.getCurrentInstance();
    
    @PostConstruct
    public void init() {
        if (!topicService.getProjectTopicList().isEmpty()) {
            this.topics = topicService.getProjectTopicList();
        }
        
        if (!supervisorService.getSupervisorList().isEmpty()) {
            this.supervisors = supervisorService.getSupervisorList();
        }
    }
    
    public StudentProjectCreationBean() {
        
    }

    public String getSelectedTopicName() {
        return selectedTopicName;
    }

    public void setSelectedTopicName(String selectedTopicName) {
        this.selectedTopicName = selectedTopicName;
        System.out.println("selected: " + this.selectedTopicName);
    }

    public String getSelectedSupervisorId() {
        return selectedSupervisorId;
    }

    public void setSelectedSupervisorId(String selectedSupervisorId) {
        this.selectedSupervisorId = selectedSupervisorId;
        System.out.println("selected: " + this.selectedSupervisorId);
    }

    public List<ProjectTopic> getTopics() {
        return topics;
    }

    public void setTopics(List<ProjectTopic> topics) {
        this.topics = topics;
    }

    public List<Supervisor> getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(List<Supervisor> supervisors) {
        this.supervisors = supervisors;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
    
    public void submit() {
        //System.out.println(selectedTopicName);
        //System.out.println(topicName);
        //System.out.println(projectCreationService.getSelectedTopicName());
        int result = projectCreationService.createStudentProposal(
                selectedTopicName,
                selectedSupervisorId,
                projectName,
                projectDescription,
                requiredSkills);
        
        switch (result) {
            case 1:
                System.out.println("success");
                context.addMessage(null, new FacesMessage("Proposal sent successfully."));
                break;
            case 0:
                System.out.println("failure, student has a project");
                context.addMessage(null, new FacesMessage("Error! You have already applied or accepted to a project"));
                break;
        }
    }
    
}
