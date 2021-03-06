/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;
import com.webappsproject.ejb.ProjectTopicService;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class ProjectTopicCreationBean implements Serializable {
    
    //inject services.
    @EJB
    ProjectTopicService topicService;
    
    //get current context.
    FacesContext context = FacesContext.getCurrentInstance();
    
    //declare properties.
    String topicname;
    String topicDescription;
    
    public ProjectTopicCreationBean() {
        
    }
    
    public void register() {
        //register topic and get result indicator int.
        int result = topicService.registerTopic(topicname, topicDescription);
        //display informative message to view depending on the outcome.
        switch (result) {
            case 0:
                context.addMessage(null, new FacesMessage("This topic already exists."));
                break;
            case 1:
                context.addMessage(null, new FacesMessage(topicname + " is successfully created."));
                break;
        }
    }

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }
}
