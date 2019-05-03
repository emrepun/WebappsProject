/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.rest;

import com.webappsproject.entity.Project;
import com.webappsproject.entity.Student;
import com.webappsproject.entity.Supervisor;
import com.webappsproject.rest.entities.SupervisorREST;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author emrehavan
 */
@Singleton
@Path("/supervisor")
public class RestServiceSupervisor {
    
    @Context
    private UriInfo context;
    
    //inject entity manager to interact with DB.
    @PersistenceContext
    EntityManager em;
    
    public RestServiceSupervisor() {
        
    }
    
    @GET
    @Path("/{studentId}")
    @Produces(MediaType.APPLICATION_JSON)
    
    public Response getStudentSupervisor(@PathParam("studentId") String studentId) {
        //get student by querying the db.
        List<Student> studentResultList = em.createNamedQuery("findStudentWithSussexId").
                setParameter("sussexId", studentId).
                getResultList();
        
        if (!studentResultList.isEmpty()) {
            Student student = studentResultList.get(0);
            
            //get project first because students and supervisors are connected via projects.
            Project project = student.getAssociatedProject();
            Supervisor supervisor = null;
        
            //initialize the supervisor depending on the project's type associated with student.
            if (project != null) {
                if (project.getSupervisor() != null) {
                    //student either applied or accepted to a project created by a supervisor.
                    supervisor = project.getSupervisor();
                } else if (project.getSupervisorOptional() != null) {
                    //student proposed to a supervisor with his or her own project.
                    supervisor = project.getSupervisorOptional();
                } else {
                    //wont happen.
                    supervisor = null;
                }
            } else {
                //student does not have a project associated with. so supervisor not found.
                return Response.status(Response.Status.NOT_FOUND).build(); 
            }
        
            //map entity supervisor to SupervisorREST to return with JSON.
            SupervisorREST supervisorResult = new SupervisorREST();
        
            supervisorResult.setSussexId(supervisor.getSussexId());
            supervisorResult.setName(supervisor.getName());
            supervisorResult.setSurname(supervisor.getSurname());
            supervisorResult.setEmail(supervisor.getEmail());
            supervisorResult.setTelephone(supervisor.getTelephone());
        
            return Response.ok(supervisorResult).build();
            
        } else {
            //student not found to return supervisor.
            return Response.status(Response.Status.NOT_FOUND).build();    
        }
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSupervisors() {
        //get all supervisors by querying the db.
        List<Supervisor> supervisors = em.createNamedQuery("getAllSupervisors").getResultList();
        List<SupervisorREST> resultSupervisors = new ArrayList<>();
        
        //map entity supervisors to SupervisorREST to return with JSON.
        for (Supervisor s: supervisors) {
            SupervisorREST supervisorResult = new SupervisorREST();
        
            supervisorResult.setSussexId(s.getSussexId());
            supervisorResult.setName(s.getName());
            supervisorResult.setSurname(s.getSurname());
            supervisorResult.setEmail(s.getEmail());
            supervisorResult.setTelephone(s.getTelephone());
            
            resultSupervisors.add(supervisorResult);   
        }
        
        return Response.ok(resultSupervisors).build();
    }
}
