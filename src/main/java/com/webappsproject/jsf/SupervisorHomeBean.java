/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class SupervisorHomeBean {
    
    public SupervisorHomeBean() {
        
    }
    
    //redirect to project topic creation view
    public String createProjectTopic() {
        return "supervisorProjectTopicCreation";
    }
    
}
