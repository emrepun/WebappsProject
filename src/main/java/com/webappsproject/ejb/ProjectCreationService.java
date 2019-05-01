/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;
import com.webappsproject.entity.Project;
import com.webappsproject.entity.ProjectTopic;
import com.webappsproject.entity.Student;
import com.webappsproject.entity.Supervisor;

import java.util.List;
import javax.ejb.Singleton;
import javax.faces.context.FacesContext;
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
    
    // Used by supervisors
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
    
    // Used by students
    public int createStudentProposal(String topicName,
            String supervisorId,
            String projectName,
            String projectDescription,
            String requiredSkills) {
        
        String studentName = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        
        Student student = (Student)em.createNamedQuery("findStudentWithSussexId").
                setParameter("sussexId", studentName).
                getResultList().get(0);
        
        System.out.println(topicName);
        System.out.println(supervisorId);
        System.out.println(projectName);
        System.out.println(projectDescription);
        System.out.println(requiredSkills);
        
        if (student.getAssociatedProject() == null) {
            ProjectTopic topic = (ProjectTopic)em.createNamedQuery("findProjectTopicWithName").setParameter("topicname", topicName).getResultList().get(0);
            Supervisor supervisor = (Supervisor)em.createNamedQuery("findSupervisorWithSussexID").setParameter("sussexId", supervisorId).getResultList().get(0);
            Project project = new Project(projectName, projectDescription, requiredSkills);
            project.setProjectTopic(topic);
            project.setSupervisorOptional(supervisor);
            project.setStudent(student);
            project.setStatus(Project.ProjectStatus.PROPOSED);
            
            topic.addProject(project);
            //supervisor.addProject(project);
            supervisor.addProposedProject(project);
            student.setAssociatedProject(project);
            
            em.persist(topic);
            em.persist(supervisor);
            em.persist(project);
            em.persist(student);
            return 1;
        } else {
            return 0;
        }
        
    }
}
