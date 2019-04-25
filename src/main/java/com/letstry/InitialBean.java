/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.letstry;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author emrehavan
 */
@Singleton
@Named
public class InitialBean implements Serializable {
    
    public InitialBean() {
        
    }
    
    @PostConstruct
    public void initial() {
        System.out.println("created");
    }

    public String administratorSelected() {
        return "adminLogin";
    }
    
    public String supervisorSelected() {
        return "supervisorLogin";
    }
    
    public String studentSelected() {
        return "studentLogin";
    }
    
}
