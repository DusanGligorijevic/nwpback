package com.nwp.security.config;

import com.nwp.security.repositories.ErrorMessageRepository;
import com.nwp.security.repositories.MachineRepository;
import com.nwp.security.repositories.UserRepository;
import com.nwp.security.services.UserService;
import com.nwp.security.services.impl.MachineService;
import com.nwp.security.services.impl.MachineServiceImpl;
import com.nwp.security.services.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@Configuration
public class ServiceConfig {

    @Bean
    UserService userService(UserRepository userRepository){
        return UserServiceImpl.builder()
                .userRepository(userRepository)
                .build();
    }
    @Bean
    MachineService machineService(MachineRepository machineRepository, UserRepository userRepository, TaskScheduler taskScheduler,
             ErrorMessageRepository errorMessageRepository){
        return MachineServiceImpl.builder()
                .machineRepository(machineRepository)
                .errorMessageRepository(errorMessageRepository)
                .taskScheduler(taskScheduler)
                .userRepository(userRepository)
                .build();
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(); //single threaded by default
    }

}
