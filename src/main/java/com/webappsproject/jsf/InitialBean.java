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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    
    public void logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            //this method will disassociate the principal from the session (effectively logging him/her out)
            request.logout();
            request.getSession(true).invalidate();
            context.addMessage(null, new FacesMessage("User is logged out"));
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
        }
    }
}
