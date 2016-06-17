package org.ism.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.ism.entities.NonConformite;
import org.ism.entities.NonConformiteRequest;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-06-17T19:04:16")
@StaticMetamodel(IsmNcrstate.class)
public class IsmNcrstate_ { 

    public static volatile SingularAttribute<IsmNcrstate, Integer> id;
    public static volatile SingularAttribute<IsmNcrstate, String> statename;
    public static volatile CollectionAttribute<IsmNcrstate, NonConformiteRequest> nonConformiteRequestCollection;
    public static volatile SingularAttribute<IsmNcrstate, String> istate;
    public static volatile CollectionAttribute<IsmNcrstate, NonConformite> nonConformiteCollection;

}