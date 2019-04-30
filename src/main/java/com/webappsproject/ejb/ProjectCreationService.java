/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;
import com.webappsproject.entity.Project;
import com.webappsproject.entity.ProjectTopic;
import com.webappsproject.entity.Supervisor;

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
    
    public void createProjectProposal(String supervisorID, String topicName, String projectName, String projectDesc, String projectSkills) {
       ProjectTopic topic = (ProjectTopic)em.createNamedQuery("findProjectTopicWithName").setParameter("topicname", topicName).getResultList().get(0);
       Supervisor supervisor = (Supervisor)em.createNamedQuery("findSupervisorWithSussexID").setParameter("sussexId", supervisorID).getResultList().get(0);
       Project proj = new Project(projectName, projectDesc, projectSkills);
       proj.setProjectTopic(topic);
       proj.setSupervisor(supervisor);
       proj.setStudent(null);
       topic.addProject(proj);
       supervisor.addProject(proj);
       
       //persist changes for both project topic and supervisor perspective.
       em.persist(topic);
       em.persist(supervisor);
       em.persist(proj);
       System.out.println("seems chill");
    }
}
