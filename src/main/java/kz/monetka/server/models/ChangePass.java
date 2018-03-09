package kz.monetka.server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class ChangePass {
    @NotNull
    @NotEmpty
    @JsonProperty("login")
    private String login;

    @NotNull
    @NotEmpty
    @JsonProperty("password")
    private String password;

    @NotNull
    @NotEmpty
    @JsonProperty("newPassword")
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ChangePass() {
    }

    public ChangePass(String login, String password, String newPassword) {
        this.login = login;
        this.password = password;
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "ChangePass{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
