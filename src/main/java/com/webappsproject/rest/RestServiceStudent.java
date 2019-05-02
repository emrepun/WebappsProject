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
    
    @PersistenceContext
    EntityManager em;
    
    public RestServiceStudent() {
        
    }
    
    @GET
    @Path("/{supervisorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudentREST> getSupervisorStudents(@PathParam("supervisorId") String supervisorId) {
        List<Supervisor> supervisors = em.createNamedQuery("findSupervisorWithSussexID").
                setParameter("sussexId", supervisorId).
                getResultList();
        
        if (!supervisors.isEmpty()) {
            Supervisor supervisor = supervisors.get(0);
            List<Project> projects = supervisor.getOwnedProjects();
            List<Student> students = new ArrayList<>();

            for (Project p: projects) {
                if (p.getStudent() != null) {
                    students.add(p.getStudent());
                }
            }

            List<StudentREST> resultStudents = new ArrayList<>();

            for (Student s: students) {
                StudentREST temp = new StudentREST();
                temp.setSussexId(s.getSussexId());
                temp.setName(s.getName());
                temp.setSurname(s.getSurname());
                temp.setEmail(s.getEmail());
                temp.setCourse(s.getCourse());

                resultStudents.add(temp);
            }

            return resultStudents;
        } else {
            //will display no content response.
            return null;
        }
    }
    
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<StudentREST> getAllStudents() {
        List<Student> students = em.createNamedQuery("getAllStudents").getResultList();
        List<StudentREST> resultStudents = new ArrayList<>();
        
        for (Student s: students) {
            StudentREST temp = new StudentREST();
            temp.setSussexId(s.getSussexId());
            temp.setName(s.getName());
            temp.setSurname(s.getSurname());
            temp.setEmail(s.getEmail());
            temp.setCourse(s.getCourse());
            
            resultStudents.add(temp);
        }
        
        return resultStudents;
    }
}
