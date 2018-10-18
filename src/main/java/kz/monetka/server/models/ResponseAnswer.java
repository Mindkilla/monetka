package kz.monetka.server.models;

/**
 * @author Andrey Smirnov
 * @date 23.02.2018
 */
public class ResponseAnswer {

    private String status;
    private String error;

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ResponseAnswer() {
    }

    public ResponseAnswer(String status, String error) {
        this.status = status;
        this.error = error;
    }

    @Override
    public String toString() {
        return "ResponseAnswer{" +
                "status='" + status + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
