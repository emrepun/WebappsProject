/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;
import com.webappsproject.ejb.SupervisorService;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import java.io.Serializable;

/**
 *
 * @author emrehavan
 */
@Named
@RequestScoped
public class SupervisorRegistrationBean implements Serializable {
    
    @EJB
    SupervisorService supervisorService;
    
    FacesContext context = FacesContext.getCurrentInstance();
    
    String sussexId;
    String password;
    String name;
    String surname;
    String email;
    String telephone;
    
    public SupervisorRegistrationBean() {
        
    }
    
    public void register() {
        supervisorService.registerSupervisor(sussexId, password, name, surname, email, telephone);
        context.addMessage(null, new FacesMessage("Supervisor is created."));
    }

    public String getSussexId() {
        return sussexId;
    }

    public void setSussexId(String sussexId) {
        this.sussexId = sussexId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
