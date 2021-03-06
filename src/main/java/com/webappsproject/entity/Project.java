/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webappsproject.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author emrehavan
 */


@NamedQueries({
    @NamedQuery(name="getAllProjects", query="SELECT c FROM Project c "),
    @NamedQuery(name="findAvailableProjects", query="SELECT c FROM Project c WHERE c.status = :status"),
    @NamedQuery(name="findProjectWithName", query="SELECT c FROM Project c WHERE c.title LIKE :title"),
    @NamedQuery(name="findProjectsWithSupervisorId", query="SELECT c FROM Project c WHERE c.supervisor.sussexId LIKE :sussexId")
})

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"title"})})
public class Project {
    
    @Id
    @GeneratedValue
    private Long id;
    
    //make sure there are no duplicates in DB by title column
    //and max character allowed = 200
    @NotNull
    @Column(name="title", unique=true)
    @Size(min=0, max=200)
    private String title;
    
    //max character allowed = 1000
    @NotNull
    @Size(min=0, max=1000)
    private String description;
    
    @NotNull
    private String requiredSkills;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topic_id")
    private ProjectTopic projectTopic;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supervisor_id")
    private Supervisor supervisor;
    
    // this is set when a student makes a proposal by creating a project.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="supervisorOptional_id")
    private Supervisor supervisorOptional;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id")
    private Student student;
    
    public enum ProjectStatus {
        ACCEPTED,
        PROPOSED,
        AVAILABLE
    }
    
    public Project() {
        
    }
    
    public Project(String title, String description, String requiredSkills) {
        this.title = title;
        this.description = description;
        this.requiredSkills = requiredSkills;
        this.status = ProjectStatus.AVAILABLE;
        
    }

    public String getName() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public ProjectTopic getProjectTopic() {
        return projectTopic;
    }

    public void setProjectTopic(ProjectTopic projectTopic) {
        this.projectTopic = projectTopic;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
 
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Supervisor getSupervisorOptional() {
        return supervisorOptional;
    }

    public void setSupervisorOptional(Supervisor supervisorOptional) {
        this.supervisorOptional = supervisorOptional;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.title);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.requiredSkills);
        hash = 79 * hash + Objects.hashCode(this.status);
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
        final Project other = (Project) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.requiredSkills, other.requiredSkills)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return this.title;
    }
}

