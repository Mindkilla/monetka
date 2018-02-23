package kz.monetka.server.entities.pojo;

import org.apache.log4j.Logger;

/**
 * @author Andrey Smirnov
 * @date 23.02.2018
 */
public class LoginAnswer {
    private static final Logger LOGGER = Logger.getLogger(LoginAnswer.class);
    private String authToken;
    private String status;
    private String err;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public String getStatus() {
        return status;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public LoginAnswer(){}

    public LoginAnswer(String authToken, String status, String err){
        this.status = status;
        this.authToken = authToken;
        this.err = err;
    }
}
