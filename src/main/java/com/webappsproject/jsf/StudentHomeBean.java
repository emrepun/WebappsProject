/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.LogoutService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class StudentHomeBean {
    
    //inject services.
    @EJB
    LogoutService logoutService;
    
    public StudentHomeBean() {
        
    }
    
    public String logout() {
        return logoutService.logout();
    }
}
