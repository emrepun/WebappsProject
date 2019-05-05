/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;
import com.webappsproject.entity.Project;
import com.webappsproject.entity.ProjectTopic;
import com.webappsproject.entity.Supervisor;
import java.util.ArrayList;

import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author emrehavan
 */
@Singleton
//this service is available for all user types.
public class ProjectListService {
    
    //declare properties.
    private String selectedProjectTopic;
    
    //inject entity manager to interact with DB.
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
        if (selectedProjectTopic.equals("empty")) {
            return new ArrayList<Project>();
        } else {
            ProjectTopic topic = (ProjectTopic)em.createNamedQuery("findProjectTopicWithName").
                setParameter("topicname", topicName).
                getResultList().get(0);
            return topic.getProjects();
        }
    }
    
    public List<Project> getProjectsForSupervisor(String sussexId) {
        Supervisor supervisor = (Supervisor)em.createNamedQuery("findSupervisorWithSussexID").
                setParameter("sussexId", sussexId).
                getResultList().get(0);
        System.out.println(supervisor.getSussexId());
        return supervisor.getOwnedProjects();
    }
}
