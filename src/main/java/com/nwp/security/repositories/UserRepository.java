package com.nwp.security.repositories;



import com.nwp.security.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAll();

    Optional<User> findById(Long id);

    User save(User user);

     void deleteById(Long id);


    Optional<User> findByEmail(String mail);
}
