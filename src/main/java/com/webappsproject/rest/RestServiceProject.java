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
import javax.ws.rs.core.Response;

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
    
    //inject entity manager to interact with DB.
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
    public Response getSupervisorProjects(@PathParam("supervisorId") String supervisorId) {
        //get entityProjects by querying the db.
        List<Project> entityProjects = (List<Project>)em.createNamedQuery("findProjectsWithSupervisorId").
                setParameter("sussexId", supervisorId).
                getResultList();
        
        if (entityProjects.isEmpty()) {
            //return not found if supervisor or projects found.
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        List<ProjectREST> restResult = new ArrayList<>();
        
        //map entity projects to ProjectREST to return with JSON.
        for (Project p: entityProjects) {
            ProjectREST temp = new ProjectREST();
            temp.setTitle(p.getTitle());
            temp.setDescription(p.getDescription());
            temp.setRequiredSkills(p.getRequiredSkills());
            temp.setProjectTopic(p.getProjectTopic().getTopicname());
            
            //this field wont be missing because every project either has a supervisor,
            //or an optional supervisor.
            if (p.getSupervisor() != null) {
                temp.setSupervisor(p.getSupervisor().getSussexId());
            } else if (p.getSupervisorOptional() != null) {
                temp.setSupervisor(p.getSupervisorOptional().getSussexId());
            }
            
            // student will be missing from JSON if its null.
            if (p.getStudent() == null) {
                temp.setStudent(null);
            } else {
                temp.setStudent(p.getStudent().getSussexId());
            }
            
            restResult.add(temp);
        }
        return Response.ok(restResult).build();
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProjects() {
        //get entityProjects by querying the db.
        List<Project> allProjects = (List<Project>)em.createNamedQuery("getAllProjects").
                getResultList();
        
        if (allProjects.isEmpty()) {
            //return not found if there are no projects created.
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        List<ProjectREST> restResult = new ArrayList<>();
        
        //map entity projects to ProjectREST to return with JSON.
        for (Project p: allProjects) {
            ProjectREST temp = new ProjectREST();
            temp.setTitle(p.getTitle());
            temp.setDescription(p.getDescription());
            temp.setRequiredSkills(p.getRequiredSkills());
            temp.setProjectTopic(p.getProjectTopic().getTopicname());
            
            if (p.getSupervisor() != null) {
                temp.setSupervisor(p.getSupervisor().getSussexId());
            } else if (p.getSupervisorOptional() != null) {
                temp.setSupervisor(p.getSupervisorOptional().getSussexId());
            }
            
            // student will be missing from JSON if its null.
            if (p.getStudent() == null) {
                temp.setStudent(null);
            } else {
                temp.setStudent(p.getStudent().getSussexId());
            }
            
            restResult.add(temp);
        }
        
        return Response.ok(restResult).build();
    }
}
