/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.AdminService;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
    
    @EJB
    AdminService adminService;
    
    public InitialBean() {
        
    }
    
    @PostConstruct
    public void initial() {
        System.out.println("created");
        adminService.registerAdmin("admin1", "admin1");
    }

    public String loginSelected() {
        return "login";
    }    
}
