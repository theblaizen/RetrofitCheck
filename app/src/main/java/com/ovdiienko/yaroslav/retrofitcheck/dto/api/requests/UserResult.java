package com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests;

import com.ovdiienko.yaroslav.retrofitcheck.dto.db.models.User;

/**
 * Created by Yaroslav Ovdiienko on 9/13/18.
 * RetrofitCheck
 */

public class UserResult {
    private User user;
    private String description;
    private boolean status;

    public UserResult(User user, String description, boolean status) {
        this.user = user;
        this.description = description;
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
