package kz.monetka.server.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Andrey Smirnov
 * @date 23.02.2018
 */

@Entity
@Data
@Getter
@Table(name = "MON_USERS")
public class User extends BaseEntity {

    @JsonProperty("login")
    private String login;

    @Column(nullable = false)
    @JsonProperty("content")
    private String content;

    public User(String login, String content) {
        this.login = login;
        this.content = content;
    }

    public String getLogin() {
        return login;
    }

    public String getContent() {
        return content;
    }

    public User() {
    }
}
