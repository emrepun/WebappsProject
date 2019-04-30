/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;

import com.webappsproject.entity.Project;
import com.webappsproject.entity.Supervisor;
import com.webappsproject.entity.Student;
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
}
