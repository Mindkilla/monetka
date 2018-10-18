package kz.monetka.server.models;

/**
 * @author Andrey Smirnov
 * @date 26.02.2018
 */
public class ErrorMsg {

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
