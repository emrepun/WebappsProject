/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author emrehavan
 */
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"topicname"})})
public class ProjectTopic {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @NotNull
    @Column(name="topicname", unique=true)
    private String topicname;
    
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name="post_id")
    private List<Project> projects = new ArrayList<>();
    
    public ProjectTopic() {
        
    }

    public ProjectTopic(String topicname) {
        this.topicname = topicname;
    }
    
    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
    
}
