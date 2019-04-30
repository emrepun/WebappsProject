/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;

import com.webappsproject.entity.Project;
import com.webappsproject.entity.Supervisor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
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
}
