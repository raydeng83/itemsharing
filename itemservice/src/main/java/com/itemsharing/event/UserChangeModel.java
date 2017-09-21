package com.itemsharing.event;

/**
 * Created by z00382545 on 9/20/17.
 */
public class UserChangeModel {
    private String type;
    private String action;
    private String username;
    private String correlationId;

    public UserChangeModel(){super();}

    public UserChangeModel(String type, String action, String username, String correlationId) {
        super();
        this.type   = type;
        this.action = action;
        this.username = username;
        this.correlationId = correlationId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }


}