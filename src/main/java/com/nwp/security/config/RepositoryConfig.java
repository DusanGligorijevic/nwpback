package com.nwp.security.config;



import com.nwp.security.repositories.ErrorMessageRepository;
import com.nwp.security.repositories.MachineRepository;
import com.nwp.security.repositories.UserRepository;
import com.nwp.security.repositories.impl.ErrorMessageRepositoryImpl;
import com.nwp.security.repositories.impl.MachineRepositoryImpl;
import com.nwp.security.repositories.impl.UserRepositoryImpl;
import com.nwp.security.user.ErrorMessageRepositoryJpa;
import com.nwp.security.user.Machine;
import com.nwp.security.user.MachineRepositoryJpa;
import com.nwp.security.user.UserRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    UserRepository userRepository(UserRepositoryJpa userRepositoryJpa){
        return UserRepositoryImpl.builder()
                .userRepositoryJpa(userRepositoryJpa)
                .build();
    }
    @Bean
    ErrorMessageRepository errorMessageRepository(ErrorMessageRepositoryJpa errorMessageRepositoryJpa){
        return ErrorMessageRepositoryImpl.builder()
                .errorMessageRepositoryJpa(errorMessageRepositoryJpa)
                .build();
    }
    @Bean
    MachineRepository machineRepository(MachineRepositoryJpa machineRepositoryJpa){
        return MachineRepositoryImpl.builder()
                .machineRepositoryJpa(machineRepositoryJpa)
                .build();
    }
}
