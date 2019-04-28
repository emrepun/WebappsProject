package com.webappsproject.entity;

import com.webappsproject.entity.Project;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-28T02:53:26")
@StaticMetamodel(Student.class)
public class Student_ { 

    public static volatile SingularAttribute<Student, String> sussexId;
    public static volatile SingularAttribute<Student, String> password;
    public static volatile SingularAttribute<Student, String> surname;
    public static volatile ListAttribute<Student, Project> associatedProject;
    public static volatile SingularAttribute<Student, String> name;
    public static volatile SingularAttribute<Student, String> course;
    public static volatile SingularAttribute<Student, Long> id;
    public static volatile SingularAttribute<Student, String> email;

}