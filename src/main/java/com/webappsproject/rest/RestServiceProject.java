/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.rest;

import com.webappsproject.rest.entities.ProjectREST;
import com.webappsproject.entity.Project;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author emrehavan
 */
@Singleton
@Path("/project")
public class RestServiceProject {

    @Context
    private UriInfo context;
    
    @PersistenceContext
    EntityManager em;

    /**
     * Creates a new instance of GenericResource
     */
    public RestServiceProject() {
        
    }

    /**
     * Retrieves representation of an instance of com.webappsproject.rest.RestServiceProject
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/{supervisorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectREST> getSupervisorProjects(@PathParam("supervisorId") String supervisorId) {
        System.out.println("this ran.");
        System.out.println(supervisorId);
        List<Project> entityProjects = (List<Project>)em.createNamedQuery("findProjectsWithSupervisorId").
                setParameter("sussexId", supervisorId).
                getResultList();
        List<ProjectREST> restResult = new ArrayList<>();
        
        for (Project p: entityProjects) {
            ProjectREST temp = new ProjectREST();
            temp.setTitle(p.getTitle());
            temp.setDescription(p.getDescription());
            temp.setRequiredSkills(p.getRequiredSkills());
            temp.setProjectTopic(p.getProjectTopic().getTopicname());
            
            restResult.add(temp);
        }
        
        return restResult;
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProjectREST> getAllProjects() {
        System.out.println("all ran.");
        List<Project> allProjects = (List<Project>)em.createNamedQuery("getAllProjects").
                getResultList();
        List<ProjectREST> restResult = new ArrayList<>();
        
        for (Project p: allProjects) {
            ProjectREST temp = new ProjectREST();
            temp.setTitle(p.getTitle());
            temp.setDescription(p.getDescription());
            temp.setRequiredSkills(p.getRequiredSkills());
            temp.setProjectTopic(p.getProjectTopic().getTopicname());
            
            restResult.add(temp);
        }
        
        return restResult;
    }
}
