package com.webappsproject.entity;

import com.webappsproject.entity.Project.ProjectStatus;
import com.webappsproject.entity.ProjectTopic;
import com.webappsproject.entity.Supervisor;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-30T00:48:11")
@StaticMetamodel(Project.class)
public class Project_ { 

    public static volatile SingularAttribute<Project, ProjectTopic> projectTopic;
    public static volatile SingularAttribute<Project, String> description;
    public static volatile SingularAttribute<Project, String> requiredSkills;
    public static volatile SingularAttribute<Project, Long> id;
    public static volatile SingularAttribute<Project, String> title;
    public static volatile SingularAttribute<Project, Supervisor> supervisor;
    public static volatile SingularAttribute<Project, ProjectStatus> status;

}