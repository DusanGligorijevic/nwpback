package com.nwp.security.repositories.impl;


import com.nwp.security.repositories.UserRepository;
import com.nwp.security.user.User;
import com.nwp.security.user.UserRepositoryJpa;
import lombok.Builder;

import java.util.List;
import java.util.Optional;
@Builder
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJpa userRepositoryJpa;

    @Override
    public List<User> getAll() {
        return userRepositoryJpa.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(userRepositoryJpa.findById(id).orElse(null));
    }

    @Override
    public User save(User user) {
        return userRepositoryJpa.save(user);
    }

    @Override
    public void deleteById(Long id) {
         userRepositoryJpa.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepositoryJpa.findByEmail(email).orElse(null));
    }
}
