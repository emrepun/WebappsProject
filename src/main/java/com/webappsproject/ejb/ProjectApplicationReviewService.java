/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;

import com.webappsproject.entity.Project;
import com.webappsproject.entity.ProjectTopic;
import com.webappsproject.entity.Supervisor;
import com.webappsproject.entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author emrehavan
 */
@Singleton
@RolesAllowed({"supervisor"}) //only supervisors can access to this service.
public class ProjectApplicationReviewService {
    
    private String selectedProjectApplication;
    private String studentId;
    
    @PersistenceContext
    EntityManager em;
    
    public ProjectApplicationReviewService() {
        
    }

    public String getSelectedProjectApplication() {
        return selectedProjectApplication;
    }

    public void setSelectedProjectApplication(String selectedProjectApplication) {
        this.selectedProjectApplication = selectedProjectApplication;
    }

    public String getStudent() {
        return studentId;
    }

    public void setStudent(String studentId) {
        this.studentId = studentId;
    }
    
    //Get applications made to projects created by a supervisor.
    public synchronized List<Project> getApplicationsForSupervisor(String sussexId) {
        Supervisor supervisor = (Supervisor)em.createNamedQuery("findSupervisorWithSussexID").
                setParameter("sussexId", sussexId).
                getResultList().get(0);
        List<Project> allProjects = supervisor.getOwnedProjects();
        List<Project> appliedProjects = new ArrayList();
        for (Project p: allProjects) {
            if (p.getStatus() == Project.ProjectStatus.PROPOSED) {
                appliedProjects.add(p);
            }
        }
        
        return appliedProjects;
    }
    
    //Get applications made by a student to a supervisor.
    public synchronized List<Project> getStudentProposedApplicationsForSupervisor(String sussexId) {
        Supervisor supervisor = (Supervisor)em.createNamedQuery("findSupervisorWithSussexID").
                setParameter("sussexId", sussexId).
                getResultList().get(0);
        List<Project> proposals = supervisor.getProposedProjects();
        return proposals;
    }
    
    // Accept and Reject applications that created by supervisor.
    
    public void acceptApplication() {
        Project project = (Project)em.createNamedQuery("findProjectWithName").
                setParameter("title", selectedProjectApplication).
                getResultList().get(0);
        if (project.getStatus() == Project.ProjectStatus.PROPOSED) {
            project.setStatus(Project.ProjectStatus.ACCEPTED);
            em.persist(project);
        }
    }
    
    public void rejectApplication() {
        Project project = (Project)em.createNamedQuery("findProjectWithName").
                setParameter("title", selectedProjectApplication).
                getResultList().get(0);
        
        if (project.getStatus() == Project.ProjectStatus.PROPOSED) {
            project.setStatus(Project.ProjectStatus.AVAILABLE);
            project.setStudent(null);
            
            em.persist(project);
            
            if (studentId != null) {
                Student student = (Student)em.createNamedQuery("findStudentWithSussexId").
                        setParameter("sussexId", studentId).
                        getResultList().get(0);
                student.setAssociatedProject(null);
                em.persist(student);
            } 
        }
    }
    
    // Accept and reject applications proposed by a student
    public void acceptStudentProposal(String projectName) {
        Project project = (Project)em.createNamedQuery("findProjectWithName").
                setParameter("title", projectName).
                getResultList().get(0);
        
        if (project != null && project.getStatus() == Project.ProjectStatus.PROPOSED) {
            Supervisor supervisor = project.getSupervisorOptional();
            
            project.setStatus(Project.ProjectStatus.ACCEPTED);
            
            //remove supervisor from optional and add to actual supervisor.
            project.setSupervisorOptional(null);
            project.setSupervisor(supervisor);
            
            //remove project from proposed and add to actual projects.
            supervisor.removeProposedProject(project);
            supervisor.addProject(project);
            
            //persist objects to DB.
            em.persist(project);
            em.persist(supervisor);
        }
    }
    
    public void rejectStudentProposal(String projectName) {
        Project project = (Project)em.createNamedQuery("findProjectWithName").
                setParameter("title", projectName).
                getResultList().get(0);
        
        if (project != null && project.getStatus() == Project.ProjectStatus.PROPOSED) {
            //get associated topic, supervisor and student from project.
            Supervisor supervisor = project.getSupervisorOptional();
            Student student = project.getStudent();
            ProjectTopic topic = project.getProjectTopic();
            
            //dissociate topic, supervisor and student from project.
            project.setSupervisorOptional(null);
            project.setStudent(null);
            project.setProjectTopic(null);
            
            //dissociate project from student and supervisor.
            supervisor.removeProposedProject(project);
            student.setAssociatedProject(null);
            topic.removeProject(project);
            
            //persist changes for all objects.
            em.persist(project);
            em.persist(supervisor);
            em.persist(student);
            em.persist(topic);
            
            //finally delete the project from DB.
            em.remove(project);
        }
    }
}
