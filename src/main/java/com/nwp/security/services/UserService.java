package com.nwp.security.services;

import com.nwp.security.user.Role;
import com.nwp.security.user.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);


    Optional<User> findByEmail(String mail);

    Role getRole(Long id);
}
