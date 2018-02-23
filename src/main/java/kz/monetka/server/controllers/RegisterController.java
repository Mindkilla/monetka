package kz.monetka.server.controllers;

import kz.monetka.server.entities.User;
import kz.monetka.server.entities.pojo.ResponseAnswer;
import kz.monetka.server.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Andrey Smirnov
 * @date 23.02.2018
 */
@RestController
public class RegisterController {
    private static final Logger LOGGER = Logger.getLogger(RegisterController.class);

    @Autowired
    UserService userService;
    private ResponseAnswer answer;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity post(@RequestBody User user) {
        if (userService.checkIfExist(user.getLogin())) {
            return response(HttpStatus.CONFLICT);
        }
        User newUser = new User(user.getLogin(), user.getContent());
        try {
            userService.create(newUser);
        } catch (Exception ex) {
            LOGGER.error("Error creating user! \r\n" + ex.getMessage());
            return response(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response(HttpStatus.OK);
    }

    private ResponseEntity response(HttpStatus status) {
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
