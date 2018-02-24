package kz.monetka.server.controllers;

import kz.monetka.server.entities.User;
import kz.monetka.server.services.UserService;
import kz.monetka.server.utils.ControllerUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andrey Smirnov
 * @date 23.02.2018
 */
@RestController
public class RegisterController {
    private static final Logger LOGGER = Logger.getLogger(RegisterController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ResponseEntity post(@RequestBody User user) {
        if (userService.checkIfExist(user.getLogin())) {
            return ControllerUtils.registerResponse(HttpStatus.CONFLICT);
        }
        User newUser = new User(user.getLogin(), user.getContent());
        try {
            userService.create(newUser);
        } catch (Exception ex) {
            LOGGER.error("Error creating user! \r\n" + ex.getMessage());
            return ControllerUtils.registerResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ControllerUtils.registerResponse(HttpStatus.OK);
    }
}
