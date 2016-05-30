package org.ism.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.Staff;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-30T19:32:41")
@StaticMetamodel(IsmGenre.class)
public class IsmGenre_ { 

    public static volatile SingularAttribute<IsmGenre, String> genrename;
    public static volatile SingularAttribute<IsmGenre, String> genre;
    public static volatile CollectionAttribute<IsmGenre, Staff> staffCollection;
    public static volatile SingularAttribute<IsmGenre, Integer> id;

}