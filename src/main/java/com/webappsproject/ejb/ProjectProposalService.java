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
public class ProjectProposalService {
    
    private String selectedProject;
    
    @PersistenceContext
    EntityManager em;
    
    public ProjectProposalService() {
        
    }

    public String getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(String selectedProject) {
        this.selectedProject = selectedProject;
    }
    
    public synchronized List<Project> getAllProjectsForStudents() {
        return em.createNamedQuery("getAllProjects").getResultList();
    }
    
    public synchronized List<Project> getAllAvailableProjectsForStudents() {
        return em.createNamedQuery("findAvailableProjects").
                setParameter("status", Project.ProjectStatus.AVAILABLE).
                getResultList();
    }
    
    public synchronized int applyForProjectWithName() {
        //get current logged-in student.
        
        String studentName = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        //get project with selectedProject name
        
        Project project = (Project)em.createNamedQuery("findProjectWithName").
                setParameter("title", selectedProject).
                getResultList().get(0);
        //get student
        Student student = (Student)em.createNamedQuery("findStudentWithSussexId").
                setParameter("sussexId", studentName).
                getResultList().get(0);
        if (student.getAssociatedProject() == null) {
            project.setStatus(Project.ProjectStatus.PROPOSED);
            project.setStudent(student);
        
            student.setAssociatedProject(project);
            em.persist(project);
            em.persist(student);
            return 1;
        } else if (student.getAssociatedProject().getStatus() == Project.ProjectStatus.PROPOSED) {
            return 0;
        } else if (student.getAssociatedProject().getStatus() == Project.ProjectStatus.ACCEPTED) {
            return -1;
        }
        
        System.out.println(student.getAssociatedProject().getTitle());
        return -2;
    }
}
