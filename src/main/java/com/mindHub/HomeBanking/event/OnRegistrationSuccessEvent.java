package com.mindHub.HomeBanking.event;

import com.mindHub.HomeBanking.models.Client;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationSuccessEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private String appUrl;
    private Client client;

    public OnRegistrationSuccessEvent(Client client, String appUrl) {
        super(client);
        this.client = client;
        this.appUrl = appUrl;
    }
    public String getAppUrl() {
        return appUrl;
    }
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
    public Client getClient() {
        return client;
    }
    public void setUser(Client client) {
        this.client = client;
    }
}
