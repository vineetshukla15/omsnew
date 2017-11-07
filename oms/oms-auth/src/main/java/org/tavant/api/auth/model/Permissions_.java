package org.tavant.api.auth.model;

import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Permissions.class)
public class Permissions_ extends AuthBaseEntity_{
	public static volatile SingularAttribute<Permissions, String> permissionName;
	public static volatile SingularAttribute<Permissions, String> value;
	public static volatile CollectionAttribute<Permissions, Role> role;
}
