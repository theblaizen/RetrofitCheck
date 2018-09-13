package com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public class LogIn {
    private final String login;
    private final String password;

    public LogIn(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
