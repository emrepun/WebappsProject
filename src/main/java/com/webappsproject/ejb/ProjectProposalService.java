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
        System.out.println("ne");
        return em.createNamedQuery("findAvailableProjects").
                setParameter("status", Project.ProjectStatus.AVAILABLE).
                getResultList();
    }
}
