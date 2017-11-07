package org.tavant.api.auth.model;

import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
public class Role_ extends AuthBaseEntity_{
	public static volatile SingularAttribute<Role, Long> roleId;
	public static volatile SingularAttribute<Role, String> roleName;
	public static volatile SingularAttribute<Role, Boolean> active;
	public static volatile CollectionAttribute<Role, Permissions> permissions;
}
