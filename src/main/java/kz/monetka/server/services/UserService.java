package kz.monetka.server.services;

import kz.monetka.server.entities.AuthToken;
import kz.monetka.server.entities.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String createVerificationToken(User user) {
        User dbUser = userRepository.findBylogin(user.getLogin());
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
        userRepository.save(user);
    }

    public User findOne(String id) {
        return userRepository.findOne(id);
    }

    public boolean checkIfExist(String login) {
        return userRepository.existsByLogin(login);
    }

    public User findByLogin(String login) {
        return userRepository.findBylogin(login);
    }
}
