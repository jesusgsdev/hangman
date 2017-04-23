package com.jesusgsdev.entities.users;

/**
 * Created by jesgarsal on 19/04/17.
 */
public abstract class User {

    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
