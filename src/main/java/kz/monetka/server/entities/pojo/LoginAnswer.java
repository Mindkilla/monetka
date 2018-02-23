package kz.monetka.server.entities.pojo;

import org.apache.log4j.Logger;

/**
 * @author Andrey Smirnov
 * @date 23.02.2018
 */
public class LoginAnswer {
    private static final Logger LOGGER = Logger.getLogger(LoginAnswer.class);
    private String token;
    private String status;

    public String getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginAnswer(){}

    public LoginAnswer(String token, String status){
        this.status = status;
        this.token = token;
    }
}
