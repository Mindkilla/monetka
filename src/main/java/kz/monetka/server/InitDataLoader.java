package kz.monetka.server;

import kz.monetka.server.entities.login.User;
import kz.monetka.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitDataLoader {
    private UserRepository userRepository;

    @Autowired
    public InitDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        loadUsers();
        loadPaymentTypes();
    }

    private void loadUsers() {
        if (!userRepository.existsByLogin("user")){
            userRepository.save(new User("user", "pass"));
        }
        if (!userRepository.existsByLogin("admin")){
            userRepository.save(new User("admin", "pass"));
        }
    }

    private void loadPaymentTypes() {
        //example
        userRepository.save(new User("user", "pass"));
    }
}
