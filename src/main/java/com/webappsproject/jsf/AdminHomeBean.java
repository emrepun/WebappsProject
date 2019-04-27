/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class AdminHomeBean implements Serializable {
    
    public AdminHomeBean() {
        
    }
    
    //redirect to admin registration view
    public String registerAdmin() {
        return "adminRegistration";
    }
    
    //redirect to supervisor registration view
    public String registerSupervisor() {
        return "supervisorRegistration";
    }
    
    //redirect to student registration view
    public String registerStudent() {
        return "studentRegistration";
    }
    
    //redirect to project topic creation view
    public String createProjectTopic() {
        return "projectTopicCreation";
    }
    
}
