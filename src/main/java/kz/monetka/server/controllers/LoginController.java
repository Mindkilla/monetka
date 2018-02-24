package kz.monetka.server.controllers;

import kz.monetka.server.entities.User;
import kz.monetka.server.models.LoginAnswer;
import kz.monetka.server.models.UserModel;
import kz.monetka.server.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public User greeting(@RequestParam(value = "id", defaultValue = "") String id) {
        return userService.findOne(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity post(@RequestBody UserModel userModel) {
        String login = userModel.getLogin();
        if (userService.checkIfExist(login) && userService.verifyUser(userModel)) {
            String token = userService.createVerificationToken(login);
            answer = new LoginAnswer(token, HttpStatus.OK.toString(), "");
            return new ResponseEntity(answer, HttpStatus.OK);
        }
        return new ResponseEntity(answer, HttpStatus.UNAUTHORIZED);
    }
}
