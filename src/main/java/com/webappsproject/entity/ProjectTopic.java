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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author emrehavan
 */

@NamedQuery(name="getAllProjectTopics", query="SELECT c FROM ProjectTopic c ")

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"topicname"})})
public class ProjectTopic {
    
    @Id
    @GeneratedValue
    private Long id;
    
    //make sure there are no duplicates in DB by topicname column
    @NotNull
    @Column(name="topicname", unique=true)
    private String topicname;
    
    @NotNull
    private String topicDescription;
    
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name="post_id")
    private List<Project> projects = new ArrayList<>();
    
    public ProjectTopic() {
        
    }

    public ProjectTopic(String topicname, String topicDescription) {
        this.topicname = topicname;
        this.topicDescription = topicDescription;
    }
    
    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return this.topicname + " (" + this.topicDescription + ")";
    }
    
}
