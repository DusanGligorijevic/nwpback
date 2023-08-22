package com.nwp.security.services.impl;

import com.nwp.security.repositories.UserRepository;
import com.nwp.security.services.UserService;
import com.nwp.security.user.Role;
import com.nwp.security.user.User;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Builder
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Role getRole(Long id) {
        Optional<User> userOptional = findById(id);
        if(userOptional.isEmpty()){
            return null;
        }
        return userOptional.get().getRole();
    }
}
