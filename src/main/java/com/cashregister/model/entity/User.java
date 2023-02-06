package com.cashregister.model.entity;

public class User extends Entity{
    private String login;
    private String passwd;
    private Role role;

    public User(long id, String login, String passwd) {
        super(id);
        this.login = login;
        this.passwd = passwd;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return login;
    }
}
