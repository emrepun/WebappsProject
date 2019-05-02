/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;
import com.webappsproject.entity.SystemUser;
import com.webappsproject.entity.SystemUserGroup;
import com.webappsproject.entity.Admin;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author emrehavan
 */
@Stateless
@RolesAllowed({"admin"}) //only admins can use this service.
public class AdminService {
    
    @PersistenceContext
    EntityManager em;
    
    public AdminService() {
        
    }
    
    public void registerAdmin(String username, String password) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            Admin admin;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = password;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();

            sys_user = new SystemUser(username, paswdToStoreInDB);
            sys_user_group = new SystemUserGroup(username, "admin");
            admin = new Admin(username, paswdToStoreInDB);
            
            em.persist(sys_user);
            em.persist(sys_user_group);
            em.persist(admin);
            System.out.println("Admin Registration Completed");
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
//    public synchronized Admin getAdminWithUsername(String username) {
//        return (Admin)em.createNamedQuery("findAdminWithUsername").
//                setParameter("username", username).
//                getResultList().get(0);
//    }
    
    public synchronized List<Admin> getAdminList() {
        return em.createNamedQuery("getAllAdmins").getResultList();
    }
}
