/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;
import com.webappsproject.entity.ProjectTopic;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author emrehavan
 */
@Stateless
public class ProjectTopicService {
    
    @PersistenceContext
    EntityManager em;
    
    public ProjectTopicService() {
        
    }
    
    //return 1 for success, 0 for failure because duplicate.
    public int registerTopic(String topicname) {
        ProjectTopic topic = new ProjectTopic(topicname);
        try {
            em.persist(topic);
            em.flush();
        } catch (Exception e) {
            System.out.println("exception happened");
            return 0;
        }
        
        System.out.println("Project Topic Created.");
        return 1;
        
    }
    
}
