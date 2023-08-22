package com.nwp.security.repositories;





import com.nwp.security.user.ErrorMessage;
import com.nwp.security.user.User;



import java.util.List;
import java.util.Optional;

public interface ErrorMessageRepository {
    List<ErrorMessage> findAllByMachine_CreatedBy(Optional<User> byMail);

    ErrorMessage save(ErrorMessage errorMessage);
}
