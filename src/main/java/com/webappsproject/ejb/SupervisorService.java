/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.ejb;
import com.webappsproject.entity.SystemUser;
import com.webappsproject.entity.SystemUserGroup;
import com.webappsproject.entity.Admin;
import com.webappsproject.entity.Supervisor;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author emrehavan
 */
@Stateless
public class SupervisorService {
    
    @PersistenceContext
    EntityManager em;
    
    public SupervisorService() {
        
    }
    
    public void registerSupervisor(
            String sussexId,
            String password,
            String name,
            String surname,
            String email,
            String telephone
            ) {
        
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            Supervisor supervisor;
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = password;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String paswdToStoreInDB = sb.toString();

            sys_user = new SystemUser(sussexId, paswdToStoreInDB);
            sys_user_group = new SystemUserGroup(sussexId, "supervisor");
            supervisor = new Supervisor(sussexId, paswdToStoreInDB, name, surname, email, telephone);
            
            em.persist(sys_user);
            em.persist(sys_user_group);
            em.persist(supervisor);
            System.out.println("Supervisor Registration Completed");
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(AdminService.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
}
