package org.security.springsecurity.service;

import org.security.springsecurity.entity.User;
import org.security.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  BCryptPasswordEncoder encoder;

    public User createUser(String username, String password, String name) {
        User user = new User(username, encoder.encode(password), name);
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

}
