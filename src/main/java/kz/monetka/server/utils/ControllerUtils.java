package kz.monetka.server.utils;

import kz.monetka.server.models.ResponseAnswer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ControllerUtils {

    public static ResponseEntity registerResponse(HttpStatus status) {
        ResponseAnswer answer;
        if (HttpStatus.CONFLICT.equals(status)) {
            answer = new ResponseAnswer(HttpStatus.CONFLICT.toString(), "Login is already registered!");
            return new ResponseEntity(answer, HttpStatus.CONFLICT);
        } else if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            answer = new ResponseAnswer(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Please mail to administrator!");
            return new ResponseEntity(answer, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        answer = new ResponseAnswer(HttpStatus.OK.toString(), "");
        return new ResponseEntity(answer, HttpStatus.OK);
    }
}
