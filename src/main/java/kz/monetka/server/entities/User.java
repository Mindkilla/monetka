package kz.monetka.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Andrey Smirnov
 * @date 23.02.2018
 */

@Entity
@Data
@Table(name = "MONETKA_USERS")
public class User extends BaseEntity {

    @JsonProperty("login")
    private String login;

    @Column(nullable = false)
    @JsonProperty("content")
    @JsonIgnore
    private String content;
    private boolean confirmed;

    public User(String login, String content) {
        this.login = login;
        this.content = content;
        this.confirmed = false;
        this.setSysCreateTime(new Date());
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getLogin() {
        return login;
    }

    public String getContent() {
        return content;
    }

    public User() {
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
