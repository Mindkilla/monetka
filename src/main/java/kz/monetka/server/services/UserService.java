package kz.monetka.server.services;

import kz.monetka.server.entities.AuthToken;
import kz.monetka.server.entities.User;
import kz.monetka.server.models.UserModel;
import kz.monetka.server.repository.AuthTokenRepository;
import kz.monetka.server.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * @author Andrey Smirnov
 * @date 22.02.2018
 */
@Service
@Transactional
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthTokenRepository tokenRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public String createVerificationToken(String login) {
        User dbUser = userRepository.findBylogin(login);
        AuthToken oldToken = tokenRepository.findByUser(dbUser);
        if(oldToken != null){
            return oldToken.getToken();
        }
        String token = UUID.randomUUID().toString();
        AuthToken newToken = new AuthToken(dbUser, token);
        tokenRepository.save(newToken);
        return token;
    }

    public void deleteAllExpiredSince(Date now) {
        tokenRepository.deleteAllExpiredSince(now);
    }

    public AuthToken findByToken(String token) {
         return tokenRepository.findByToken(token);
    }

    private User findDoAndSave(String id) {
        User user = userRepository.findOne(id);
        //do something
        return userRepository.save(user);
    }

    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    public void create(User user) {
        passwordEncoder = new BCryptPasswordEncoder();
        String hashPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPass);
        userRepository.save(user);
    }

    public User findOne(String id) {
        return userRepository.findOne(id);
    }

    public boolean checkIfExist(String login) {
        return userRepository.existsByLogin(login);
    }

    public boolean checkToken(String token) {
        return tokenRepository.existsByToken(token);
    }

    public String findPassByLogin(String login) {
        User user = userRepository.findBylogin(login);
        return user.getPassword();
    }

    public boolean verifyUser(UserModel userModel) {
        String userPass = findPassByLogin(userModel.getLogin());
        passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(userModel.getPassword(), userPass);
    }
}
