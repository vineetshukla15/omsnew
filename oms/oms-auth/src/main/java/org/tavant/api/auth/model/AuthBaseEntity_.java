package org.tavant.api.auth.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(AuthBaseEntity.class)
public class AuthBaseEntity_ {
	public static volatile SingularAttribute<AuthBaseEntity, Boolean> deleted;
	public static volatile SingularAttribute<AuthBaseEntity, Long> createdBy;
	public static volatile SingularAttribute<AuthBaseEntity, Date> created;
	public static volatile SingularAttribute<AuthBaseEntity, Long> updatedBy;
	public static volatile SingularAttribute<AuthBaseEntity, Date> updated;
}
