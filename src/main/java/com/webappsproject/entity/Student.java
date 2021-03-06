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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;

/**
 *
 * @author emrehavan
 */

@NamedQueries({
    @NamedQuery(name="getAllStudents", query="SELECT c FROM Student c "),
    @NamedQuery(name="findStudentWithSussexId", query="SELECT c FROM Student c WHERE c.sussexId LIKE :sussexId")
})



@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"sussexId"})})
public class Student {
    
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
    private String course;
    
    //associated project of student, either selected or proposed.
    @OneToOne(mappedBy = "student")
    private Project associatedProject;
    
    public Student() {
        
    }

    public Student(String sussexId, String password, String name, String surname, String email, String course) {
        this.sussexId = sussexId;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.course = course;
        this.associatedProject = null;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Project getAssociatedProject() {
        return associatedProject;
    }

    public void setAssociatedProject(Project associatedProject) {
        this.associatedProject = associatedProject;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.sussexId);
        hash = 19 * hash + Objects.hashCode(this.password);
        hash = 19 * hash + Objects.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.surname);
        hash = 19 * hash + Objects.hashCode(this.email);
        hash = 19 * hash + Objects.hashCode(this.course);
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
        final Student other = (Student) obj;
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
        if (!Objects.equals(this.course, other.course)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return this.name + " " + this.surname + "(" + this.sussexId + ")";
    }
}
