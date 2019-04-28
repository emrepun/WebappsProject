/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;

/**
 *
 * @author emrehavan
 */

@NamedQuery(name="getAllSupervisors", query="SELECT c FROM Supervisor c ")

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"sussexId"})})
public class Supervisor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    //make sure there are no duplicates in DB by username column
    @NotNull
    @Column(name="sussexId", unique=true)
    private String sussexId;
    
    @NotNull
    private String password;
    
    @NotNull
    private String name;
    
    @NotNull
    private String surname;
    
    @Email(message = "Please enter a valid email.")
    @NotNull
    private String email;
    
    @NotNull
    private String telephone;
    
    // projects created "by" the supervisor.
    @OneToMany(
            cascade = CascadeType.DETACH,
            orphanRemoval = true
    )
    @JoinColumn(name="post_id")
    private List<Project> ownedProjects = new ArrayList<>();
    
    // projects proposed "to" the supervisor.
    @OneToMany(
            cascade = CascadeType.DETACH,
            orphanRemoval = true
    )
    @JoinColumn(name="post_id")
    private List<Project> proposedProjects = new ArrayList<>();
    
    public Supervisor() {
        
    }

    public Supervisor(String sussexId, String password, String name, String surname, String email, String telephone) {
        this.sussexId = sussexId;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.telephone = telephone;
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

    public List<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(List<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public List<Project> getProposedProjects() {
        return proposedProjects;
    }

    public void setProposedProjects(List<Project> proposedProjects) {
        this.proposedProjects = proposedProjects;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + Objects.hashCode(this.sussexId);
        hash = 41 * hash + Objects.hashCode(this.password);
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.surname);
        hash = 41 * hash + Objects.hashCode(this.email);
        hash = 41 * hash + Objects.hashCode(this.telephone);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Supervisor other = (Supervisor) obj;
        if (!Objects.equals(this.sussexId, other.sussexId)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.telephone, other.telephone)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
