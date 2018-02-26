package kz.monetka.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Andrey Smirnov
 * @date 23.02.2018
 */

@Entity
@Data
@Table(name = "MONETKA_USERS")
public class User extends BaseEntity {

    @NotNull
    @NotEmpty
    @JsonProperty("login")
    private String login;

    @NotNull
    @NotEmpty
    @Column(nullable = false)
    @JsonProperty("password")
    @JsonIgnore
    private String password;
    private boolean confirmed;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public User() {
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
