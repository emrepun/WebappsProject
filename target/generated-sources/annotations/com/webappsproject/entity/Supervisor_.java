package com.webappsproject.entity;

import com.webappsproject.entity.Project;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-05-02T16:53:12")
@StaticMetamodel(Supervisor.class)
public class Supervisor_ { 

    public static volatile SingularAttribute<Supervisor, String> sussexId;
    public static volatile SingularAttribute<Supervisor, String> password;
    public static volatile SingularAttribute<Supervisor, String> surname;
    public static volatile SingularAttribute<Supervisor, String> name;
    public static volatile SingularAttribute<Supervisor, String> telephone;
    public static volatile SingularAttribute<Supervisor, Long> id;
    public static volatile SingularAttribute<Supervisor, String> email;
    public static volatile ListAttribute<Supervisor, Project> ownedProjects;
    public static volatile ListAttribute<Supervisor, Project> proposedProjects;

}