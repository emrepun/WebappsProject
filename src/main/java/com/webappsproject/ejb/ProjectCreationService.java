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
public class ProjectCreationService {
    
    @PersistenceContext
    EntityManager em;
    
    public ProjectCreationService() {
        
    }
    
    public void createProjectProposal(String topicName, String projectName, String projectDesc, String projectSkills) {
       ProjectTopic topic = (ProjectTopic)em.createNamedQuery("findProjectTopicWithName").setParameter("topicname", topicName).getResultList().get(0);
       Project proj = new Project(projectName, projectDesc, projectSkills);
       topic.addProject(proj);
       
       em.persist(topic);
       System.out.println("seems chill");
    }
}
