package com.oms.registration;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;
import org.tavant.api.auth.model.OMSUser;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final OMSUser user;

    public OnRegistrationCompleteEvent(final OMSUser user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    //

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public OMSUser getOMSUser() {
        return user;
    }

}
