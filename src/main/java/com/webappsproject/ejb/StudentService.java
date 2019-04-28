/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;
import com.webappsproject.entity.SystemUser;
import com.webappsproject.entity.SystemUserGroup;
import com.webappsproject.entity.Student;
import java.security.MessageDigest;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author emrehavan
 */
@Stateless
public class StudentService {
    
    @PersistenceContext
    EntityManager em;
    
    public StudentService() {
        
    }
    
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
    
}
