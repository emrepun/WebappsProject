/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;
import com.webappsproject.entity.ProjectTopic;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public int registerTopic(String topicname, String topicDescription) {
        ProjectTopic topic = new ProjectTopic(topicname, topicDescription);
        try {
            em.persist(topic);
            //use flush to execute persisting immediately so we can catch exceptions.
            em.flush();
        } catch (Exception e) {
            System.out.println("exception happened");
            return 0;
        }
        
        System.out.println("Project Topic Created.");
        return 1;
        
    }
    
    public synchronized List<ProjectTopic> getProjectTopicList() {
        return em.createNamedQuery("getAllProjectTopics").getResultList();
    }
    
}
