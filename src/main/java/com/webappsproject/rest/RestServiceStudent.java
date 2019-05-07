/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.rest;

import com.webappsproject.entity.Project;
import com.webappsproject.entity.Student;
import com.webappsproject.entity.Supervisor;
import com.webappsproject.rest.entities.StudentREST;
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
@Path("/student")
public class RestServiceStudent {
    
    @Context
    private UriInfo context;
    
    //inject entity manager to interact with DB.
    @PersistenceContext
    EntityManager em;
    
    public RestServiceStudent() {
        
    }
    
    @GET
    @Path("/{supervisorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSupervisorStudents(@PathParam("supervisorId") String supervisorId) {
        //get supervisors by querying the db.
        List<Supervisor> supervisors = em.createNamedQuery("findSupervisorWithSussexID").
                setParameter("sussexId", supervisorId).
                getResultList();
        
        if (!supervisors.isEmpty()) {
            //if supervisor found get projects of the supervisor.
            Supervisor supervisor = supervisors.get(0);
            List<Project> projects = supervisor.getOwnedProjects();
            List<Student> students = new ArrayList<>();

            //for each project, check if there is an associated student with it (applied or accepted)
            //if so, add student to students.
            for (Project p: projects) {
                if (p.getStudent() != null) {
                    students.add(p.getStudent());
                }
            }

            List<StudentREST> resultStudents = new ArrayList<>();

            //map entity students to StudentREST to return with JSON.
            for (Student s: students) {
                StudentREST temp = new StudentREST();
                temp.setSussexId(s.getSussexId());
                temp.setName(s.getName());
                temp.setSurname(s.getSurname());
                temp.setEmail(s.getEmail());
                temp.setCourse(s.getCourse());
                
                // project and supervisor will be missing from JSON if its null.
                if (s.getAssociatedProject() != null) {
                    temp.setProject(s.getAssociatedProject().getName());
                    if (s.getAssociatedProject().getSupervisor() != null) {
                        temp.setSupervisor(s.getAssociatedProject().getSupervisor().getSussexId());
                    } else {
                        temp.setSupervisor(s.getAssociatedProject().getSupervisorOptional().getSussexId());
                    }
                } else {
                    temp.setProject(null);
                    temp.setSupervisor(null);
                }

                resultStudents.add(temp);
            }
            
            return Response.ok(resultStudents).build(); 
        } else {
            //supervisor not found or this supervisor has no students.
            return Response.status(Response.Status.NOT_FOUND).build();            
        }
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents() {
        //get all students by querying the db.
        List<Student> students = em.createNamedQuery("getAllStudents").getResultList();
        List<StudentREST> resultStudents = new ArrayList<>();
        
        //map entity students to StudentREST to return with JSON.
        for (Student s: students) {
            StudentREST temp = new StudentREST();
            temp.setSussexId(s.getSussexId());
            temp.setName(s.getName());
            temp.setSurname(s.getSurname());
            temp.setEmail(s.getEmail());
            temp.setCourse(s.getCourse());
            
            // project and supervisor will be missing from JSON if its null.
            if (s.getAssociatedProject() != null) {
                    temp.setProject(s.getAssociatedProject().getName());
                    if (s.getAssociatedProject().getSupervisor() != null) {
                        temp.setSupervisor(s.getAssociatedProject().getSupervisor().getSussexId());
                    } else {
                        temp.setSupervisor(s.getAssociatedProject().getSupervisorOptional().getSussexId());
                    }
                } else {
                    temp.setProject(null);
                    temp.setSupervisor(null);
                }
            
            resultStudents.add(temp);
        }
        
        return Response.ok(resultStudents).build();
    }
}
