/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;
import com.webappsproject.entity.Project;
import com.webappsproject.entity.ProjectTopic;

import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author emrehavan
 */
@Singleton
public class ProjectListService {
    
    private String selectedProjectTopic;
    
    @PersistenceContext
    EntityManager em;
    
    public ProjectListService() {
        
    }

    public String getSelectedProjectTopic() {
        return selectedProjectTopic;
    }

    public void setSelectedProjectTopic(String selectedProjectTopic) {
        this.selectedProjectTopic = selectedProjectTopic;
    }
    
    public List<Project> getProjectsForProjectTopicName(String topicName) {
        ProjectTopic topic = (ProjectTopic)em.createNamedQuery("findProjectTopicWithName").
                setParameter("topicname", topicName).
                getResultList().get(0);
        return topic.getProjects();
    }
}
