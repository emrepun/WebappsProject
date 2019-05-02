package com.webappsproject.entity;

import com.webappsproject.entity.Project;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-02T01:36:54")
@StaticMetamodel(ProjectTopic.class)
public class ProjectTopic_ { 

    public static volatile ListAttribute<ProjectTopic, Project> projects;
    public static volatile SingularAttribute<ProjectTopic, String> topicname;
    public static volatile SingularAttribute<ProjectTopic, Long> id;
    public static volatile SingularAttribute<ProjectTopic, String> topicDescription;

}