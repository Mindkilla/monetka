package kz.monetka.server.controllers;

import kz.monetka.server.entities.User;
import kz.monetka.server.models.ErrorMsg;
import kz.monetka.server.services.UserService;
import kz.monetka.server.utils.ControllerUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * @author Andrey Smirnov
 * @date 23.02.2018
 */
@RestController
public class RegisterController {
    private static final Logger LOGGER = Logger.getLogger(RegisterController.class);

    @Autowired
    UserService userService;

    @PreAuthorize("hasIpAddress('127.0.0.1')")
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ResponseEntity post(@Valid @RequestBody User user, Errors errors) {
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
}
