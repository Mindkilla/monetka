package kz.monetka.server.controllers;

import kz.monetka.server.entities.login.User;
import kz.monetka.server.models.ChangePass;
import kz.monetka.server.models.LoginAnswer;
import kz.monetka.server.models.UserModel;
import kz.monetka.server.services.UserService;
import kz.monetka.server.utils.Consts;
import kz.monetka.server.utils.ControllerUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Andrey Smirnov
 * @date 22.02.2018
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;
    LoginAnswer answer;

    private static final Logger LOGGER = Logger.getLogger(UserController.class);

    @PreAuthorize("hasIpAddress('127.0.0.1')")
    @PostMapping(value = "/user/register")
    public ResponseEntity post(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ControllerUtils.showErrors(errors);
        }
        if (userService.checkIfExist(user.getLogin())) {
            return ControllerUtils.registerResponse(HttpStatus.CONFLICT);
        }
        User newUser = new User(user.getLogin(), user.getPassword());
        try {
            userService.create(newUser);
        } catch (Exception ex) {
            LOGGER.error("Error creating user! \r\n" + ex.getMessage());
            return ControllerUtils.registerResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ControllerUtils.registerResponse(HttpStatus.OK);
    }

    @PostMapping(value = "/user/login")
    public ResponseEntity login(@Valid @RequestBody UserModel userModel, Errors errors) {
        if (errors.hasErrors()) {
            return ControllerUtils.showErrors(errors);
        }
        String login = userModel.getLogin();
        if (userService.checkIfExist(login) && userService.verifyUser(userModel)) {
            String token = userService.createVerificationToken(login);
            answer = new LoginAnswer(token, HttpStatus.OK.toString(), "");
            return new ResponseEntity(answer, HttpStatus.OK);
        }
        return new ResponseEntity(answer, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping(value = "/user/logout/{login}")
    public ResponseEntity logout(@PathVariable("login") String login, @RequestHeader(name = Consts.HEADER) String token) {
        if (userService.logout(login, token)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/user/remind")
    public ResponseEntity remindPass(@RequestBody String login) {
        if (userService.remindPass(login)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/user/changePass")
    public ResponseEntity changePass(@Valid @RequestBody ChangePass userModel, @RequestHeader(name = Consts.HEADER) String token,
                                     Errors errors) {
        if (errors.hasErrors()) {
            return ControllerUtils.showErrors(errors);
        }
        if (userService.changePass(userModel, token)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }


}
