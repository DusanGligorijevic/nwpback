package com.nwp.security.repositories.impl;

import com.nwp.security.repositories.ErrorMessageRepository;
import com.nwp.security.user.ErrorMessage;
import com.nwp.security.user.ErrorMessageRepositoryJpa;
import com.nwp.security.user.User;
import lombok.Builder;


import java.util.List;
import java.util.Optional;
@Builder
public class ErrorMessageRepositoryImpl implements ErrorMessageRepository {


    private final ErrorMessageRepositoryJpa errorMessageRepositoryJpa;


    @Override
    public List<ErrorMessage> findAllByMachine_CreatedBy(Optional<User> byMail) {
        return null;
    }

    @Override
    public ErrorMessage save(ErrorMessage errorMessage) {
        return null;
    }
}
