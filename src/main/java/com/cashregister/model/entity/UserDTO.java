package com.cashregister.model.entity;

public class UserDTO {
    private String login;
    private String passwd;
    private String role;
    private String id;

    public UserDTO(User user){
        this.login = user.getLogin();
        this.passwd = user.getPasswd();
        this.role = String.valueOf(user.getRole().ordinal() + 1);
        this.id = String.valueOf(user.getId());
    }

    private UserDTO(){
        this.login = "";
        this.passwd = "";
        this.role = "";
        this.id = "";
    }

    public static UserDTO getInstance(){
        return new UserDTO();
    }

    public String getLogin() {
        return login;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getRole() {
        return role;
    }

    public String getId() {
        return id;
    }
}
