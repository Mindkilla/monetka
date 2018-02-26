package kz.monetka.server.controllers;

import kz.monetka.server.models.ErrorMsg;
import kz.monetka.server.models.LoginAnswer;
import kz.monetka.server.models.UserModel;
import kz.monetka.server.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @author Andrey Smirnov
 * @date 22.02.2018
 */
@RestController
public class LoginController {

    @Autowired
    UserService userService;
    LoginAnswer answer;

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity post(@Valid @RequestBody UserModel userModel, Errors errors) {
        ErrorMsg result = new ErrorMsg();
        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {
            // get all errors
            result.setMsg(errors.getFieldErrors()
                    .stream()
                    .map(x ->x.getField() + " " + x.getDefaultMessage())
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);
        }
        String login = userModel.getLogin();
        if (userService.checkIfExist(login) && userService.verifyUser(userModel)) {
            String token = userService.createVerificationToken(login);
            answer = new LoginAnswer(token, HttpStatus.OK.toString(), "");
            return new ResponseEntity(answer, HttpStatus.OK);
        }
        return new ResponseEntity(answer, HttpStatus.UNAUTHORIZED);
    }
}
