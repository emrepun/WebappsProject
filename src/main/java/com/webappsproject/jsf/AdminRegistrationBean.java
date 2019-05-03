/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;
import com.webappsproject.ejb.AdminService;

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
public class AdminRegistrationBean implements Serializable {
    
    //inject services.
    @EJB
    AdminService adminService;
    
    //get current context.
    FacesContext context = FacesContext.getCurrentInstance();
    
    //declare properties.
    String username;
    String password;
    
    public AdminRegistrationBean() {
        
    }
    
    //register admin.
    public void register() {
        adminService.registerAdmin(username, password);
        context.addMessage(null, new FacesMessage("Admin is created."));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    } 
}
