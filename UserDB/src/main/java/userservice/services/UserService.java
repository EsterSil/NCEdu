package userservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import userservice.repositories.User;
import userservice.repositories.UserRepository;
import userservice.utils.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;


    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;

    }


    public Long saveUser(String password, String login) {
        User user = new User(login, encoder.encode(password));
        repository.save(user);
        log.info("userService:: new user "+ user.toString());
        return user.getUserID();
    }


    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public User getByLogin(String login){
        return repository.getUserByLogin(login);
    }
}
