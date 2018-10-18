package kz.monetka.server.utils;

import kz.monetka.server.models.ErrorMsg;
import kz.monetka.server.models.ResponseAnswer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

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

    public static ResponseEntity showErrors(Errors errors) {
        ErrorMsg result = new ErrorMsg();
        //If error, just return a 400 bad request, along with the error message
        // get all errors
        result.setMsg(errors.getFieldErrors()
                .stream()
                .map(x -> x.getField() + " " + x.getDefaultMessage())
                .collect(Collectors.joining(",")));

        return ResponseEntity.badRequest().body(result);
    }

    private ControllerUtils() {
    }
}
