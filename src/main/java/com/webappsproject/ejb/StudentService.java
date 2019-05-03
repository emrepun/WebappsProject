/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;
import com.webappsproject.entity.Project;
import com.webappsproject.entity.SystemUser;
import com.webappsproject.entity.SystemUserGroup;
import com.webappsproject.entity.Student;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author emrehavan
 */
@Stateless
public class StudentService {
    
    //inject entity manager to interact with DB.
    @PersistenceContext
    EntityManager em;
    
    public StudentService() {
        
    }
    
    @RolesAllowed({"admin"}) //only admins can register a student
    public int registerStudent(
            String sussexId,
            String password,
            String name,
            String surname,
            String email,
            String course
            ) {
        
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            Student student;
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = password;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();

            sys_user = new SystemUser(sussexId, paswdToStoreInDB);
            sys_user_group = new SystemUserGroup(sussexId, "student");
            student = new Student(sussexId, paswdToStoreInDB, name, surname, email, course);
            
            em.persist(sys_user);
            em.persist(sys_user_group);
            em.persist(student);
            em.flush();
            System.out.println("Student Registration Completed");
            return 1;
            
        } catch (Exception e) {
            return 0;
        }
    }
    
    public synchronized List<Student> getStudentList() {
        return em.createNamedQuery("getAllStudents").getResultList();
    }
    
    public synchronized Student getLoggedInStudent() {
        String sussexId = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        System.out.println(sussexId);
        return (Student)em.createNamedQuery("findStudentWithSussexId").setParameter("sussexId", sussexId)
                .getResultList().get(0);
    }
    
    public synchronized List<Student> getStudentsForSupervisor(String supervisorId) {
        //get projects of supervisor first because students and supervisors are connected through projects.
        List<Project> supervisorProjects = new ArrayList<>();
        supervisorProjects = em.createNamedQuery("findProjectsWithSupervisorId").
                setParameter("sussexId", supervisorId).
                getResultList();
        
        ArrayList<Student> students = new ArrayList<>();
        
        // get projects with confirmed students and add them to students result.
        for (Project p: supervisorProjects) {
            if (p.getStudent() != null) {
                students.add(p.getStudent());
            }
        }
        
        return students;
    }
    
}
