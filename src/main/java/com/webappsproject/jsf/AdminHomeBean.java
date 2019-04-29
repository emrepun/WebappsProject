/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.AdminService;
import com.webappsproject.ejb.StudentService;
import com.webappsproject.ejb.SupervisorService;
import com.webappsproject.ejb.ProjectTopicService;
import com.webappsproject.entity.Admin;
import com.webappsproject.entity.Supervisor;
import com.webappsproject.entity.Student;
import com.webappsproject.entity.ProjectTopic;
import com.webappsproject.entity.Project;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class AdminHomeBean implements Serializable {
    
    @EJB
    AdminService adminService;
    
    @EJB
    SupervisorService supervisorService;
    
    @EJB
    StudentService studentService;
    
    @EJB
    ProjectTopicService projectTopicService;
    
    private String ne;
    
    public AdminHomeBean() {
        
    }
    
    public List<Admin> getAdminList() {
        return adminService.getAdminList();
    }
    
    public List<Supervisor> getSupervisorList() {
        return supervisorService.getSupervisorList();
    }
    
    public List<Student> getStudentList() {
        return studentService.getStudentList();
    }
    
    public List<ProjectTopic> getProjectTopicList() {
        return projectTopicService.getProjectTopicList();
    }
    
}
