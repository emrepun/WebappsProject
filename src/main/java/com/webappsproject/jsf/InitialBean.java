/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.jsf;

import com.webappsproject.ejb.AdminService;
import com.webappsproject.entity.Admin;
import com.webappsproject.entity.SystemUser;
import com.webappsproject.entity.SystemUserGroup;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



/**
 *
 * @author emrehavan
 */
@Named
@Singleton
//This bean is triggered from index by referring to welcomeString,
//so that this bean would be initialized to register admin1 if it does not exists.
public class InitialBean implements Serializable {
    
    @PersistenceContext
    EntityManager em;
    
    private String welcomeString = "Welcome to Msc Project System!";
    
    public InitialBean() {
        
    }
    
    @PostConstruct
    public void initial() {
        System.out.println("created");

        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            Admin admin;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = "admin1";
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();

            sys_user = new SystemUser("admin1", paswdToStoreInDB);
            sys_user_group = new SystemUserGroup("admin1", "admin");
            admin = new Admin("admin1", paswdToStoreInDB);
            
            em.persist(sys_user);
            em.persist(sys_user_group);
            em.persist(admin);
            System.out.println("Admin Registration Completed");
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Admin already registered");
        }
        
        
        
        //String supervisorID = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
                
        //adminService.registerAdmin("admin1", "admin1");
//        if (adminService.getAdminWithUsername("admin1") == null) {
//            adminService.registerAdmin("admin1", "admin1");
//        }
    }  

    public String getWelcomeString() {
        return welcomeString;
    }

    public void setWelcomeString(String welcomeString) {
        this.welcomeString = welcomeString;
    }
}
