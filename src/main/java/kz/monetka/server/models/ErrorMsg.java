package kz.monetka.server.models;

import org.apache.log4j.Logger;

/**
 * @author Andrey Smirnov
 * @date 26.02.2018
 */
public class ErrorMsg {
    private static final Logger LOGGER = Logger.getLogger(ErrorMsg.class);

    private String msg;

    public ErrorMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ErrorMsg() {
    }
}
