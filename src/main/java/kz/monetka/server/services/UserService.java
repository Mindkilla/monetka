package kz.monetka.server.services;

import kz.monetka.server.entities.login.AuthToken;
import kz.monetka.server.entities.login.User;
import kz.monetka.server.models.ChangePass;
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

    /**
     * Создает и возвращает токен для доступа к API
     * @param  login  Логин пользователя
     * @return  возвращает новый или уже имещийся токен пользователя
     * @see         AuthToken
     */
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

    /**
     * Удаляет протухшие AuthToken-ы
     */
    public void deleteAllExpiredSince(Date now) {
        tokenRepository.deleteAllExpiredSince(now);
    }

    /**
     * Поиск AuthToken по значению token
     */
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

    /**
     * Создание нового пользователя с зашифрованным паролем
     * @see User
     */
    public void create(User user) {
        passwordEncoder = new BCryptPasswordEncoder();
        String hashPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPass);
        userRepository.save(user);
    }

    public User findOne(String id) {
        return userRepository.findOne(id);
    }

    /**
     * Проверка что пользователь с таким логином существует
     */
    public boolean checkIfExist(String login) {
        return userRepository.existsByLogin(login);
    }

    /**
     * Проверка что токен существует
     */
    public boolean checkToken(String token) {
        return tokenRepository.existsByToken(token);
    }

    public boolean checkUserToken(String token) {
        return tokenRepository.existsByToken(token);
    }

    /**
     * Получаем пароль пользователя
     */
    public String findPassByLogin(String login) {
        User user = userRepository.findBylogin(login);
        return user.getPassword();
    }

    /**
     * Проверяем пароль из запроса с паролем БД
     */
    public boolean verifyUser(UserModel userModel) {
        String userPass = findPassByLogin(userModel.getLogin());
        passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(userModel.getPassword(), userPass);
    }

    public boolean logout(String login, String token) {
        if (checkIfExist(login) && checkToken(token)) {
            User dbUser = userRepository.findBylogin(login);
            AuthToken dbToken = tokenRepository.findByUser(dbUser);
            if (dbToken.getToken().equals(token)) {
                tokenRepository.delete(dbToken);
                return true;
            }
        }
        return false;
    }

    public boolean remindPass(String login) {
        if (checkIfExist(login)) {
            User dbUser = userRepository.findBylogin(login);
            //emailService.sendReminder(dbUser)
            return true;
        }
        return false;
    }

    public boolean changePass(ChangePass model, String token) {
        UserModel userModel = new UserModel(model.getLogin(), model.getPassword());
        if (checkIfExist(userModel.getLogin()) && verifyUser(userModel) && checkToken(token)) {
            User dbUser = userRepository.findBylogin(userModel.getLogin());
            passwordEncoder = new BCryptPasswordEncoder();
            String hashPass = passwordEncoder.encode(model.getNewPassword());
            dbUser.setPassword(hashPass);
            userRepository.save(dbUser);
            return true;
        }
        return false;
    }
}
