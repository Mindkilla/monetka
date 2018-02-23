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
    ResponseAnswer answer;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity post(@RequestBody User user) {
        if (userService.checkIfExist(user.getLogin())) {
            answer = new ResponseAnswer(HttpStatus.CONFLICT.toString(), "Login is already registered!");
            return new ResponseEntity(answer, HttpStatus.CONFLICT);
        }
        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setContent(user.getContent());
        userService.create(newUser);
        answer = new ResponseAnswer(HttpStatus.OK.toString(), "");
        return new ResponseEntity(answer, HttpStatus.OK);
    }
}
