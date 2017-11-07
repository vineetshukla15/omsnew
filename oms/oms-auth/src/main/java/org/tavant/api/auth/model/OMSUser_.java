package org.tavant.api.auth.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(OMSUser.class)
public class OMSUser_ extends AuthBaseEntity_{

	public static volatile SingularAttribute<OMSUser, String> username;
	public static volatile SingularAttribute<OMSUser, String> firstName;
	public static volatile SingularAttribute<OMSUser, String> lastName;
	public static volatile SingularAttribute<OMSUser, String> emailId;
	public static volatile SingularAttribute<OMSUser, Boolean> enabled;
	public static volatile SingularAttribute<OMSUser, Role> role;

}
