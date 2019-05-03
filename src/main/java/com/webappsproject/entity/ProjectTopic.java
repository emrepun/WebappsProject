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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author emrehavan
 */

@NamedQueries({
    @NamedQuery(name="getAllProjectTopics", query="SELECT c FROM ProjectTopic c "),
    @NamedQuery(name="findProjectTopicWithName", query="SELECT c FROM ProjectTopic c WHERE c.topicname LIKE :topicname"
)
})

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"topicname"})})
public class ProjectTopic {
    
    @Id
    @GeneratedValue
    private Long id;
    
    //make sure there are no duplicates in DB by topicname column
    //and max character allowed = 100
    @NotNull
    @Column(name="topicname", unique=true)
    @Size(min=0, max=100)
    private String topicname;
    
    //max character allowed = 1000
    @NotNull
    @Size(min=0, max=1000)
    private String topicDescription;
    
    @OneToMany(
            mappedBy = "projectTopic",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Project> projects = new ArrayList<>();
    
    public ProjectTopic() {
        
    }

    public ProjectTopic(String topicname, String topicDescription) {
        this.topicname = topicname;
        this.topicDescription = topicDescription;
    }
    
    public void addProject(Project project) {
        projects.add(project);
    }
    
    public void removeProject(Project project) {
        projects.remove(project);
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
