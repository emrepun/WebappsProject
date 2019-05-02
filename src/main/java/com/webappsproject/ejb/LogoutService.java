/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;

import javax.ejb.Singleton;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author emrehavan
 */
@Singleton
public class LogoutService {
    
    public LogoutService() {
    
    }
    
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            //this method will disassociate the principal from the session (effectively logging him/her out)
            request.logout();
            request.getSession(true).invalidate();
            context.addMessage(null, new FacesMessage("User is logged out"));
            return "index";
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
            return "";
        }
    }
    
}
