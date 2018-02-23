package kz.monetka.server.controllers;

import kz.monetka.server.entities.User;
import kz.monetka.server.entities.pojo.LoginAnswer;
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
    public User greeting(@RequestParam(value="id", defaultValue="") String id) {
        return userService.getOne(id);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity post(@RequestBody User user) {
        String login = user.getLogin();
        String pass = user.getContent();
        if ( userService.checkIfExist(login)){
            User dbUser = userService.findByLogin(login);
            if (dbUser.getContent().equals(pass)){
                answer = new LoginAnswer(HttpStatus.CONFLICT.toString(), "Login is already registered!");
                return new ResponseEntity(answer, HttpStatus.CONFLICT);
            }
        }
        return  new ResponseEntity(answer, HttpStatus.CONFLICT);
    }
}
