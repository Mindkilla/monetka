package kz.monetka.server.services;

import kz.monetka.server.entities.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.Date;

/**
 * @author Andrey Smirnov
 * @date 22.02.2018
 */
@Service
@Transactional
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    @Autowired
    private LoginRepository loginRepository;

    @Bean
    public SessionFactory sessionFactory(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    private User findDoAndSave(Long id) {
        User user = loginRepository.findOne(id);
        //do something
        return loginRepository.save(user);
    }

    public User save(User user) {
        loginRepository.save(user);
        return user;
    }

    public User create(User user) {
        user.setSysCreateTime(new Date());
        loginRepository.save(user);
        return user;
    }

    public User findOne(Long id) {
        return loginRepository.findOne(id);
    }

    public User getOne(String id) {
        return loginRepository.findByUUID(id);
    }

    public boolean checkIfExist(String login) {
        return loginRepository.exists(login);
    }

    public User findByLogin(String login) {
        return loginRepository.findBylogin(login);
    }
}
